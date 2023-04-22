/*
 * This file is part of Classic4J - https://github.com/FlorianMichael/Classic4J
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
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
package de.florianmichael.classic4j.handler.betacraft;

import de.florianmichael.classic4j.handler.BetaCraftHandler;
import de.florianmichael.classic4j.model.betacraft.BCServerList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.CompletableFuture;

public class BCServerListRequest {

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
