/*
 * Copyright Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags and
 * the COPYRIGHT.txt file distributed with this work.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.komodo.relational.profile;

import org.komodo.relational.RelationalObject;
import org.komodo.spi.KException;
import org.komodo.spi.repository.KomodoType;
import org.komodo.spi.repository.UnitOfWork;

/**
 * Represents an user profile.
 */
public interface Profile extends RelationalObject {

    /**
     * The type identifier.
     */
    int TYPE_ID = Profile.class.hashCode();

    /**
     * Identifier of this object
     */
    KomodoType IDENTIFIER = KomodoType.PROFILE;

    /**
     * An empty array of Profiles
     */
    Profile[] NO_PROFILES = new Profile[0];

    /**
     *
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not {@link State#NOT_STARTED})
     * @param stateId the id of the view editor state
     * @return the new view editor state (never <code>null</code>)
     * @throws KException
     *          if an error occurs
     */
    ViewEditorState addViewEditorState(final UnitOfWork transaction,  String stateId) throws KException;

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not {@link State#NOT_STARTED})
     * @param namePatterns
     *        optional name patterns (can be <code>null</code> or empty but cannot have <code>null</code> or empty elements)
     *
     * @return the view editor states (never <code>null</code> but can be empty)
     * @throws KException
     *         if an error occurs
     */
    ViewEditorState[] getViewEditorStates( final UnitOfWork transaction, final String... namePatterns ) throws KException;

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not {@link State#NOT_STARTED})
     * @param viewEditorStateId
     *        the id of the viewEditorState being removed (cannot be empty)
     * @throws KException
     *         if an error occurs
     */
    void removeViewEditorState( final UnitOfWork transaction,
                         final String viewEditorStateId ) throws KException;
}
