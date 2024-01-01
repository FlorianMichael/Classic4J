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

package de.florianmichael.classic4j.model.betacraft.impl;

import com.google.gson.annotations.SerializedName;
import de.florianmichael.classic4j.model.betacraft.BCServerInfoSpec;
import de.florianmichael.classic4j.model.betacraft.BCVersionCategory;

import java.util.List;
import java.util.UUID;

public record BCServerInfov1(Ping ping, Info info, boolean online,
                             @SerializedName("version_category") BCVersionCategory versionCategory) implements
        BCServerInfoSpec {

    @Override
    public String name() {
        return this.info.name;
    }

    @Override
    public String description() {
        return this.info.description;
    }

    @Override
    public boolean isPublic() {
        return this.ping.isPublic;
    }

    @Override
    public int playerCount() {
        return this.ping.onlinePlayers;
    }

    @Override
    public List<String> playerNames() {
        return this.ping.players;
    }

    @Override
    public int playerLimit() {
        return this.ping.maxPlayers;
    }

    @Override
    public boolean onlineMode() {
        return this.online;
    }

    @Override
    public String connectVersion() {
        return this.info.version;
    }

    @Override
    public String socketAddress() {
        return this.info.socket;
    }

    @Override
    public String protocol() {
        return this.info.protocol;
    }

    @Override
    public String softwareName() {
        return this.ping.serverName;
    }

    @Override
    public String softwareVersion() {
        return this.ping.serverVersion;
    }

    @Override
    public long lastPingTime() {
        return this.ping.lastPingTime;
    }

    public record Ping(@SerializedName("max_players") int maxPlayers,
                       @SerializedName("online_players") int onlinePlayers,
                       @SerializedName("server_name") String serverName,
                       @SerializedName("server_version") String serverVersion,
                       List<String> players,
                       @SerializedName("last_ping_time") long lastPingTime,
                       @SerializedName("is_public") boolean isPublic) {

    }

    public record Info(@SerializedName("public_id") UUID publicId, String name, String description,
                       String version, String protocol, String socket,
                       @SerializedName("online_mode") boolean onlineMode) {

    }

}
