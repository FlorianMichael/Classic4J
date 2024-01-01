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

package de.florianmichael.classic4j;

import de.florianmichael.classic4j.api.JoinServerInterface;
import de.florianmichael.classic4j.model.betacraft.BCServerList;
import de.florianmichael.classic4j.request.betacraft.BCServerListRequest;
import de.florianmichael.classic4j.util.WebUtils;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * This class provides methods to interact with the BetaCraft API. These API methods are used to request the Multiplayer Pass for a server.
 */
public class BetaCraftHandler {

    public final static URI BETACRAFT_ROOT_URI = URI.create("https://api.betacraft.uk");

    public final static URI GET_MP_PASS = BETACRAFT_ROOT_URI.resolve("/getmppass.jsp");

    /**
     * Requests the Multiplayer Pass for a server from the BetaCraft API.
     *
     * @param username            The username of the player.
     * @param ip                  The IP of the server.
     * @param port                The port of the server.
     * @param joinServerInterface The {@link JoinServerInterface} to use for the request. This is used to send the authentication request.
     * @return The Multiplayer Pass for the server.
     */
    public static String requestMPPass(final String username, final String ip, final int port, final JoinServerInterface joinServerInterface) {
        return requestMPPass(username, ip, port, joinServerInterface, Throwable::printStackTrace);
    }

    /**
     * Requests the Multiplayer Pass for a server from the BetaCraft API.
     *
     * @param username            The username of the player.
     * @param ip                  The IP of the server.
     * @param port                The port of the server.
     * @param joinServerInterface The {@link JoinServerInterface} to use for the request. This is used to send the authentication request.
     * @param throwableConsumer   The consumer that will be called when an error occurs.
     * @return The Multiplayer Pass for the server.
     */
    public static String requestMPPass(final String username, final String ip, final int port, final JoinServerInterface joinServerInterface, final Consumer<Throwable> throwableConsumer) {
        try {
            final String server = InetAddress.getByName(ip).getHostAddress() + ":" + port;

            joinServerInterface.sendAuthRequest(sha1(server.getBytes()));

            final InputStream connection = new URL(GET_MP_PASS + "?user=" + username + "&server=" + server).openStream();
            Scanner scanner = new Scanner(connection);
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.next());
            }
            connection.close();

            if (response.toString().contains("FAILED") || response.toString().contains("SERVER NOT FOUND")) return "0";

            return response.toString();
        } catch (Throwable t) {
            throwableConsumer.accept(t);
            return "0";
        }
    }

    /**
     * Requests the server list from the BetaCraft API.
     *
     * @param complete The consumer that will be called when the request is complete.
     */
    public static void requestV2ServerList(final Consumer<BCServerList> complete) {
        requestV2ServerList(complete, Throwable::printStackTrace);
    }

    /**
     * Requests the server list from the BetaCraft API.
     *
     * @param complete          The consumer that will be called when the request is complete.
     * @param throwableConsumer The consumer that will be called when an error occurs.
     */
    public static void requestV2ServerList(final Consumer<BCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        requestServerList(BCServerListRequest.V2, complete, throwableConsumer);
    }

    /**
     * Requests the server list from the BetaCraft API.
     *
     * @param complete The consumer that will be called when the request is complete.
     */
    public static void requestV1ServerList(final Consumer<BCServerList> complete) {
        requestV1ServerList(complete, Throwable::printStackTrace);
    }

    /**
     * Requests the server list from the BetaCraft API.
     *
     * @param complete          The consumer that will be called when the request is complete.
     * @param throwableConsumer The consumer that will be called when an error occurs.
     */
    public static void requestV1ServerList(final Consumer<BCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        requestServerList(BCServerListRequest.V1, complete, throwableConsumer);
    }

    /**
     * Requests the server list from the BetaCraft API. This method is used internally by {@link #requestV1ServerList(Consumer)} and {@link #requestV2ServerList(Consumer)}.
     *
     * @param request           The {@link BCServerListRequest} to use for the request.
     * @param complete          The consumer that will be called when the request is complete.
     * @param throwableConsumer The consumer that will be called when an error occurs.
     */
    private static void requestServerList(final BCServerListRequest request, final Consumer<BCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        request.send(WebUtils.HTTP_CLIENT, ClassiCubeHandler.GSON).whenComplete((bcServerList, throwable) -> {
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
