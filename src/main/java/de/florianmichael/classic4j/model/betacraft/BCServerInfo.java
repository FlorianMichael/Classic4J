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

import java.util.Objects;

/**
 * This class represents a server on the BetaCraft server list.
 */
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BCServerInfo that = (BCServerInfo) o;
        return playerCount == that.playerCount && playerLimit == that.playerLimit && port == that.port && onlineMode == that.onlineMode && Objects.equals(name, that.name) && Objects.equals(host, that.host) && version == that.version && Objects.equals(joinUrl, that.joinUrl) && Objects.equals(gameVersion, that.gameVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, playerCount, playerLimit, host, port, version, onlineMode, joinUrl, gameVersion);
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
