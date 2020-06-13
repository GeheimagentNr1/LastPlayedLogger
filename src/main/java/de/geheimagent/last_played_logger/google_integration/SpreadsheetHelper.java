package de.geheimagent.last_played_logger.google_integration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import de.geheimagent.last_played_logger.LastPlayedLogger;
import de.geheimagent.last_played_logger.configs.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SpreadsheetHelper {
    
    
    private final static Logger LOGGER = LogManager.getLogger();
    
    private static Sheets sheetsService = null;
    
    private static Credential authorize() throws IOException, GeneralSecurityException {
    
        InputStream inputStream = new FileInputStream( "." + File.separator + LastPlayedLogger.MODID + File.separator +
            "credentials.json" );
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load( JacksonFactory.getDefaultInstance(),
            new InputStreamReader( inputStream ) );
        List<String> spopes = Collections.singletonList( SheetsScopes.SPREADSHEETS );
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
            GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, spopes )
            .setDataStoreFactory( new FileDataStoreFactory( new File( LastPlayedLogger.MODID ) ) )
            .setAccessType( "offline" ).build();
        return new AuthorizationCodeInstalledApp( googleAuthorizationCodeFlow,
            new LocalServerReceiver() ).authorize( "user" );
    }
    
    public static void initSheetsService() throws IOException, GeneralSecurityException {
        
        if( sheetsService == null ) {
            Credential credential = authorize();
            sheetsService = new Sheets.Builder( GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential ).setApplicationName( ModConfig.getModName() ).build();
        }
    }
    
    public static void insertOrUpdateUser( String playerName ) {
    
        try {
            initSheetsService();
            String range = ModConfig.getTabName();
            int index = -1;
    
            ValueRange responce = sheetsService.spreadsheets().values().get( ModConfig.getSpreadsheetID(), range )
                .execute();
            List<List<Object>> users = responce.getValues();
            if( users != null ) {
                for( int i = 0; i < users.size(); i++ ) {
                    List<Object> user = users.get( i );
                    if( playerName.equals( user.get( 0 ) ) ) {
                        index = i + 1;
                        break;
                    }
                }
            }
            if( index > 0 ) {
                ValueRange body = new ValueRange().setValues( Collections.singletonList( Collections.singletonList(
                    LocalDate.now().format( DateTimeFormatter.ofPattern( "dd.MM.yyyy" ) ) ) ) );
                //noinspection StringConcatenationMissingWhitespace
                sheetsService.spreadsheets().values().update( ModConfig.getSpreadsheetID(), range + "!B" + index,
                    body ).setValueInputOption( "USER_ENTERED" ).execute();
                
            } else {
                ValueRange appendBody = new ValueRange().setValues( Collections.singletonList( Arrays.asList(
                    playerName, LocalDate.now().format( DateTimeFormatter.ofPattern( "dd.MM.yyyy" ) ) ) ) );
                sheetsService.spreadsheets().values().append( ModConfig.getSpreadsheetID(), range, appendBody )
                    .setValueInputOption( "USER_ENTERED" ).setInsertDataOption( "INSERT_ROWS" )
                    .setIncludeValuesInResponse( false ).execute();
            }
        } catch( IOException | GeneralSecurityException exception ) {
            LOGGER.error( "Spreadsheet interaction failed", exception );
        }
    }
}
