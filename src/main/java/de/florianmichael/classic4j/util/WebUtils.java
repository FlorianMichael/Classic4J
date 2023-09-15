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

package de.florianmichael.classic4j.util;

import de.florianmichael.classic4j.util.model.CookieStore;
import de.florianmichael.classic4j.util.model.Parameter;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WebUtils {
    public final static HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public static String createRequestBody(final Parameter... parameters) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            if (parameter.name == null || parameter.value == null) continue;

            builder.append(parameter.name).append("=").append(URLEncoder.encode(parameter.value, StandardCharsets.UTF_8));

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
