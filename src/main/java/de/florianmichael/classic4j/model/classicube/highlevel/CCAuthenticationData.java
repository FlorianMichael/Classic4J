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
