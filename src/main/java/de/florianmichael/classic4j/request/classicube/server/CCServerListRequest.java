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

package de.florianmichael.classic4j.request.classicube.server;

import de.florianmichael.classic4j.ClassiCubeHandler;
import de.florianmichael.classic4j.model.classicube.server.CCServerList;
import de.florianmichael.classic4j.model.classicube.account.CCAccount;
import de.florianmichael.classic4j.util.HttpClientUtils;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * This class is used to get the server list from the ClassiCube server list. It is used by {@link de.florianmichael.classic4j.ClassiCubeHandler}.
 */
public class CCServerListRequest {

    /**
     * Sends a request to the ClassiCube server list to get the server list.
     *
     * @param client  The HttpClient to use.
     * @param account The account to use for the request.
     * @return A CompletableFuture containing the response.
     */
    public static CompletableFuture<CCServerList> send(final HttpClient client, final CCAccount account) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpRequest request = HttpClientUtils.buildWithCookies(account.cookieStore, HttpRequest.newBuilder().GET().uri(ClassiCubeHandler.SERVER_LIST_INFO_URI));

            final HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            HttpClientUtils.updateCookies(account.cookieStore, response);

            final String body = response.body();
            return CCServerList.fromJson(body);
        });
    }

}
