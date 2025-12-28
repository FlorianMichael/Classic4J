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

package de.florianmichael.classic4j.util;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CookieStore {

    private final Map<String, String> values;

    public CookieStore(Map<String, String> values) {
        this.values = values;
    }

    public static CookieStore parse(final String value) {
        final char[] characters = value.toCharArray();
        StringBuilder currentKey = new StringBuilder();
        StringBuilder currentValue = new StringBuilder();
        boolean key = true;
        final Map<String, String> values = new HashMap<>();

        for (final char character : characters) {
            if (character == '=' && key) {
                currentValue = new StringBuilder();
                key = false;
                continue;
            }

            if (character == ';' && !key) {
                key = true;
                values.put(currentKey.toString(), currentValue.toString());
                continue;
            }

            if (key) {
                currentKey.append(character);
            } else {
                currentValue.append(character);
            }
        }
        return new CookieStore(values);
    }

    public Map<String, String> getMap() {
        return Collections.unmodifiableMap(values);
    }

    public void merge(CookieStore cookieStore) {
        this.values.putAll(cookieStore.getMap());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        int i = 0;

        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (i != 0) {
                builder.append(";");
            }

            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            i++;
        }

        return builder.toString();
    }

    public void mergeFromResponse(HttpResponse<?> response) {
        final Optional<String> setCookieHeaderOptional = response.headers().firstValue("set-cookie");
        if (setCookieHeaderOptional.isEmpty()) {
            return;
        }
        final String setCookieHeader = setCookieHeaderOptional.get();
        final CookieStore store = CookieStore.parse(setCookieHeader);

        this.merge(store);
    }

    public HttpRequest.Builder appendCookies(HttpRequest.Builder builder) {
        builder.header("Cookie", this.toString());

        return builder;
    }

}
