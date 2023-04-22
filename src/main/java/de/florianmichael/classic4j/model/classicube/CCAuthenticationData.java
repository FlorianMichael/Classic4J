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

package de.florianmichael.classic4j.model.classicube;

public class CCAuthenticationData {
    private final String username;
    private final String password;
    private final String previousToken;
    private final String loginCode;

    public CCAuthenticationData(final String username, final String password, final String previousToken) {
        this(username, password, previousToken, null);
    }

    public CCAuthenticationData(final String username, final String password, final String previousToken, final String loginCode) {
        this.username = username;
        this.password = password;
        this.previousToken = previousToken;
        this.loginCode = loginCode;
    }

    public CCAuthenticationData withLoginCode(final String loginCode) {
        return new CCAuthenticationData(this.username, this.password, this.previousToken, loginCode);
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String previousToken() {
        return previousToken;
    }

    public String loginCode() {
        return loginCode;
    }

    @Override
    public String toString() {
        return "CCAuthenticationData{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", previousToken='" + previousToken + '\'' +
                ", loginCode='" + loginCode + '\'' +
                '}';
    }
}
