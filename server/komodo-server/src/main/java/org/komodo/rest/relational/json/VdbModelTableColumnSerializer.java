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
package org.komodo.rest.relational.json;

import org.komodo.rest.relational.response.RestVdbModelTableColumn;

/**
 * A GSON serializer/deserializer for {@link RestVdbModelTableColumn}s.
 */
public final class VdbModelTableColumnSerializer extends BasicEntitySerializer<RestVdbModelTableColumn> {

    @Override
    protected RestVdbModelTableColumn createEntity() {
        return new RestVdbModelTableColumn();
    }
}
