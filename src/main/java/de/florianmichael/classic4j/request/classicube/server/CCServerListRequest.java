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
package de.florianmichael.classic4j.request.classicube.server;

import de.florianmichael.classic4j.ClassiCubeHandler;
import de.florianmichael.classic4j.model.classicube.CCServerList;
import de.florianmichael.classic4j.model.classicube.highlevel.CCAccount;
import de.florianmichael.classic4j.util.WebRequests;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class CCServerListRequest {

    public static CompletableFuture<CCServerList> send(final CCAccount account) {
        return CompletableFuture.supplyAsync(() -> {
            final HttpRequest request = WebRequests.buildWithCookies(account.cookieStore, HttpRequest.newBuilder().GET().uri(ClassiCubeHandler.SERVER_LIST_INFO_URI));

            final HttpResponse<String> response = WebRequests.HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();

            WebRequests.updateCookies(account.cookieStore, response);

            final String body = response.body();
            return CCServerList.fromJson(body);
        });
    }
}
