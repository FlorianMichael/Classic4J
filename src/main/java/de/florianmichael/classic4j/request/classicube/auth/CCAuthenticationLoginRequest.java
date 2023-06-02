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

package de.florianmichael.classic4j.request.classicube.auth;

import de.florianmichael.classic4j.ClassiCubeHandler;
import de.florianmichael.classic4j.request.classicube.auth.base.CCAuthenticationResponse;
import de.florianmichael.classic4j.model.classicube.highlevel.CCAccount;
import de.florianmichael.classic4j.model.classicube.highlevel.CCAuthenticationData;
import de.florianmichael.classic4j.util.Pair;
import de.florianmichael.classic4j.util.WebRequests;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class CCAuthenticationLoginRequest {

    public static CompletableFuture<CCAuthenticationResponse> send(final CCAccount account, final CCAuthenticationResponse previousResponse, final String loginCode) {
        return CompletableFuture.supplyAsync(() -> {
            final CCAuthenticationData authenticationData = new CCAuthenticationData(account.username(), account.password(), previousResponse.token, loginCode);

            final String requestBody = WebRequests.createRequestBody(
                    new Pair<>("username", authenticationData.username()),
                    new Pair<>("password", authenticationData.password()),
                    new Pair<>("token", authenticationData.previousToken()),
                    new Pair<>("login_code", authenticationData.loginCode())
            );

            final HttpRequest request = WebRequests.buildWithCookies(account.cookieStore, HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(ClassiCubeHandler.AUTHENTICATION_URI)
                    .header("content-type", "application/x-www-form-urlencoded"));

            final HttpResponse<String> response = WebRequests.HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            WebRequests.updateCookies(account.cookieStore, response);

            final String responseBody = response.body();
            return CCAuthenticationResponse.fromJson(responseBody);
        });
    }
}
