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

import de.florianmichael.classic4j.handler.ClassiCubeHandler;
import de.florianmichael.classic4j.handler.classicube.response.auth.CCAuthenticationResponse;
import de.florianmichael.classic4j.model.classicube.CCAccount;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class CCAuthenticationTokenRequest extends CCAuthenticationRequest {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public CCAuthenticationTokenRequest(CCAccount account) {
        super(account);
    }

    @Override
    public CompletableFuture<CCAuthenticationResponse> send() {
        return CompletableFuture.supplyAsync(() -> {
            final HttpRequest request = HttpRequest.newBuilder().GET().uri(ClassiCubeHandler.AUTHENTICATION_URI).build();

            final HttpResponse<String> response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            this.updateCookies(response);
            final String responseBody = response.body();
            return CCAuthenticationResponse.fromJson(responseBody);
        });
    }
}
