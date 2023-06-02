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

package de.florianmichael.classic4j.model.classicube;

import com.google.gson.annotations.SerializedName;

public class CCServerInfo {
    @SerializedName("hash") private final String hash;
    @SerializedName("maxplayers") private final int maxPlayers;
    @SerializedName("name") private final String name;
    @SerializedName("players") private final int players;
    @SerializedName("software") private final String software;
    @SerializedName("uptime") private final long uptime;
    @SerializedName("country_abbr") private final String countryCode;
    @SerializedName("web") private final boolean web;
    @SerializedName("featured") private final boolean featured;
    @SerializedName("ip") private final String ip;
    @SerializedName("port") private final int port;
    @SerializedName("mppass") private final String mpPass;

    public CCServerInfo(String hash, int maxPlayers, String name, int players, String software, long uptime, String countryCode, boolean web, boolean featured, String ip, int port, String mpPass) {
        this.hash = hash;
        this.maxPlayers = maxPlayers;
        this.name = name;
        this.players = players;
        this.software = software;
        this.uptime = uptime;
        this.countryCode = countryCode;
        this.web = web;
        this.featured = featured;
        this.ip = ip;
        this.port = port;
        this.mpPass = mpPass;
    }

    public String hash() {
        return hash;
    }

    public int maxPlayers() {
        return maxPlayers;
    }

    public String name() {
        return name;
    }

    public int players() {
        return players;
    }

    public String software() {
        return software;
    }

    public long uptime() {
        return uptime;
    }

    public String countryCode() {
        return countryCode;
    }

    public boolean web() {
        return web;
    }

    public boolean featured() {
        return featured;
    }

    public String ip() {
        return ip;
    }

    public int port() {
        return port;
    }

    public String mpPass() {
        return mpPass;
    }

    @Override
    public String toString() {
        return "CCServerInfo{" +
                "hash='" + hash + '\'' +
                ", maxPlayers=" + maxPlayers +
                ", name='" + name + '\'' +
                ", players=" + players +
                ", software='" + software + '\'' +
                ", uptime=" + uptime +
                ", countryCode='" + countryCode + '\'' +
                ", web=" + web +
                ", featured=" + featured +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", mpPass='" + mpPass + '\'' +
                '}';
    }
}
