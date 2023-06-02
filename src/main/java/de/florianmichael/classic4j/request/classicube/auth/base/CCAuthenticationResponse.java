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

package de.florianmichael.classic4j.request.classicube.auth.base;

import de.florianmichael.classic4j.ClassiCubeHandler;
import de.florianmichael.classic4j.model.classicube.highlevel.CCError;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class CCAuthenticationResponse {
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
        return ClassiCubeHandler.GSON.fromJson(json, CCAuthenticationResponse.class);
    }
}
