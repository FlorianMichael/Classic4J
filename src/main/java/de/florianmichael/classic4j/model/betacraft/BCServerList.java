/*
 * This file is part of Classic4J - https://github.com/FlorianMichael/Classic4J
 * Copyright (C) 2023-2026 FlorianMichael/EnZaXD <git@florianmichael.de> and contributors
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

package de.florianmichael.classic4j.model.betacraft;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a list of servers on the BetaCraft server list. It is used to parse the HTML document returned by the BetaCraft server list.
 *
 * @param servers The servers on the BetaCraft server list.
 */
public record BCServerList(List<BCServerInfo> servers) {

    /**
     * Sends a request to the BetaCraft server list and returns the response as a {@link BCServerList} object.
     *
     * @param httpClient The HttpClient to use for the request.
     * @param gson       The Gson instance to use for the request.
     * @param uri        The URI of the request. This is the URI of the BetaCraft server list.
     * @param infoSpec   The class of the server info specification. This is the class that will be used to parse the JSON response.
     * @return A CompletableFuture containing the response.
     */
    public static CompletableFuture<BCServerList> get(final HttpClient httpClient, final Gson gson, final URI uri, final Class<? extends BCServerInfo> infoSpec) {
        return CompletableFuture.supplyAsync(() -> new BCServerList(gson.fromJson(httpClient.sendAsync(HttpRequest.newBuilder(uri).build(), BodyHandlers.ofString()).join().body(), JsonArray.class)
            .asList()
            .stream()
            .map(element -> (BCServerInfo) gson.fromJson(element, infoSpec))
            .toList()
        ));
    }

    @Override
    public List<BCServerInfo> servers() {
        return Collections.unmodifiableList(this.servers);
    }

    /**
     * Returns a list of servers that have the given version category.
     *
     * @param version The version category to filter by.
     * @return A list of servers that have the given version category.
     */
    public List<BCServerInfo> serversOfVersionCategory(final BCVersionCategory version) {
        final List<BCServerInfo> serverListCopy = this.servers();

        return serverListCopy.stream().filter(s -> s.versionCategory().equals(version)).toList();
    }

    /**
     * Returns a list of servers that have the given version category.
     *
     * @param on Whether the servers should be online mode or not.
     * @return A list of servers that have the given version category.
     */
    public List<BCServerInfo> serversWithOnlineMode(final boolean on) {
        return this.servers().stream().filter(s -> s.onlineMode() == on).toList();
    }

    /**
     * Returns a list of servers that have the given version category.
     *
     * @param v1Version The connect version to filter by.
     * @return A list of servers that have the given version category.
     */
    public List<BCServerInfo> withConnectVersion(final String v1Version) {
        return this.servers().stream().filter(s -> s.v1Version().equals(v1Version)).toList();
    }

}
