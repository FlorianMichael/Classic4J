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
