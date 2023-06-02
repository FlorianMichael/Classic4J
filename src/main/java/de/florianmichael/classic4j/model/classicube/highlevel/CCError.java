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

public enum CCError {
    TOKEN("Incorrect token. Is your ViaFabricPlus out of date?"),
    USERNAME("Invalid username."),
    PASSWORD("Invalid password."),
    VERIFICATION("User hasn't verified their E-mail address yet."),
    LOGIN_CODE("Multi-factor authentication requested. Please check your E-mail.");

    public final String description;

    CCError(final String description) {
        this.description = description;
    }
}
