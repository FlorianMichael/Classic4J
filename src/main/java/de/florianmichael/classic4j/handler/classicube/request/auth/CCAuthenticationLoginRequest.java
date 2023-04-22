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
import de.florianmichael.classic4j.model.classicube.CCAuthenticationData;
import de.florianmichael.classic4j.util.Pair;
import de.florianmichael.classic4j.util.WebRequests;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class CCAuthenticationLoginRequest extends CCAuthenticationRequest {
    private final CCAuthenticationData authenticationData;

    public CCAuthenticationLoginRequest(CCAuthenticationResponse previousResponse, CCAccount account) {
        this(previousResponse, account, null);
    }

    public CCAuthenticationLoginRequest(CCAuthenticationResponse previousResponse, CCAccount account, String loginCode) {
        super(account);
        this.authenticationData = new CCAuthenticationData(account.username(), account.password(), previousResponse.token, loginCode);
    }

    @Override
    public CompletableFuture<CCAuthenticationResponse> send() {
        return CompletableFuture.supplyAsync(() -> {
            final String requestBody = WebRequests.createRequestBody(
                    new Pair<>("username", authenticationData.username()),
                    new Pair<>("password", authenticationData.password()),
                    new Pair<>("token", authenticationData.previousToken()),
                    new Pair<>("login_code", authenticationData.loginCode())
            );

            final HttpRequest request = this.buildWithCookies(HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(ClassiCubeHandler.AUTHENTICATION_URI)
                    .header("content-type", "application/x-www-form-urlencoded"));

            final HttpResponse<String> response = WebRequests.HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            this.updateCookies(response);

            final String responseBody = response.body();
            return CCAuthenticationResponse.fromJson(responseBody);
        });
    }
}
