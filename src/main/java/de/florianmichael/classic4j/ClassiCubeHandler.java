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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.florianmichael.classic4j.api.LoginProcessHandler;
import de.florianmichael.classic4j.request.classicube.auth.CCAuthenticationLoginRequest;
import de.florianmichael.classic4j.request.classicube.auth.CCAuthenticationTokenRequest;
import de.florianmichael.classic4j.request.classicube.server.CCServerInfoRequest;
import de.florianmichael.classic4j.request.classicube.server.CCServerListRequest;
import de.florianmichael.classic4j.model.classicube.CCServerList;
import de.florianmichael.classic4j.model.classicube.highlevel.CCAccount;

import javax.security.auth.login.LoginException;
import java.net.URI;
import java.util.List;
import java.util.function.Consumer;

public class ClassiCubeHandler {
    public final static Gson GSON = new GsonBuilder().serializeNulls().create();

    public final static URI CLASSICUBE_ROOT_URI = URI.create("https://www.classicube.net");

    public final static URI AUTHENTICATION_URI = CLASSICUBE_ROOT_URI.resolve("/api/login/");
    public final static URI SERVER_INFO_URI = CLASSICUBE_ROOT_URI.resolve("/api/server/");
    public final static URI SERVER_LIST_INFO_URI = CLASSICUBE_ROOT_URI.resolve("/api/servers/");

    public static void requestServerList(final CCAccount account, final Consumer<CCServerList> complete) {
        requestServerList(account, complete, Throwable::printStackTrace);
    }

    public static void requestServerList(final CCAccount account, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        CCServerListRequest.send(account).whenComplete((ccServerList, throwable) -> {
            if (throwable != null) {
                throwableConsumer.accept(throwable);
                return;
            }
            complete.accept(ccServerList);
        });
    }

    public static void requestServerInfo(final CCAccount account, final String serverHash, final Consumer<CCServerList> complete) {
        requestServerInfo(account, serverHash, complete, Throwable::printStackTrace);
    }

    public static void requestServerInfo(final CCAccount account, final String serverHash, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        requestServerInfo(account, List.of(serverHash), complete, throwableConsumer);
    }

    public static void requestServerInfo(final CCAccount account, final List<String> serverHashes, final Consumer<CCServerList> complete) {
        requestServerInfo(account, serverHashes, complete, Throwable::printStackTrace);
    }

    public static void requestServerInfo(final CCAccount account, final List<String> serverHashes, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        CCServerInfoRequest.send(account, serverHashes).whenComplete((ccServerList, throwable) -> {
            if (throwable != null) {
                throwableConsumer.accept(throwable);
                return;
            }
            complete.accept(ccServerList);
        });
    }

    public static void requestAuthentication(final CCAccount account, final String loginCode, final LoginProcessHandler processHandler) {
        CCAuthenticationTokenRequest.send(account).whenComplete((initialTokenResponse, throwable) -> {
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

            CCAuthenticationLoginRequest.send(account, initialTokenResponse, loginCode).whenComplete((loginResponse, throwable1) -> {
                if (throwable1 != null) {
                    processHandler.handleException(throwable1);
                    return;
                }
                if (loginResponse.mfaRequired()) {
                    processHandler.handleMfa(account);
                    return;
                }
                if (loginResponse.shouldError()) {
                    final String errorDisplay = loginResponse.getErrorDisplay();
                    processHandler.handleException(new LoginException(errorDisplay));
                    return;
                }
                processHandler.handleSuccessfulLogin(account);
            });
        });
    }
}
