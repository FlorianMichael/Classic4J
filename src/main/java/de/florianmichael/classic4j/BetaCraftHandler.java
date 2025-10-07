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

package de.florianmichael.classic4j;

import de.florianmichael.classic4j.api.JoinServerInterface;
import de.florianmichael.classic4j.model.betacraft.BCServerList;
import de.florianmichael.classic4j.request.betacraft.BCServerListRequest;
import de.florianmichael.classic4j.util.HttpClientUtils;
import de.florianmichael.classic4j.util.IPUtils;
import java.net.URI;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.function.Consumer;

/**
 * This class provides methods to interact with the BetaCraft API. These API methods are used to request the Multiplayer Pass for a server.
 */
public final class BetaCraftHandler {

    /**
     * The URI of the BetaCraft API.
     */
    public static final URI BETACRAFT_ROOT_URI = URI.create("https://api.betacraft.uk");

    public static final URI SERVER_LIST_URI = BETACRAFT_ROOT_URI.resolve("/v2/server_list");

    /**
     * Authenticates the player with the BetaCraft API.
     *
     * @param joinServerInterface The {@link JoinServerInterface} to use for the request. This is used to send the authentication request.
     */
    public static void authenticate(final JoinServerInterface joinServerInterface) {
        authenticate(joinServerInterface, Throwable::printStackTrace);
    }

    /**
     * Authenticates the player with the BetaCraft API.
     *
     * @param joinServerInterface The {@link JoinServerInterface} to use for the request. This is used to send the authentication request.
     */
    public static void authenticate(final JoinServerInterface joinServerInterface, final Consumer<Throwable> throwableConsumer) {
        try {
            joinServerInterface.sendAuthRequest(sha1(IPUtils.get().getBytes()));
        } catch (Throwable t) {
            throwableConsumer.accept(t);
        }
    }

    /**
     * Requests the server list from the BetaCraft API.
     *
     * @param complete The consumer that will be called when the request is complete.
     */
    public static void requestServerList(final Consumer<BCServerList> complete) {
        requestServerList(complete, Throwable::printStackTrace);
    }

    /**
     * Requests the server list from the BetaCraft API.
     *
     * @param complete          The consumer that will be called when the request is complete.
     * @param throwableConsumer The consumer that will be called when an error occurs.
     */
    private static void requestServerList(final Consumer<BCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        BCServerListRequest.send(HttpClientUtils.HTTP_CLIENT, ClassiCubeHandler.GSON).whenComplete((bcServerList, throwable) -> {
            if (throwable != null) {
                throwableConsumer.accept(throwable);
                return;
            }

            complete.accept(bcServerList);
        });
    }

    /**
     * Hashes the input using SHA-1.
     *
     * @param input The input to hash.
     * @return The hashed input.
     */
    private static String sha1(final byte[] input) {
        try {
            Formatter formatter = new Formatter();
            final byte[] hash = MessageDigest.getInstance("SHA-1").digest(input);
            for (byte b : hash) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
