# AGENTS.md - Last Played Logger

## Projekt-Übersicht

**Last Played Logger** ist ein NeoForge Minecraft Mod für Minecraft 1.21.1.
- **Mod ID**: `last_played_logger`
- **Package**: `de.geheimagentnr1.last_played_logger`
- **Java Version**: 21
- **NeoForge Version**: 21.1.x

Loggt das letzte Datum, an dem ein Spieler online war, in ein Google Spreadsheet.

## Abhängigkeiten

Keine Mod-Abhängigkeiten - eigenständiger Mod.

## Externe Libraries

- **Google API Services Sheets**
- **Google API Client**
- **Google OAuth Client Jetty**

## Projektstruktur

```
src/main/java/de/geheimagentnr1/last_played_logger/
├── LastPlayedLogger.java              # Haupt-Mod-Klasse
├── configs/
│   └── ServerConfig.java              # Server-Konfiguration
└── google_integration/
    └── SpreadsheetWritter.java        # Google Sheets Integration
```

## Besonderheiten

- **Server-Only**: `usableOnClientSide=false`
- **Google Integration**: Schreibt Daten in Google Spreadsheets
- **OAuth**: Benötigt Google OAuth Credentials

## Code-Stil

- **Annotations**: `@NotNull` aus `org.jetbrains.annotations`
- **Lombok**: Projekt nutzt Lombok
- **Formatierung**: Leerzeichen nach `(` und vor `)` bei Methodenaufrufen

## Build & Test

```bash
./gradlew build
./gradlew runServer
```

## Deployment

- **CurseForge**: `./gradlew curseforge`
- **Modrinth**: `./gradlew modrinth`

## Wichtige Hinweise

1. **Google Credentials**: Benötigt Google API Credentials für Spreadsheet-Zugriff
2. **OAuth Setup**: Erfordert OAuth-Konfiguration
