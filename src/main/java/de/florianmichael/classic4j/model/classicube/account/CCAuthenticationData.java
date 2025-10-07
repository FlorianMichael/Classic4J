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

package de.florianmichael.classic4j.model.classicube.account;

import de.florianmichael.classic4j.ClassiCubeHandler;

/**
 * This class represents the data needed to authenticate with the ClassiCube server list. It is used by {@link ClassiCubeHandler}.
 */
public record CCAuthenticationData(String username, String password, String previousToken, String loginCode) {

    public CCAuthenticationData(final String username, final String password, final String previousToken) {
        this(username, password, previousToken, null);
    }

    /**
     * Creates a new {@link CCAuthenticationData} with the given loginCode. This is used for two-factor authentication.
     *
     * @param loginCode The login code.
     * @return The created {@link CCAuthenticationData}.
     */
    public CCAuthenticationData withLoginCode(final String loginCode) {
        return new CCAuthenticationData(this.username, this.password, this.previousToken, loginCode);
    }

}
