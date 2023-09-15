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

import de.florianmichael.classic4j.api.JoinServerInterface;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Scanner;

/**
 * This class provides methods to interact with the BetaCraft API. These API methods are used to request the Multiplayer Pass for a server.
 */
public class JSPBetaCraftHandler {
    public final static URI GET_MP_PASS = URI.create("http://api.betacraft.uk/getmppass.jsp");

    /**
     * Requests the Multiplayer Pass for a server from the BetaCraft API.
     * @param username            The username of the player.
     * @param ip                  The IP of the server.
     * @param port                The port of the server.
     * @param joinServerInterface The {@link JoinServerInterface} to use for the request. This is used to send the authentication request.
     * @return                    The Multiplayer Pass for the server.
     */
    public static String requestMPPass(final String username, final String ip, final int port, final JoinServerInterface joinServerInterface) {
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
            t.printStackTrace();
            return "0";
        }
    }

    /**
     * Hashes the input using SHA-1.
     * @param input The input to hash.
     * @return      The hashed input.
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
