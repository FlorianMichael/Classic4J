/*
 * This file is part of ClassiCube4J - https://github.com/FlorianMichael/ClassiCube4J
 * Copyright (C) 2023 FlorianMichael/EnZaXD, Allink and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.florianmichael.classic4j.handler.classicube.response.server;


import de.florianmichael.classic4j.handler.classicube.response.ClassiCubeResponse;
import de.florianmichael.classic4j.model.classicube.CCServerInfo;

import java.util.Set;

public class CCServerInfoResponse extends ClassiCubeResponse {
    private final Set<CCServerInfo> servers;

    public CCServerInfoResponse(Set<CCServerInfo> servers) {
        this.servers = servers;
    }

    public Set<CCServerInfo> servers() {
        return servers;
    }

    public static CCServerInfoResponse fromJson(final String json) {
        return GSON.fromJson(json, CCServerInfoResponse.class);
    }
}
