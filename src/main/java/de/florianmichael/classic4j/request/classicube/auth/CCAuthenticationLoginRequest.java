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
import de.florianmichael.classic4j.model.classicube.CCAuthenticationResponse;
import de.florianmichael.classic4j.model.classicube.account.CCAccount;
import de.florianmichael.classic4j.model.classicube.account.CCAuthenticationData;
import de.florianmichael.classic4j.util.model.Parameter;
import de.florianmichael.classic4j.util.WebUtils;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a request to log in to the ClassiCube server list. It is used by {@link ClassiCubeHandler}.
 */
public class CCAuthenticationLoginRequest {

    /**
     * Sends a login request to the ClassiCube server list.
     * @param account          The account to log in with.
     * @param previousResponse The previous response.
     * @param loginCode        The login code.
     * @return                 The response.
     */
    public static CompletableFuture<CCAuthenticationResponse> send(final CCAccount account, final CCAuthenticationResponse previousResponse, final String loginCode) {
        return CompletableFuture.supplyAsync(() -> {
            final CCAuthenticationData authenticationData = new CCAuthenticationData(account.username(), account.password(), previousResponse.token, loginCode);

            final String requestBody = WebUtils.createRequestBody(
                    new Parameter("username", authenticationData.username()),
                    new Parameter("password", authenticationData.password()),
                    new Parameter("token", authenticationData.previousToken()),
                    new Parameter("login_code", authenticationData.loginCode())
            );

            final HttpRequest request = WebUtils.buildWithCookies(account.cookieStore, HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(ClassiCubeHandler.AUTHENTICATION_URI)
                    .header("content-type", "application/x-www-form-urlencoded"));

            final HttpResponse<String> response = WebUtils.HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            WebUtils.updateCookies(account.cookieStore, response);

            final String responseBody = response.body();
            return CCAuthenticationResponse.fromJson(responseBody);
        });
    }
}
