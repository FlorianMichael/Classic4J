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

package de.florianmichael.classic4j.model.classicube.server;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * This class represents a server on the Classicube server list. It is used by {@link CCServerList}.
 */
public record CCServerInfo(@SerializedName("hash") String hash, @SerializedName("maxplayers") int maxPlayers,
                           @SerializedName("name") String name, @SerializedName("players") int players,
                           @SerializedName("software") String software, @SerializedName("uptime") long uptime,
                           @SerializedName("country_abbr") String countryCode, @SerializedName("web") boolean web,
                           @SerializedName("featured") boolean featured, @SerializedName("ip") String ip,
                           @SerializedName("port") int port, @SerializedName("mppass") String mpPass) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CCServerInfo that = (CCServerInfo) o;
        return maxPlayers == that.maxPlayers && players == that.players && uptime == that.uptime && web == that.web && featured == that.featured && port == that.port && Objects.equals(hash, that.hash) && Objects.equals(name, that.name) && Objects.equals(software, that.software) && Objects.equals(countryCode, that.countryCode) && Objects.equals(ip, that.ip) && Objects.equals(mpPass, that.mpPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, maxPlayers, name, players, software, uptime, countryCode, web, featured, ip, port, mpPass);
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
