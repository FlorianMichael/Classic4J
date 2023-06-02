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
