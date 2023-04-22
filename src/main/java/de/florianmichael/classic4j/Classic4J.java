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

import java.util.function.Consumer;

public class Classic4J {
    private final BetaCraftHandler betaCraftHandler = new BetaCraftHandler(this);
    private final ClassiCubeHandler classiCubeHandler = new ClassiCubeHandler(this);

    public String userAgent = "Java/" + Runtime.version();
    public ExternalInterface externalInterface = serverId -> {
        // Not implemented
    };
    public Consumer<Throwable> errorHandler = Throwable::printStackTrace;

    public Classic4J(final String userAgent, final ExternalInterface externalInterface, final Consumer<Throwable> errorHandler) {
        if (userAgent != null) this.userAgent = userAgent;
        if (externalInterface != null) this.externalInterface = externalInterface;
        if (errorHandler != null) this.errorHandler = errorHandler;
    }

    public BetaCraftHandler betaCraftHandler() {
        return betaCraftHandler;
    }

    public ClassiCubeHandler classiCubeHandler() {
        return classiCubeHandler;
    }
}
