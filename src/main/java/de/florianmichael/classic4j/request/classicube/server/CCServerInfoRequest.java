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

package de.florianmichael.classic4j.request.classicube.server;

import de.florianmichael.classic4j.ClassiCubeHandler;
import de.florianmichael.classic4j.model.classicube.CCServerList;
import de.florianmichael.classic4j.model.classicube.highlevel.CCAccount;
import de.florianmichael.classic4j.util.WebRequests;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CCServerInfoRequest {

    private static URI generateUri(final List<String> serverHashes) {
        final String joined = String.join(",", serverHashes);

        return ClassiCubeHandler.SERVER_INFO_URI.resolve(joined);
    }

    public static CompletableFuture<CCServerList> send(final CCAccount account, final List<String> serverHashes) {
        return CompletableFuture.supplyAsync(() -> {
            final URI uri = generateUri(serverHashes);

            final HttpRequest request = WebRequests.buildWithCookies(account.cookieStore, HttpRequest.newBuilder().GET().uri(uri));
            final HttpResponse<String> response = WebRequests.HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            WebRequests.updateCookies(account.cookieStore, response);

            final String body = response.body();

            return CCServerList.fromJson(body);
        });
    }
}
