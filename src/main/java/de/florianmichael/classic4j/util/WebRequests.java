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
package de.florianmichael.classic4j.util;

import de.florianmichael.classic4j.model.CookieStore;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WebRequests {
    public final static HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    @SafeVarargs
    public static String createRequestBody(final Pair<String, String>... parameters) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            final Pair<String, String> parameter = parameters[i];
            if (parameter.key() == null || parameter.value() == null) continue;

            builder.append(parameter.key()).append("=").
                    append(URLEncoder.encode(parameter.value(), StandardCharsets.UTF_8));

            if (i != parameters.length - 1) {
                builder.append("&");
            }
        }
        return builder.toString();
    }

    public static HttpRequest buildWithCookies(final CookieStore cookieStore, final HttpRequest.Builder builder) {
        return cookieStore.appendCookies(builder).build();
    }

    public static void updateCookies(final CookieStore cookieStore, final HttpResponse<?> response) {
        cookieStore.mergeFromResponse(response);
    }
}
