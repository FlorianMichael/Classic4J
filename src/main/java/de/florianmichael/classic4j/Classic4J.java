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
package de.florianmichael.classic4j;

import de.florianmichael.classic4j.api.ExternalInterface;
import de.florianmichael.classic4j.handler.BetaCraftHandler;
import de.florianmichael.classic4j.handler.ClassiCubeHandler;

public class Classic4J {
    private final BetaCraftHandler betaCraftHandler = new BetaCraftHandler(this);
    private final ClassiCubeHandler classiCubeHandler = new ClassiCubeHandler(this);

    public ExternalInterface externalInterface = serverId -> {
        // Not implemented
    };

    public Classic4J(final ExternalInterface externalInterface) {
        if (externalInterface != null) this.externalInterface = externalInterface;
    }

    public BetaCraftHandler betaCraftHandler() {
        return betaCraftHandler;
    }

    public ClassiCubeHandler classiCubeHandler() {
        return classiCubeHandler;
    }
}
