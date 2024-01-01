/*
 * This file is part of Classic4J - https://github.com/FlorianMichael/Classic4J
 * Copyright (C) 2023-2024 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
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
import de.florianmichael.classic4j.util.WebUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class is used to request information about a server from the ClassiCube server list. It is used by {@link de.florianmichael.classic4j.ClassiCubeHandler}.
 */
public class CCServerInfoRequest {

    private static URI generateUri(final List<String> serverHashes) {
        final String joined = String.join(",", serverHashes);

        return ClassiCubeHandler.SERVER_INFO_URI.resolve(joined);
    }

    /**
     * Sends a request to the ClassiCube server list to get information about the given servers. The response contains information about the server, such as the name, the MOTD, the number of players, etc.
     * @param account      The account to use for the request.
     * @param serverHashes The hashes of the servers to get information about.
     * @return             A CompletableFuture containing the response.
     */
    public static CompletableFuture<CCServerList> send(final HttpClient client, final CCAccount account, final List<String> serverHashes) {
        return CompletableFuture.supplyAsync(() -> {
            final URI uri = generateUri(serverHashes);

            final HttpRequest request = WebUtils.buildWithCookies(account.cookieStore, HttpRequest.newBuilder().GET().uri(uri));
            final HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            WebUtils.updateCookies(account.cookieStore, response);

            final String body = response.body();

            return CCServerList.fromJson(body);
        });
    }

}
