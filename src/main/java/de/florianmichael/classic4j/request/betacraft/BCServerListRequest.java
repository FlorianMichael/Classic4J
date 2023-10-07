/*
 * This file is part of Classic4J - https://github.com/FlorianMichael/Classic4J
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.classic4j.request.betacraft;

import de.florianmichael.classic4j.model.betacraft.BCServerInfoSpec;
import de.florianmichael.classic4j.model.betacraft.impl.BCServerInfov1;
import de.florianmichael.classic4j.model.betacraft.impl.BCServerInfov2;

import java.net.URI;

public enum BCServerListRequest implements BCServerListRequestInterface {
    V1(URI.create("https://api.betacraft.uk/server_list.jsp"), BCServerInfov1.class),
    V2(URI.create("https://api.betacraft.uk/v2/server_list"), BCServerInfov2.class);

    private final URI uri;
    private final Class<? extends BCServerInfoSpec> infoSpec;

    BCServerListRequest(final URI uri, final Class<? extends BCServerInfoSpec> infoSpec) {
        this.uri = uri;
        this.infoSpec = infoSpec;
    }

    @Override
    public URI uri() {
        return this.uri;
    }

    @Override
    public Class<? extends BCServerInfoSpec> infoSpec() {
        return this.infoSpec;
    }
}
