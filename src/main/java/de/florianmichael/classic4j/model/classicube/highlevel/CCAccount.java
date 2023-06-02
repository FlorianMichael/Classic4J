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

package de.florianmichael.classic4j.model.classicube.highlevel;

import com.google.gson.JsonObject;
import de.florianmichael.classic4j.model.CookieStore;

public class CCAccount {
    public final CookieStore cookieStore = new CookieStore();

    public String token;
    private final String username;
    private final String password;

    public CCAccount(final String username, final String password) {
        this(null, username, password);
    }

    public CCAccount(final String token, final String username, final String password) {
        this.token = token;
        this.username = username;
        this.password = password;
    }

    public JsonObject asJson() {
        final JsonObject object = new JsonObject();

        object.addProperty("token", this.token);
        object.addProperty("username", this.username);
        object.addProperty("password", this.password);

        return object;
    }

    public static CCAccount fromJson(final JsonObject object) {
        String token = null;
        if (object.has("token"))
            token = object.get("token").getAsString();

        return new CCAccount(
                token,
                object.get("username").getAsString(),
                object.get("password").getAsString()
        );
    }

    public String token() {
        return token;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    @Override
    public String toString() {
        return "CCAccount{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
