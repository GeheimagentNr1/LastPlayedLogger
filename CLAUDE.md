# CLAUDE.md - Last Played Logger

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

## Testing

### Java-Versionen

Verschiedene Java-Versionen sind unter `C:\Program Files\Eclipse Adoptium` installiert. Für einen Gradle-Build muss die passende Java-Version gewählt werden:

```powershell
# Java 21 für MC 1.20.5+ (NeoForge)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot"
./gradlew build
```

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Für Integration Tests in einer echten Minecraft-Umgebung:

```bash
./gradlew runGameTestServer
```

GameTest-Klassen werden mit `@GameTestHolder` annotiert und liegen unter `src/main/java/.../elements/gametests/`.

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus
3. **GameTests**: Startet GameTestServer (optional)

### Was kann automatisiert getestet werden?

| Aspekt | Automatisiert? | Methode |
|--------|----------------|---------|
| Utility-Klassen | ✅ | JUnit |
| Config-Parsing | ✅ | JUnit |
| Commands | ✅ | GameTest |
| Block/Item-Verhalten | ✅ | GameTest |
| Multi-MC-Version | ⚠️ Pro Branch | CI Matrix |
