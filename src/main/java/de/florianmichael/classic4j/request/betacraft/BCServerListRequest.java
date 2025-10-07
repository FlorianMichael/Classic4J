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

package de.florianmichael.classic4j.request.betacraft;

import com.google.gson.Gson;
import de.florianmichael.classic4j.BetaCraftHandler;
import de.florianmichael.classic4j.model.betacraft.BCServerList;
import de.florianmichael.classic4j.model.betacraft.BCServerInfo;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

public final class BCServerListRequest {

    /**
     * Sends the request to the BetaCraft server list and returns the response as a {@link BCServerList} object.
     *
     * @param client The HttpClient to use for the request.
     * @param gson   The Gson instance to use for the request.
     * @return A CompletableFuture containing the response.
     */
    public static CompletableFuture<BCServerList> send(final HttpClient client, final Gson gson) {
        return BCServerList.get(client, gson, BetaCraftHandler.SERVER_LIST_URI, BCServerInfo.class);
    }

}
