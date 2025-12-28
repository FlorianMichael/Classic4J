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

package de.florianmichael.classic4j.model.classicube.account;

import com.google.gson.JsonObject;
import de.florianmichael.classic4j.util.CookieStore;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class represents an account on the ClassiCube server list. It is used by {@link de.florianmichael.classic4j.ClassiCubeHandler}.
 */
public final class CCAccount {

    public final CookieStore cookieStore = new CookieStore(new HashMap<>());
    private final String username;
    private final String password;
    public String token;

    public CCAccount(final String username, final String password) {
        this(null, username, password);
    }

    public CCAccount(final String token, final String username, final String password) {
        this.token = token;
        this.username = username;
        this.password = password;
    }

    /**
     * Creates a new {@link CCAccount} from the given JSON object.
     *
     * @param object The JSON object to create the {@link CCAccount} from.
     * @return The created {@link CCAccount}.
     */
    public static CCAccount fromJson(final JsonObject object) {
        String token = null;
        if (object.has("token")) token = object.get("token").getAsString();

        return new CCAccount(token, object.get("username").getAsString(), object.get("password").getAsString());
    }

    /**
     * Converts this {@link CCAccount} to a JSON object.
     *
     * @return The JSON object.
     */
    public JsonObject asJson() {
        final JsonObject object = new JsonObject();

        object.addProperty("token", this.token);
        object.addProperty("username", this.username);
        object.addProperty("password", this.password);

        return object;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CCAccount ccAccount = (CCAccount) o;
        return Objects.equals(cookieStore, ccAccount.cookieStore) && Objects.equals(token, ccAccount.token) && Objects.equals(username, ccAccount.username) && Objects.equals(password, ccAccount.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookieStore, token, username, password);
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
