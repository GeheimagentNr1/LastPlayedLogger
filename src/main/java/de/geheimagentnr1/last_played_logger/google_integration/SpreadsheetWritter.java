package de.geheimagentnr1.last_played_logger.google_integration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import de.geheimagentnr1.last_played_logger.LastPlayedLogger;
import de.geheimagentnr1.last_played_logger.configs.ServerConfig;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.events.ForgeEventHandlerInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.*;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


@Log4j2
@RequiredArgsConstructor
public class SpreadsheetWritter implements ForgeEventHandlerInterface {
	
	
	@NotNull
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( "dd.MM.yyyy", Locale.ENGLISH );
	
	@NotNull
	private final AbstractMod abstractMod;
	
	@Nullable
	private ServerConfig serverConfig;
	
	private Sheets sheetsService = null;
	
	@NotNull
	private ServerConfig getServerConfig() {
		
		if( serverConfig == null ) {
			serverConfig = abstractMod.getConfig( ModConfig.Type.SERVER, ServerConfig.class )
				.orElseThrow( () -> new IllegalStateException( "ServerConfig could not be found" ) );
		}
		return serverConfig;
	}
	
	@NotNull
	private Credential authorize() throws IOException, GeneralSecurityException {
		
		InputStream inputStream = new FileInputStream(
			"." + File.separator + LastPlayedLogger.MODID + File.separator + "credentials.json"
		);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
			GsonFactory.getDefaultInstance(),
			new InputStreamReader( inputStream )
		);
		List<String> scopes = Collections.singletonList( SheetsScopes.SPREADSHEETS );
		GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
			GoogleNetHttpTransport.newTrustedTransport(),
			GsonFactory.getDefaultInstance(),
			clientSecrets,
			scopes
		).setDataStoreFactory( new FileDataStoreFactory( new File( LastPlayedLogger.MODID ) ) )
			.setAccessType( "offline" )
			.build();
		return new AuthorizationCodeInstalledApp( googleAuthorizationCodeFlow, new LocalServerReceiver() )
			.authorize( "user" );
	}
	
	public synchronized void initSheetsService() {
		
		if( getServerConfig().getActive() ) {
			try {
				Credential credential = authorize();
				sheetsService = new Sheets.Builder(
					GoogleNetHttpTransport.newTrustedTransport(),
					GsonFactory.getDefaultInstance(),
					credential
				).setApplicationName( abstractMod.getModName() )
					.build();
			} catch( IOException | GeneralSecurityException exception ) {
				log.error( "Spreadsheet interaction failed", exception );
			}
		} else {
			sheetsService = null;
		}
	}
	
	private synchronized void insertOrUpdateUser( @NotNull String playerName ) {
		
		if( sheetsService == null ) {
			return;
		}
		try {
			String range = getServerConfig().getTabName();
			int index = -1;
			
			ValueRange responce = sheetsService.spreadsheets()
				.values()
				.get( getServerConfig().getSpreadsheetID(), range )
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
					LocalDate.now().format( dateTimeFormatter )
				) ) );
				sheetsService.spreadsheets()
					.values()
					.update(
						getServerConfig().getSpreadsheetID(),
						String.format( "%s!B%d", range, index ),
						body
					).setValueInputOption( "USER_ENTERED" )
					.execute();
				
			} else {
				ValueRange appendBody = new ValueRange().setValues( Collections.singletonList( Arrays.asList(
					playerName,
					LocalDate.now().format( dateTimeFormatter )
				) ) );
				sheetsService.spreadsheets()
					.values()
					.append( getServerConfig().getSpreadsheetID(), range, appendBody )
					.setValueInputOption( "USER_ENTERED" )
					.setInsertDataOption( "INSERT_ROWS" )
					.setIncludeValuesInResponse( false )
					.execute();
			}
		} catch( IOException exception ) {
			log.error( "Spreadsheet interaction failed", exception );
		}
	}
	
	@SubscribeEvent
	@Override
	public void handleServerStartedEvent( @NotNull ServerStartedEvent event ) {
		
		initSheetsService();
	}
	
	@SubscribeEvent
	@Override
	public void handlePlayerLoggedInEvent( @NotNull PlayerEvent.PlayerLoggedInEvent event ) {
		
		if( getServerConfig().getActive() ) {
			new Thread( () -> insertOrUpdateUser( event.getEntity().getGameProfile().getName() ) ).start();
		}
	}
}
