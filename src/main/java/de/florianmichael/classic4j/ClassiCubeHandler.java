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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.florianmichael.classic4j.api.LoginProcessHandler;
import de.florianmichael.classic4j.request.classicube.auth.CCAuthenticationLoginRequest;
import de.florianmichael.classic4j.request.classicube.auth.CCAuthenticationTokenRequest;
import de.florianmichael.classic4j.request.classicube.server.CCServerInfoRequest;
import de.florianmichael.classic4j.request.classicube.server.CCServerListRequest;
import de.florianmichael.classic4j.model.classicube.server.CCServerList;
import de.florianmichael.classic4j.model.classicube.account.CCAccount;
import de.florianmichael.classic4j.util.HttpClientUtils;

import javax.security.auth.login.LoginException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class provides methods to interact with the ClassiCube API.
 */
public class ClassiCubeHandler {

    public static final Gson GSON = new GsonBuilder().serializeNulls().create();

    /**
     * The URI to the ClassiCube root.
     */
    public static final URI CLASSICUBE_ROOT_URI = URI.create("https://www.classicube.net");

    public static final URI AUTHENTICATION_URI = CLASSICUBE_ROOT_URI.resolve("/api/login/");
    public static final URI SERVER_INFO_URI = CLASSICUBE_ROOT_URI.resolve("/api/server/");
    public static final URI SERVER_LIST_INFO_URI = CLASSICUBE_ROOT_URI.resolve("/api/servers/");

    /**
     * Requests the ClassiCube server list and returns it as a {@link CCServerList} object.
     * @param account  The account to use for the request.
     * @param complete The consumer that will be called when the request is complete.
     */
    public static void requestServerList(final CCAccount account, final Consumer<CCServerList> complete) {
        requestServerList(account, complete, Throwable::printStackTrace);
    }

    /**
     * Requests the ClassiCube server list and returns it as a {@link CCServerList} object.
     * @param account           The account to use for the request.
     * @param complete          The consumer that will be called when the request is complete.
     * @param throwableConsumer The consumer that will be called when an error occurs.
     */
    public static void requestServerList(final CCAccount account, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        CCServerListRequest.send(HttpClientUtils.HTTP_CLIENT, account).whenComplete((ccServerList, throwable) -> {
            if (throwable != null) {
                throwableConsumer.accept(throwable);
                return;
            }
            complete.accept(ccServerList);
        });
    }

    /**
     * Requests the ClassiCube server list and returns it as a {@link CCServerList} object.
     * @param account     The account to use for the request.
     * @param serverHash  The server hash to request.
     * @param complete    The consumer that will be called when the request is complete.
     */
    public static void requestServerInfo(final CCAccount account, final String serverHash, final Consumer<CCServerList> complete) {
        requestServerInfo(account, serverHash, complete, Throwable::printStackTrace);
    }

    /**
     * Requests the ClassiCube server list and returns it as a {@link CCServerList} object.
     *
     * @param account           The account to use for the request.
     * @param serverHash        The server hash to request.
     * @param complete          The consumer that will be called when the request is complete.
     * @param throwableConsumer The consumer that will be called when an error occurs.
     */
    public static void requestServerInfo(final CCAccount account, final String serverHash, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        requestServerInfo(account, Collections.singletonList(serverHash), complete, throwableConsumer);
    }

    /**
     * Requests the ClassiCube server list and returns it as a {@link CCServerList} object.
     *
     * @param account      The account to use for the request.
     * @param serverHashes The server hashes to request.
     * @param complete     The consumer that will be called when the request is complete.
     */
    public static void requestServerInfo(final CCAccount account, final List<String> serverHashes, final Consumer<CCServerList> complete) {
        requestServerInfo(account, serverHashes, complete, Throwable::printStackTrace);
    }

    /**
     * Requests the ClassiCube server list and returns it as a {@link CCServerList} object.
     * @param account           The account to use for the request.
     * @param serverHashes      The server hashes to request.
     * @param complete          The consumer that will be called when the request is complete.
     * @param throwableConsumer The consumer that will be called when an error occurs.
     */
    public static void requestServerInfo(final CCAccount account, final List<String> serverHashes, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        CCServerInfoRequest.send(HttpClientUtils.HTTP_CLIENT, account, serverHashes).whenComplete((ccServerList, throwable) -> {
            if (throwable != null) {
                throwableConsumer.accept(throwable);
                return;
            }
            complete.accept(ccServerList);
        });
    }

    /**
     * Authenticates the given account with the given login code. The {@link LoginProcessHandler} will be called when the login process is complete.
     * If the login process fails, the {@link LoginProcessHandler#handleException(Throwable)} method will be called. If the login process requires MFA,
     * the {@link LoginProcessHandler#handleMfa(CCAccount)} method will be called. If the login process is successful, the {@link LoginProcessHandler#handleSuccessfulLogin(CCAccount)}
     * method will be called. If the login process is successful, the account will be updated with the new token.
     * If it is the first login, the loginCode parameter can be null.
     * To authenticate an account with MFA, you call this method twice, once with null as loginCode and once with the MFA code you received.
     *
     * @param account        The account to authenticate.
     * @param loginCode      The login code to use.
     * @param processHandler The handler to use for the login process.
     */
    public static void requestAuthentication(final CCAccount account, final String loginCode, final LoginProcessHandler processHandler) {
        CCAuthenticationTokenRequest.send(HttpClientUtils.HTTP_CLIENT, account).whenComplete((initialTokenResponse, throwable) -> {
            if (throwable != null) {
                processHandler.handleException(throwable);
                return;
            }
            // There should NEVER be any errors on the initial token response!
            if (initialTokenResponse.shouldError()) {
                final String errorDisplay = initialTokenResponse.getErrorDisplay();
                processHandler.handleException(new LoginException(errorDisplay));
                return;
            }
            account.token = initialTokenResponse.token;

            CCAuthenticationLoginRequest.send(HttpClientUtils.HTTP_CLIENT, account, initialTokenResponse, loginCode).whenComplete((loginResponse, throwable1) -> {
                if (throwable1 != null) {
                    processHandler.handleException(throwable1);
                    return;
                }
                if (loginResponse.mfaRequired()) {
                    processHandler.handleMfa(account);
                    return;
                }
                if (loginResponse.shouldError()) {
                    processHandler.handleException(new LoginException(loginResponse.getErrorDisplay()));
                    return;
                }
                processHandler.handleSuccessfulLogin(account);
            });
        });
    }

}
