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

package de.florianmichael.classic4j.model.betacraft;

import java.util.Arrays;

public enum BCVersion {
    RELEASE(new String[]{"r", "1" /* Hacky I know, but it works */}),
    CLASSIC(new String[]{"c"}),
    INDEV(new String[]{"mp-in-", "in-", "indev"}),
    INFDEV(new String[]{"infdev"}),
    // 12-year-old server owners try not to give their server a non-compliant "custom" version challenge (Quite impossible)
    ALPHA(new String[]{"a", "nsss"}),
    BETA(new String[]{"b"}),
    UNKNOWN(new String[0]);

    final String[] versionPrefixes;

    BCVersion(final String[] versionPrefixes) {
        this.versionPrefixes = versionPrefixes;
    }

    public String[] versionPrefixes() {
        final String[] copy = new String[versionPrefixes.length];
        System.arraycopy(versionPrefixes, 0, copy, 0, versionPrefixes.length);

        return copy;
    }

    public static BCVersion fromString(final String versionString) {
        final String lowercaseVersionString = versionString.toLowerCase();

        return Arrays.stream(BCVersion.values())
                .filter(v -> Arrays.stream(v.versionPrefixes).anyMatch(lowercaseVersionString::startsWith))
                .findFirst()
                .orElse(BCVersion.UNKNOWN);
    }

    @Override
    public String toString() {
        return "Version{" +
                "versionPrefixes=" + Arrays.toString(versionPrefixes) +
                '}';
    }
}
