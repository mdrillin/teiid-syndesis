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
package org.komodo.rest.relational.response;

import java.net.URI;

import org.komodo.rest.RestBasicEntity;
import org.komodo.rest.relational.connection.RestConnection;
import org.komodo.spi.KException;

/**
 * A summary of connections and their statuses.
 */
public final class RestConnectionsSummary extends RestBasicEntity {

    /**
     * JSON label for the connections collection.
     */
    public static final String CONNECTIONS_LABEL = "connections"; // //$NON-NLS-1$

    /**
     * JSON label for the statuses collection.
     */
    public static final String STATUSES_LABEL = "statuses"; // //$NON-NLS-1$

	private RestConnection[] connections;
	private RestNamedVdbStatus[] statuses;

	/**
     * Constructor for use <strong>only</strong> when deserializing.
     */
    public RestConnectionsSummary() {
    	// nothing to do
    }

    /**
     * @param baseUri the base URI of the REST request (cannot be <code>null</code>) 
     * @param connections the connections included in this summary (can be <code>null</code>)
     * @param statuses the statuses included in this summary (can be <code>null</code>)
     * @throws KException if an error occurs
     */
    public RestConnectionsSummary( final URI baseUri,
                                   final RestConnection[] connections,
                                   final RestNamedVdbStatus[] statuses ) throws KException {
        setConnections( connections );
        setStatuses( statuses );
    }

    /**
     * @return the connections included in the summary or an empty collection
     */
    public RestConnection[] getConnections() {
    	return this.connections == null ?  RestConnection.NO_CONNECTIONS : this.connections;
    }

    /**
     * @return the statuses included in the summary or an empty collection
     */
    public RestNamedVdbStatus[] getStatuses() {
    	return this.statuses == null ?  RestNamedVdbStatus.NO_STATUSES : this.statuses;
    }

    /**
     * @return <code>true</code> if there are connections
     */
    public boolean hasConnections() {
    	return ( this.connections != null ) && ( this.connections.length != 0 );
    }

    /**
     * @return <code>true</code> if there are statuses
     */
    public boolean hasStatuses() {
    	return ( this.statuses != null ) && ( this.statuses.length != 0 );
    }

    /**
     * @param connections the connections being set (can be <code>null</code>)
     */
    public void setConnections( final RestConnection[] connections ) {
    	this.connections = connections;
    }

    /**
     * @param status the statuses being set (can be <code>null</code>)
     */
    public void setStatuses( final RestNamedVdbStatus[] statuses ) {
    	this.statuses = statuses;
    }

}
