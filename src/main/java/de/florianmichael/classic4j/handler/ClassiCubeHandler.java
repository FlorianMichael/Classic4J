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
package de.florianmichael.classic4j.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.florianmichael.classic4j.Classic4J;
import de.florianmichael.classic4j.api.LoginProcessHandler;
import de.florianmichael.classic4j.handler.classicube.auth.CCAuthenticationLoginRequest;
import de.florianmichael.classic4j.handler.classicube.auth.CCAuthenticationTokenRequest;
import de.florianmichael.classic4j.handler.classicube.server.CCServerInfoRequest;
import de.florianmichael.classic4j.handler.classicube.server.CCServerListRequest;
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

    private final Classic4J classic4J;

    public ClassiCubeHandler(Classic4J classic4J) {
        this.classic4J = classic4J;
    }

    public void requestServerList(final CCAccount account, final Consumer<CCServerList> complete) {
        requestServerList(account, complete, Throwable::printStackTrace);
    }

    public void requestServerList(final CCAccount account, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        CCServerListRequest.send(account).whenComplete((ccServerList, throwable) -> {
            if (throwable != null) {
                throwableConsumer.accept(throwable);
                return;
            }
            complete.accept(ccServerList);
        });
    }

    public void requestServerInfo(final CCAccount account, final String serverHash, final Consumer<CCServerList> complete) {
        requestServerInfo(account, serverHash, complete, Throwable::printStackTrace);
    }

    public void requestServerInfo(final CCAccount account, final String serverHash, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        requestServerInfo(account, List.of(serverHash), complete, throwableConsumer);
    }

    public void requestServerInfo(final CCAccount account, final List<String> serverHashes, final Consumer<CCServerList> complete) {
        requestServerInfo(account, serverHashes, complete, Throwable::printStackTrace);
    }

    public void requestServerInfo(final CCAccount account, final List<String> serverHashes, final Consumer<CCServerList> complete, final Consumer<Throwable> throwableConsumer) {
        CCServerInfoRequest.send(account, serverHashes).whenComplete((ccServerList, throwable) -> {
            if (throwable != null) {
                throwableConsumer.accept(throwable);
                return;
            }
            complete.accept(ccServerList);
        });
    }

    public void requestAuthentication(final CCAccount account, final String loginCode, final LoginProcessHandler processHandler) {
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
