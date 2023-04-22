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
package de.florianmichael.classic4j.handler.classicube;

import de.florianmichael.classic4j.model.classicube.highlevel.CCAccount;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class ClassiCubeRequest {
    public final CCAccount account;

    public ClassiCubeRequest(CCAccount account) {
        this.account = account;
    }

    public HttpRequest buildWithCookies(final HttpRequest.Builder builder) {
        return this.account.cookieStore.appendCookies(builder).build();
    }

    public void updateCookies(final HttpResponse<?> response) {
        this.account.cookieStore.mergeFromResponse(response);
    }
}
