/*
 * This file is part of Classic4J - https://github.com/FlorianMichael/Classic4J
 * Copyright (C) 2023-2024 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IPUtils {

    public static String get() throws Exception {
        URL url;
        try {
            url = new URL("http://checkip.amazonaws.com/");
        } catch (Exception e) {
            url = new URL("https://ipv4bot.whatismyipaddress.com/");
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        return bufferedReader.readLine();
    }

}
