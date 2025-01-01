/*
 * This file is part of Classic4J - https://github.com/FlorianMichael/Classic4J
 * Copyright (C) 2023-2025 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.classic4j.model.classicube.server;

import de.florianmichael.classic4j.ClassiCubeHandler;

import java.util.List;

/**
 * This class represents a list of servers on the ClassiCube server list. It is used by {@link ClassiCubeHandler}.
 * @param servers The servers on the ClassiCube server list.
 */
public record CCServerList(List<CCServerInfo> servers) {

    /**
     * Creates a new {@link CCServerList} from the given JSON string.
     *
     * @param json The JSON string to create the {@link CCServerList} from.
     * @return The created {@link CCServerList}.
     */
    public static CCServerList fromJson(final String json) {
        return ClassiCubeHandler.GSON.fromJson(json, CCServerList.class);
    }

}
