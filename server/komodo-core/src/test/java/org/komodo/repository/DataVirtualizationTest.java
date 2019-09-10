package org.komodo.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komodo.datavirtualization.DataVirtualization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("nls")
@RunWith(SpringRunner.class)
@DataJpaTest
public class DataVirtualizationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DataVirtualizationRepository dataVirtualizationRepository;

    @Autowired
    private WorkspaceManagerImpl workspaceManagerImpl;

    @Test
    public void testFindDeleteByName() {
        DataVirtualization dv = workspaceManagerImpl.createDataVirtualization("foo");

        entityManager.flush();

        dv.setModifiedAt(null);

        entityManager.flush();

        assertNotNull(dv.getModifiedAt());
        assertEquals(Long.valueOf(1), dv.getVersion());

        DataVirtualization found = dataVirtualizationRepository.findByName(dv.getName());

        assertNotNull(dataVirtualizationRepository.findByNameIgnoreCase(dv.getName().toUpperCase()));

        assertNotNull(found.getId());

        assertEquals(dv.getName(), found.getName());

        assertTrue(workspaceManagerImpl.deleteDataVirtualization(dv.getName()));

        entityManager.flush();
    }

    @Test public void testGetAllNames() {
        workspaceManagerImpl.createDataVirtualization("foo");
        workspaceManagerImpl.createDataVirtualization("bar");

        assertEquals(2, workspaceManagerImpl.findDataVirtualizationNames().size());
    }


}
