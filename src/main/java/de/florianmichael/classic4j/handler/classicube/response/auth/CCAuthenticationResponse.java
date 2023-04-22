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
package de.florianmichael.classic4j.handler.classicube.response.auth;

import de.florianmichael.classic4j.handler.classicube.response.ClassiCubeResponse;
import de.florianmichael.classic4j.model.classicube.CCError;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class CCAuthenticationResponse extends ClassiCubeResponse {
    public final String token;
    public final String username;
    public final boolean authenticated;
    public final Set<String> errors;

    public CCAuthenticationResponse(String token, String username, boolean authenticated, Set<String> errors) {
        this.token = token;
        this.username = username;
        this.authenticated = authenticated;
        this.errors = errors;
    }

    public boolean shouldError() {
        return errors.size() > 0;
    }

    public String getErrorDisplay() {
        final StringBuilder builder = new StringBuilder();

        for (String error : this.errors) {
            builder.append(CCError.valueOf(error.toUpperCase()).description).append("\n");
        }

        return builder.toString()
                .trim();
    }

    public Set<CCError> errors() {
        return this.errors.stream().map(s -> CCError.valueOf(s.toUpperCase(Locale.ROOT))).collect(Collectors.toSet());
    }

    public boolean mfaRequired() {
        return this.errors().stream().anyMatch(e -> e == CCError.LOGIN_CODE);
    }

    public static CCAuthenticationResponse fromJson(final String json) {
        return GSON.fromJson(json, CCAuthenticationResponse.class);
    }
}
