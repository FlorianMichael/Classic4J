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

import de.florianmichael.classic4j.api.JoinServerInterface;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Scanner;

public class JSPBetaCraftHandler {
    public final static URI GET_MP_PASS = URI.create("http://api.betacraft.uk/getmppass.jsp");

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
