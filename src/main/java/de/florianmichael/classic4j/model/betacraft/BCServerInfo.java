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

public class BCServerInfo {
    private final String name;
    private final int playerCount;
    private final int playerLimit;
    private final String host;
    private final int port;
    private final BCVersion version;
    private final boolean onlineMode;
    private final String joinUrl;
    private final String gameVersion;

    public BCServerInfo(final String name, final int playerCount, final int playerLimit, final String host, final int port, final BCVersion version, final boolean onlineMode, final String joinUrl, final String gameVersion) {
        this.name = name.trim();
        this.playerCount = playerCount;
        this.playerLimit = playerLimit;
        this.host = host;
        this.port = port;
        this.version = version;
        this.onlineMode = onlineMode;
        this.joinUrl = joinUrl;
        this.gameVersion = gameVersion;
    }

    public String name() {
        return name;
    }

    public int playerCount() {
        return playerCount;
    }

    public int playerLimit() {
        return playerLimit;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

    public BCVersion version() {
        return version;
    }

    public boolean onlineMode() {
        return onlineMode;
    }

    public String joinUrl() {
        return joinUrl;
    }

    public String gameVersion() {
        return gameVersion;
    }

    @Override
    public String toString() {
        return "BCServerInfo{" +
                "name='" + name + '\'' +
                ", playerCount=" + playerCount +
                ", playerLimit=" + playerLimit +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", version=" + version +
                ", onlineMode=" + onlineMode +
                ", joinUrl='" + joinUrl + '\'' +
                ", gameVersion='" + gameVersion + '\'' +
                '}';
    }
}
