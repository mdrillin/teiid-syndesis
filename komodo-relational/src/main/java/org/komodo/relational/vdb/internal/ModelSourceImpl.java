/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.komodo.relational.vdb.internal;

import org.komodo.core.KomodoLexicon;
import org.komodo.relational.Messages;
import org.komodo.relational.connection.Connection;
import org.komodo.relational.connection.internal.ConnectionImpl;
import org.komodo.relational.internal.RelationalChildRestrictedObject;
import org.komodo.relational.model.Model;
import org.komodo.relational.vdb.ModelSource;
import org.komodo.spi.KException;
import org.komodo.spi.lexicon.datavirt.DataVirtLexicon;
import org.komodo.spi.lexicon.vdb.VdbLexicon;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.KomodoType;
import org.komodo.spi.repository.Property;
import org.komodo.spi.repository.PropertyValueType;
import org.komodo.spi.repository.Repository;
import org.komodo.spi.repository.Repository.UnitOfWork;
import org.komodo.spi.repository.Repository.UnitOfWork.State;
import org.komodo.utils.ArgCheck;

/**
 * An implementation of a VDB model source.
 */
public final class ModelSourceImpl extends RelationalChildRestrictedObject implements ModelSource {

    /**
     * @param uow
     *        the transaction (cannot be <code>null</code> or have a state that is not {@link State#NOT_STARTED})
     * @param repository
     *        the repository where the relational object exists (cannot be <code>null</code>)
     * @param workspacePath
     *        the workspace path (cannot be empty)
     * @throws KException
     *         if an error occurs
     */
    public ModelSourceImpl( final UnitOfWork uow,
                            final Repository repository,
                            final String workspacePath ) throws KException {
        super( uow, repository, workspacePath );
    }

    @Override
    public KomodoType getTypeIdentifier( UnitOfWork uow ) {
        return ModelSource.IDENTIFIER;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.vdb.ModelSource#getJndiName(org.komodo.spi.repository.Repository.UnitOfWork)
     */
    @Override
    public String getJndiName( final UnitOfWork uow ) throws KException {
        return getObjectProperty( uow, PropertyValueType.STRING, "getJndiName", VdbLexicon.Source.JNDI_NAME ); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.internal.RelationalObjectImpl#getParent(org.komodo.spi.repository.Repository.UnitOfWork)
     */
    @Override
    public Model getParent( final UnitOfWork transaction ) throws KException {
        ArgCheck.isNotNull( transaction, "transaction" ); //$NON-NLS-1$
        ArgCheck.isTrue( ( transaction.getState() == State.NOT_STARTED ), "transaction state must be NOT_STARTED" ); //$NON-NLS-1$

        final KomodoObject grouping = super.getParent( transaction );
        final Model result = Model.RESOLVER.resolve( transaction, grouping.getParent( transaction ) );
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.vdb.ModelSource#getTranslatorName(org.komodo.spi.repository.Repository.UnitOfWork)
     */
    @Override
    public String getTranslatorName( final UnitOfWork uow ) throws KException {
        return getObjectProperty( uow, PropertyValueType.STRING, "getTranslatorName", VdbLexicon.Source.TRANSLATOR ); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.vdb.ModelSource#getAssociatedConnection(org.komodo.spi.repository.Repository.UnitOfWork)
     */
    @Override
    public Connection getAssociatedConnection(final UnitOfWork uow) throws KException {
        if (hasProperty(uow, KomodoLexicon.VdbModelSource.ASSOCIATED_CONNECTION)) {
            final String refId = getProperty(uow, KomodoLexicon.VdbModelSource.ASSOCIATED_CONNECTION).getStringValue(uow);
            final KomodoObject kobj = getRepository().getUsingId(uow, refId);

            if (kobj == null) {
                throw new KException(Messages.getString(Messages.Relational.REFERENCED_RESOURCE_NOT_FOUND,
                                                        DataVirtLexicon.Connection.NODE_TYPE,
                                                        refId));
            }

            return new ConnectionImpl(uow, getRepository(), kobj.getAbsolutePath());
        }

        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.spi.repository.KomodoObject#getTypeId()
     */
    @Override
    public int getTypeId() {
        return TYPE_ID;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.vdb.ModelSource#setJndiName(org.komodo.spi.repository.Repository.UnitOfWork, java.lang.String)
     */
    @Override
    public void setJndiName( final UnitOfWork uow,
                             final String newJndiName ) throws KException {
        setObjectProperty( uow, "setJndiName", VdbLexicon.Source.JNDI_NAME, newJndiName ); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.vdb.ModelSource#setTranslatorName(org.komodo.spi.repository.Repository.UnitOfWork,
     *      java.lang.String)
     */
    @Override
    public void setTranslatorName( final UnitOfWork uow,
                                   final String newTranslatorName ) throws KException {
        setObjectProperty( uow, "setTranslatorName", VdbLexicon.Source.TRANSLATOR, newTranslatorName ); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.vdb.ModelSource#setAssociatedConnection(org.komodo.spi.repository.Repository.UnitOfWork,
     *      org.komodo.relational.Connection)
     */
    @Override
    public void setAssociatedConnection( final UnitOfWork uow,
                                   final Connection connection ) throws KException {
        String refId = null;

        if ( connection != null ) {
            Property uuidProperty = getObjectFactory().getId(uow, connection);
            if (uuidProperty == null) {
                String msg = Messages.getString(Messages.Relational.NO_UUID_PROPERTY, connection.getName(uow));
                throw new KException(msg);
            }

            refId = uuidProperty.getStringValue(uow);
        }

        setObjectProperty( uow, "setAssociatedConnection", KomodoLexicon.VdbModelSource.ASSOCIATED_CONNECTION, refId); //$NON-NLS-1$
    }

}
