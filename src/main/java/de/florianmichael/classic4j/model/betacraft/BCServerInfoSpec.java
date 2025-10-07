/*
 * This file is part of Classic4J - https://github.com/FlorianMichael/Classic4J
 * Copyright (C) 2023-2025 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
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

package de.florianmichael.classic4j.model.betacraft;

import de.florianmichael.classic4j.model.betacraft.impl.Player;
import de.florianmichael.classic4j.model.betacraft.impl.Software;
import java.util.Optional;

/**
 * This interface represents a BetaCraft server info specification. For version specific fields, cast down to the implementation class.
 *
 * @see de.florianmichael.classic4j.model.betacraft.impl.BCServerInfov2
 */
public interface BCServerInfoSpec {

    String name();

    String description();

    default Optional<byte[]> icon() {
        return Optional.empty();
    }

    boolean isPublic();

    int playerCount();

    Player[] players();

    int playerLimit();

    boolean onlineMode();

    String gameVersion();

    String v1Version();

    String socket();

    String protocol();

    BCVersionCategory versionCategory();

    Software software();

    long lastPingTime();

}
