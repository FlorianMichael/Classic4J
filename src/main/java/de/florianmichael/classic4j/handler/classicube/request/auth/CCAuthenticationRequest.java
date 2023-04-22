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
package de.florianmichael.classic4j.handler.classicube.request.auth;

import de.florianmichael.classic4j.handler.classicube.request.ClassiCubeRequest;
import de.florianmichael.classic4j.handler.classicube.response.auth.CCAuthenticationResponse;
import de.florianmichael.classic4j.model.classicube.CCAccount;

import java.util.concurrent.CompletableFuture;

public abstract class CCAuthenticationRequest extends ClassiCubeRequest {

    protected CCAuthenticationRequest(CCAccount account) {
        super(account);
    }

    public abstract CompletableFuture<CCAuthenticationResponse> send();
}
