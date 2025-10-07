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

package de.florianmichael.classic4j.api;

import de.florianmichael.classic4j.model.classicube.account.CCAccount;

/**
 * This interface is used to handle the login process. This is used by the {@link de.florianmichael.classic4j.ClassiCubeHandler}.
 */
public interface LoginProcessHandler {

    /**
     * Handles the MFA process. This is called when the account has MFA enabled. The user should be prompted to enter the MFA code.
     *
     * @param account The account that has MFA enabled.
     */
    void handleMfa(final CCAccount account);

    /**
     * Handles the successful login. This is called when the login was successful.
     *
     * @param account The account that was used for the login.
     */
    void handleSuccessfulLogin(final CCAccount account);

    /**
     * Handles the failed login. This is called when the login failed.
     *
     * @param throwable The throwable that caused the login to fail.
     */
    void handleException(final Throwable throwable);

}
