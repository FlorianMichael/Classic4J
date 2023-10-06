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

package de.florianmichael.classic4j.model.betacraft;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import de.florianmichael.classic4j.BetaCraftHandler;
import java.net.http.HttpClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a list of servers on the BetaCraft server list. It is used to parse the HTML document returned by the BetaCraft server list.
 *
 * @param servers The servers on the BetaCraft server list.
 */
public record BCServerList(List<BCServerInfo> servers) {

    public static CompletableFuture<BCServerList> get(final HttpClient httpClient, final Gson gson) {
        return CompletableFuture.supplyAsync(() -> new BCServerList(gson.fromJson(httpClient.sendAsync(HttpRequest.newBuilder()
                   .uri(BetaCraftHandler.SERVER_LIST)
                   .build(), BodyHandlers.ofString()).join().body(), JsonArray.class)
               .asList()
               .stream()
               .map(element -> gson.fromJson(element, BCServerInfo.class))
               .toList())
        );
    }

    @Override
    public List<BCServerInfo> servers() {
        return Collections.unmodifiableList(this.servers);
    }

    @Deprecated
    public List<BCServerInfo> serversOfVersion(final BCVersionCategory version) {
        return serversOfVersionCategory(version);
    }

    public List<BCServerInfo> serversOfVersionCategory(final BCVersionCategory version) {
        final List<BCServerInfo> serverListCopy = this.servers();

        return serverListCopy.stream().filter(s -> s.version().equals(version)).toList();
    }

    public List<BCServerInfo> serversWithOnlineMode(final boolean on) {
        return this.servers().stream().filter(s -> s.onlineMode() == on).toList();
    }

    @Deprecated
    public List<BCServerInfo> withGameVersion(final String gameVersion) {
        return this.withConnectVersion(gameVersion);
    }

    public List<BCServerInfo> withConnectVersion(final String connectVersion) {
        return this.servers().stream().filter(s -> s.connectVersion().equals(connectVersion)).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCServerList that = (BCServerList) o;
        return Objects.equals(servers, that.servers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(servers);
    }

    @Override
    public String toString() {
        return "BCServerList{" +
                "servers=" + servers +
                '}';
    }
}
