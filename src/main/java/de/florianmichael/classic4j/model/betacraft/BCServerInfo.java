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


import com.google.gson.annotations.SerializedName;

/**
 * This class represents a server on the BetaCraft server list.
 */
public record BCServerInfo(String name, String description, String icon, @SerializedName("is_public") boolean isPublic,
                           @SerializedName("online_players") int playerCount, @SerializedName("player_names") String playerNames, @SerializedName("max_players") int playerLimit,
                           @SerializedName("connect_online_mode") boolean onlineMode, @SerializedName("connect_version") String connectVersion, @SerializedName("connect_socket") String socketAddress, @SerializedName("connect_protocol") String protocol,
                           BCVersionCategory version, @SerializedName("software_name") String softwareName, @SerializedName("software_version") String softwareVersion,
                           @SerializedName("last_ping_time") long lastPingTime) {
}
