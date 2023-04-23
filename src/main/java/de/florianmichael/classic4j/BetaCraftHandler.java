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

import de.florianmichael.classic4j.request.betacraft.BCServerListRequest;
import de.florianmichael.classic4j.model.betacraft.BCServerList;

import java.net.URI;
import java.util.function.Consumer;

public class BetaCraftHandler {
    public final static URI SERVER_LIST = URI.create("https://betacraft.uk/serverlist");

    public static void requestServerList(final Consumer<BCServerList> complete) {
        requestServerList(complete, Throwable::printStackTrace);
    }

    public static void requestServerList(final Consumer<BCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        BCServerListRequest.send().whenComplete((bcServerList, throwable) -> {
            if (throwable != null) {
                throwableConsumer.accept(throwable);
                return;
            }
            complete.accept(bcServerList);
        });
    }
}