# CLAUDE.md - Last Played Logger

Schreibt bei jedem Login eines Spielers dessen Namen und das aktuelle Datum (`dd.MM.yyyy`) in ein Google Spreadsheet (vorhandene Zeile aktualisieren, sonst neue anhängen). Mod-ID `last_played_logger`, Package `de.geheimagentnr1.last_played_logger` (Forge bis `develop_1.19.2`: noch `de.geheimagent.last_played_logger`). Server-only: NeoForge `@Mod( value = MODID, dist = Dist.DEDICATED_SERVER )`, Forge ab 1.20 über `DistExecutor.safeRunWhenOn( Dist.DEDICATED_SERVER, .. )`, ältere Forge-Branches über `@Mod.EventBusSubscriber( value = Dist.DEDICATED_SERVER )` am Event-Handler; `displayTest=IGNORE_SERVER_VERSION`, Clients brauchen den Mod nicht.

Diese Datei ist auf allen `develop_*`-Branches identisch; Quelle: `../Docs/mod-claude/LastPlayedLogger.md` (Änderungen dort, dann `Docs/tools/sync_mod_claude_md.sh`). Versionsstand: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).

## Versionen

- Forge: `develop_1.14.4` bis `develop_1.21`, dazu `develop_1.21.1_forge`.
- NeoForge: `develop_1.21.1` (1.21.1 - 1.21.10), `develop_1.21.11`, `develop_26.1` (26.1 - 26.2, nur Tooling-Änderungen, Bytecode identisch), `develop_26.3` (ab NeoForge 26.3.0.37-beta: Config-Typ `SYNCED` statt `SERVER` mit altem Dateinamen `<modid>-server.toml`, alte `world/serverconfig/`-Datei wird auf Dedicated Servern einmalig nach `world/syncedconfig/` kopiert - `config/ServerConfigRegistration`, `config/LegacyServerConfigMigration`, 2026-10-19).
- Ranges und Tags: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).

## Abhängigkeiten

- Keine Mod-Abhängigkeiten. Forge ab 1.20: MinecraftForgeAPI per `jarJar` (siehe [`../Docs/forge/README.md`](../Docs/forge/README.md)).
- Gebündelt per `jarJar` (`custom.gradle`): `google-api-services-sheets`, `google-api-client`, `google-oauth-client-jetty` sowie deren Laufzeit-Abhängigkeiten (`google-oauth-client(-java6)`, `google-http-client(-gson, -apache-v2)`, `opencensus-*`, `grpc-context`), jeweils `transitive = false`.
- **Nicht** gebündelt (per `exclude`): Guava, Gson, commons-codec, j2objc, jsr305 - kommen aus Minecraft.

## Architektur

- `LastPlayedLogger` - Hauptklasse; erzeugt `SpreadsheetWritter`, registriert ihn auf dem Game-Bus und die Server-Config.
- `configs/ServerConfig` - Server-Config; lauscht auf `ModConfigEvent.Loading`/`Reloading` und initialisiert dann den Sheets-Dienst neu (bis 1.21.10 `@EventBusSubscriber( bus = Bus.MOD )`, ab 1.21.11 ohne `bus`).
- `google_integration/SpreadsheetWritter` - OAuth (`GoogleAuthorizationCodeFlow` + `LocalServerReceiver`), Sheets-Client; `ServerStartedEvent` → `initSheetsService()`, `PlayerLoggedInEvent` → `insertOrUpdateUser(..)` in eigenem Thread (nur wenn `active`).
- Forge bis 1.19.4: `google_integration/SpreadsheetHelper` und `handlers/ForgeEventHandler`/`ModEventHandler` statt `SpreadsheetWritter`.

## Funktionen, Befehle, Konfiguration

- Keine Befehle, keine Welt-Daten.
- Server-Config `world/serverconfig/last_played_logger-server.toml`: `active` (Standard `false`), `spreadsheetID`, `tab_name`.
- Dateien im Server-Arbeitsverzeichnis: `last_played_logger/credentials.json` (OAuth-Client von Google, vom Betreiber bereitgestellt) und der Token-Speicher `last_played_logger/StoredCredential` (`FileDataStoreFactory`). Ohne gültigen Token startet beim ersten Zugriff der OAuth-Ablauf mit lokalem Empfänger (Login im Browser nötig).
- **Credentials und Token sind Geheimnisse** - nie ausgeben, committen oder in Logs/Docs kopieren.

## Fallstricke

- Spielername über `entity.getName().getString()`, nicht `GameProfile.getName()` (authlib-`GameProfile` wurde zum Record, Methode fehlt in neueren Versionen).
- 1.21.11: `@EventBusSubscriber( bus = .. )` gibt es nicht mehr (seit NeoForge 21.6) - ohne `bus` registrieren.
- Die Google-Bibliotheken nutzen Guava/Gson aus Minecraft; bei jedem Port (v. a. 26.x) mit echtem Spreadsheet-Login testen, ein grüner Build reicht nicht.
- Testpacks ohne gültigen Token erzeugen `TokenResponse`-Fehler im Log; `../Docs/testing/tools/pack.sh` filtert diese (`last_played_logger`) bewusst heraus.

## Testen

- Testpack mit `last_played_logger/credentials.json` + `StoredCredential` im Server-Ordner und `active = true` in der Server-Config; nach dem Login muss im Spreadsheet-Tab der Spielername mit dem heutigen Datum stehen (neu angelegt oder aktualisiert).
- Nur in den Server-Testpack kopieren, die Client-Instanz braucht den Mod nicht (Client-Start ohne Absturz trotzdem prüfen, Mod lädt dort nicht).
- GameTests gibt es ab `develop_1.21.11` nicht mehr, nur JUnit. Allgemein: [`../Docs/testing/README.md`](../Docs/testing/README.md).

## Links

- Wiki: https://github.com/GeheimagentNr1/LastPlayedLogger/wiki
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/last-played-logger (Projekt-ID 389500)
- Modrinth: https://modrinth.com/mod/fQmzuyuH
- [`../Docs/migrations/BREAKING_CHANGES.md`](../Docs/migrations/BREAKING_CHANGES.md), [`../Docs/migrations/1.21.10-to-1.21.11.md`](../Docs/migrations/1.21.10-to-1.21.11.md)
