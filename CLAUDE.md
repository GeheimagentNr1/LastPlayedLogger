# CLAUDE.md - Last Played Logger

## Projekt-Übersicht

**Last Played Logger** ist ein NeoForge Minecraft Mod.
- **Mod ID**: `last_played_logger`
- **Package**: `de.geheimagentnr1.last_played_logger`
- **Java Version**: 21 (`develop_26.1`: 25, `jdk-25.0.4.7-hotspot`)

Loggt das letzte Datum, an dem ein Spieler online war, in ein Google Spreadsheet.

| Branch | MC | Range | NeoForge (kompiliert gegen) | Hinweis |
|---|---|---|---|---|
| `develop_1.21.1` | 1.21.1 - 1.21.10 | `[1.21.1,1.21.10]` | 21.1.x | Release `1.21.1-3.0.1` |
| `develop_1.21.11` | 1.21.11 | `[1.21.11,1.21.12)` | `21.11.45` | `@EventBusSubscriber(bus = ...)` (seit 1.21.6 entfernt) weggelassen, GameTests entfernt |
| `develop_26.1` | 26.1 - 26.3 | `[26.1,27)` | `26.1.0.19-beta` (Java 25) | Aufbauend auf `develop_1.21.11`, nur Tooling (Java 25 / Gradle 9.2.1 / moddev 2.0.147 / Lombok 1.18.48); Bytecode identisch für 26.1 - 26.3. Die Google-Bibliotheken nutzen Guava/Gson aus Minecraft, deshalb mit echtem Spreadsheet-Login testen |

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

- **Server-Only**: `@Mod( value = MODID, dist = Dist.DEDICATED_SERVER )` — lädt nur auf dedizierten Servern
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
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.8-hotspot"
./gradlew build
```

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Ab `develop_1.21.11` gibt es keine GameTests mehr (trivialer Smoke-Test samt Run-Config und CI-Job entfernt).

### Ingame-Test

Testpack mit `last_played_logger/credentials.json` + `StoredCredential` (Server-Ordner) und aktiver `world/serverconfig/last_played_logger-server.toml`; nach dem Login steht im Spreadsheet-Tab der Spielername mit dem heutigen Datum (neu angelegt oder aktualisiert). Der Mod ist server-only, die Client-Instanz braucht ihn nicht.

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus

### Was kann automatisiert getestet werden?

| Aspekt | Automatisiert? | Methode |
|--------|----------------|---------|
| Utility-Klassen | ✅ | JUnit |
| Config-Parsing | ✅ | JUnit |
| Commands | ✅ | GameTest |
| Block/Item-Verhalten | ✅ | GameTest |
| Multi-MC-Version | ⚠️ Pro Branch | CI Matrix |

## Referenzen

- [NeoForge Migration Primer](https://docs.neoforged.net/primer/docs/) — Dokumentiert API-Aenderungen zwischen Minecraft/NeoForge-Versionen; nuetzlich fuer die Pruefung von Breaking Changes beim Upgrade auf neue Versionen

---

## Wissensdatenbank

Versionsübergreifende Migrations- und Entwicklungs-Erkenntnisse (Breaking Changes, Fixes, Testumgebungs-Patterns) werden zentral in [`../Docs/`](../Docs/) gepflegt. Bei neuen relevanten Erkenntnissen dort ergänzen, nicht nur hier.
