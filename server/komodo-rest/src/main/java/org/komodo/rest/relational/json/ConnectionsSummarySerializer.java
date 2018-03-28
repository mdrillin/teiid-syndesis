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
package org.komodo.rest.relational.json;

import static org.komodo.rest.relational.json.KomodoJsonMarshaller.BUILDER;

import java.io.IOException;

import org.komodo.rest.relational.connection.RestConnection;
import org.komodo.rest.relational.response.RestConnectionsSummary;
import org.komodo.rest.relational.response.RestNamedVdbStatus;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * A GSON serializer/deserializer for a {@link RestConnectionsSummary}.
 */
public final class ConnectionsSummarySerializer  extends BasicEntitySerializer< RestConnectionsSummary > {

    /**
     * {@inheritDoc}
     * 
     * @see org.komodo.rest.relational.json.BasicEntitySerializer#createEntity()
     */
    @Override
    protected RestConnectionsSummary createEntity() {
        return new RestConnectionsSummary();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.komodo.rest.relational.json.BasicEntitySerializer#isComplete(org.komodo.rest.RestBasicEntity)
     */
    @Override
    protected boolean isComplete( final RestConnectionsSummary entity ) {
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.komodo.rest.relational.json.BasicEntitySerializer#readExtension(java.lang.String, org.komodo.rest.RestBasicEntity, com.google.gson.stream.JsonReader)
     */
    @Override
    protected String readExtension( final String name,
                                    final RestConnectionsSummary summary,
                                    final JsonReader in ) {
        if ( RestConnectionsSummary.CONNECTIONS_LABEL.equals( name ) ) {
            final RestConnection[] connections = BUILDER.fromJson( in, RestConnection[].class );
            summary.setConnections( connections );
            return Integer.toString( connections.length );
        }

        if ( RestConnectionsSummary.STATUSES_LABEL.equals( name ) ) {
            final RestNamedVdbStatus[] statuses = BUILDER.fromJson( in, RestNamedVdbStatus[].class );
            summary.setStatuses( statuses );
            return Integer.toString( statuses.length );
        }

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.komodo.rest.relational.json.BasicEntitySerializer#writeExtensions(com.google.gson.stream.JsonWriter, org.komodo.rest.RestBasicEntity)
     */
    @Override
    protected void writeExtensions( final JsonWriter out,
                                    final RestConnectionsSummary summary ) throws IOException {
        if ( summary.hasConnections() ) {
            out.name( RestConnectionsSummary.CONNECTIONS_LABEL );
            out.beginArray();

            for ( final RestConnection connection : summary.getConnections() ) {
            	BUILDER.getAdapter( RestConnection.class ).write( out, connection );
            }
        }

        if ( summary.hasConnections() ) {
            out.name( RestConnectionsSummary.STATUSES_LABEL );
            out.beginArray();

            for ( final RestNamedVdbStatus status : summary.getStatuses() ) {
            	BUILDER.getAdapter( RestNamedVdbStatus.class ).write( out, status );
            }
        }
    }

}
