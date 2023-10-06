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

package de.florianmichael.classic4j.request.betacraft;

import com.google.gson.Gson;
import de.florianmichael.classic4j.model.betacraft.BCServerInfoSpec;
import de.florianmichael.classic4j.model.betacraft.BCServerList;
import de.florianmichael.classic4j.model.betacraft.v1.BCServerInfov1;
import de.florianmichael.classic4j.model.betacraft.v2.BCServerInfov2;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

public enum BCServerListRequest implements BCServerListRequestInterface {
  V1(URI.create("https://api.betacraft.uk/server_list.jsp"), BCServerInfov1.class),
  V2(URI.create("https://api.betacraft.uk/v2/server_list"), BCServerInfov2.class);

  private final URI uri;
  private final Class<? extends BCServerInfoSpec> infoSpec;

  BCServerListRequest(final URI uri, final Class<? extends BCServerInfoSpec> infoSpec) {
    this.uri = uri;
    this.infoSpec = infoSpec;
  }

  @Override
  public URI uri() {
    return this.uri;
  }

  @Override
  public Class<? extends BCServerInfoSpec> infoSpec() {
    return this.infoSpec;
  }

  public static CompletableFuture<BCServerList> sendV1(final HttpClient client, final Gson gson) {
    return V1.send(client, gson);
  }

  public static CompletableFuture<BCServerList> sendV2(final HttpClient client, final Gson gson) {
    return V2.send(client, gson);
  }
}
