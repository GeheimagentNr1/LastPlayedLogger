# Last Played Logger

This is a Minecraft Forge mod.  
A mod description can be found on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/last-played-logger).  
This is a combined work only using and not modifying used libraries.

## Used libraries

The mentions of the used libraries, their licences and their copyright holders can be found in the lib_licences folder.  
Because in the master branch is no source code, lib_licences cannot be found in this branch.  
Direct dependent libraries are metioned here:

### Minecraft Forge

This Minecraft Forge mod uses [Minecraft Forge](https://github.com/MinecraftForge/MinecraftForge) as mod launcher with the [GNU LGPLv2.1](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.en.html)  
A description how to use differnt versions of Minecraft Forge can be found here: [https://github.com/MinecraftForge/MinecraftForge](https://github.com/MinecraftForge/MinecraftForge)

### Google APIs Client Library for Java

This Minecraft Forge mod uses the [Google APIs Client Library for Java](https://developers.google.com/api-client-library/java) library with the [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) licence.

#### Using modified version of Google APIs Client Library for Java

The library is provided as part of the mods jar-file.  
To customize the library version follow the following steps:
1. Clone this repository
2. Change the library version by changing the value of the google_api_client_version property in the gradle.properites file
3. Run "gradle shadowJar"
4. Your custom mod version can be found in the build/libs folder. Copy it to the mods folder of your client/server.

### Google OAuth Client Library for Java

This Minecraft Forge mod uses the [Google OAuth Client Library for Java](https://developers.google.com/api-client-library/java/google-oauth-java-client) library with the [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) licence.

#### Using modified version of Google OAuth Client Library for Java

The library is provided as part of the mods jar-file.  
To customize the library version follow the following steps:
1. Clone this repository
2. Change the library version by changing the value of the google_oauth_client_jetty_version property in the gradle.properites file
3. Run "gradle shadowJar"
4. Your custom mod version can be found in the build/libs folder. Copy it to the mods folder of your client/server.

### Google Sheets API

This Minecraft Forge mod uses the [Google Sheets API](https://developers.google.com/sheets/api) library with the [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) licence.

#### Using modified version of Google Sheets API

The library is provided as part of the mods jar-file.  
To customize the library version follow the following steps:
1. Clone this repository
2. Change the library version by changing the value of the google_api_services_sheets_version property in the gradle.properites file
3. Run "gradle shadowJar"
4. Your custom mod version can be found in the build/libs folder. Copy it to the mods folder of your client/server.

## Branches

| Branch  | Description |
| ------------- | ------------- |
| master | default branch - holds only the licences of the libraries and the README file |
| develop_X.X.X | develop branch for the Minecraft version X.X.X - holds unstable and possible not compilable or working version. |
| master_X.X.X | master branch for the Minecraft version X.X.X - holds stable/released versions of the mod/jar files can be found on CurseForge |
| Other branches | branches for implementing new feature - contains unstable and possible not compilable or working version. |
