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
package de.florianmichael.classic4j.handler.classicube.request.server;

import de.florianmichael.classic4j.handler.ClassiCubeHandler;
import de.florianmichael.classic4j.handler.classicube.request.ClassiCubeRequest;
import de.florianmichael.classic4j.handler.classicube.response.server.CCServerInfoResponse;
import de.florianmichael.classic4j.model.classicube.CCAccount;
import de.florianmichael.classic4j.util.WebRequests;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CCServerInfoRequest extends ClassiCubeRequest {
    private final Set<String> serverHashes;

    public CCServerInfoRequest(final CCAccount account, final String serverHash) {
        this(account, Set.of(serverHash));
    }

    public CCServerInfoRequest(final CCAccount account, final Set<String> serverHashes) {
        super(account);
        this.serverHashes = serverHashes;
    }

    private URI generateUri() {
        final String joined = String.join(",", serverHashes);

        return ClassiCubeHandler.SERVER_INFO_URI.resolve(joined);
    }

    public CompletableFuture<CCServerInfoResponse> send() {
        return CompletableFuture.supplyAsync(() -> {
            final URI uri = this.generateUri();

            final HttpRequest request = this.buildWithCookies(HttpRequest.newBuilder().GET().uri(uri));
            final HttpResponse<String> response = WebRequests.HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            updateCookies(response);

            final String body = response.body();

            return CCServerInfoResponse.fromJson(body);
        });
    }
}
