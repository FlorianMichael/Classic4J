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

package de.florianmichael.classic4j.handler;

import de.florianmichael.classic4j.Classic4J;
import de.florianmichael.classic4j.model.betacraft.BCServerList;
import de.florianmichael.classic4j.util.Pair;
import de.florianmichael.classic4j.util.WebRequests;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class BetaCraftHandler {
    public final static URI GET_MP_PASS = URI.create("http://api.betacraft.uk/getmppass.jsp");
    public final static URI SERVER_LIST = URI.create("https://betacraft.uk/serverlist");

    private final Classic4J classic4J;

    public BetaCraftHandler(Classic4J classic4J) {
        this.classic4J = classic4J;
    }

    public void requestServerList(final Consumer<BCServerList> complete) {
        CompletableFuture.runAsync(() -> {
            Document document;

            try {
                document = Jsoup.connect(SERVER_LIST.toString())
                        .userAgent(classic4J.userAgent)
                        .header("Accept", "text/html, image/gif, image/jpeg, ; q=.2,/*; q=.2")
                        .post()
                        .quirksMode(Document.QuirksMode.quirks);
            } catch (IOException e) {
                complete.accept(null);
                throw new RuntimeException("Failed to get Jsoup document from server list url", e);
            }

            complete.accept(BCServerList.fromDocument(document));
        });
    }

    public void requestMPPass(final String username, final String ip, final int port, final Consumer<String> complete) {
        try {
            final String server = InetAddress.getByName(ip).getHostAddress() + ":" + port;

            this.classic4J.externalInterface.sendAuthRequest(sha1(server.getBytes()));
            final String body = WebRequests.createRequestBody(new Pair<>("user", username), new Pair<>("server", server));

            final HttpRequest request = HttpRequest.
                    newBuilder().
                    POST(HttpRequest.BodyPublishers.ofString(body)).
                    uri(GET_MP_PASS).
                    build();

            AtomicBoolean success = new AtomicBoolean(false);
            WebRequests.HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).whenComplete((stringHttpResponse, throwable) -> {
                if (throwable != null) {
                    throwable.printStackTrace();
                    return;
                }

                complete.accept(stringHttpResponse.body());
                success.set(true);
            });
            if (!success.get()) {
                complete.accept("0");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            complete.accept("0");
        }
    }

    private String sha1(final byte[] input) {
        try {
            return new String(MessageDigest.getInstance("SHA-1").digest(input));
        } catch (Exception e) {
            return null;
        }
    }
}
