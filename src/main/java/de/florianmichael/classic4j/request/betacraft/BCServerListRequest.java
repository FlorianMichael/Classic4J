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

package de.florianmichael.classic4j.request.betacraft;

import de.florianmichael.classic4j.BetaCraftHandler;
import de.florianmichael.classic4j.model.betacraft.BCServerList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.CompletableFuture;

/**
 * This class is used to request the server list from the BetaCraft server list. It is used by {@link BetaCraftHandler}.
 */
public class BCServerListRequest {

    /**
     * Sends the request to the BetaCraft server list.
     *
     * @return A {@link CompletableFuture} that will be completed with the {@link BCServerList} when the request is finished. If the request fails, it will be completed with null.
     */
    public static CompletableFuture<BCServerList> send() {
        return CompletableFuture.supplyAsync(() -> {
            Document document;

            try {
                document = Jsoup.connect(BetaCraftHandler.SERVER_LIST.toString())
                        .userAgent("Java/" + Runtime.version())
                        .header("Accept", "text/html, image/gif, image/jpeg, ; q=.2,/*; q=.2")
                        .post()
                        .quirksMode(Document.QuirksMode.quirks);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return BCServerList.fromDocument(document);
        });
    }
}
