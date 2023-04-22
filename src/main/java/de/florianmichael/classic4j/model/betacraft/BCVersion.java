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
