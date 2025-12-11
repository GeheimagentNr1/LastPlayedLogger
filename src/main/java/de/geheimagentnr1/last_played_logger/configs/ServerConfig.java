package de.geheimagentnr1.last_played_logger.configs;

import de.geheimagentnr1.last_played_logger.LastPlayedLogger;
import de.geheimagentnr1.last_played_logger.google_integration.SpreadsheetWritter;
import lombok.Getter;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings( "removal" )
@EventBusSubscriber( modid = LastPlayedLogger.MODID, bus = EventBusSubscriber.Bus.MOD )
public class ServerConfig {
	
	
	@NotNull
	private static final String ACTIVE_KEY = "active";
	
	@NotNull
	private static final String SPREADSHEET_ID_KEY = "spreadsheetID";
	
	@NotNull
	private static final String TAB_NAME_KEY = "tab_name";
	
	@NotNull
	private final SpreadsheetWritter spreadsheetWritter;
	
	@NotNull
	@Getter
	private final ModConfigSpec spec;
	
	@NotNull
	private final ModConfigSpec.BooleanValue activeValue;
	
	@NotNull
	private final ModConfigSpec.ConfigValue<String> spreadsheetIdValue;
	
	@NotNull
	private final ModConfigSpec.ConfigValue<String> tabNameValue;
	
	private static ServerConfig instance;
	
	public ServerConfig( @NotNull SpreadsheetWritter _spreadsheetWritter ) {
		
		spreadsheetWritter = _spreadsheetWritter;
		instance = this;
		
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		
		activeValue = builder
			.comment( "Shall the mod be active or not?" )
			.define( ACTIVE_KEY, false );
		
		spreadsheetIdValue = builder
			.comment( "ID of the Spreadsheet." )
			.define( SPREADSHEET_ID_KEY, "" );
		
		tabNameValue = builder
			.comment( "Name of the Spreadsheet tab." )
			.define( TAB_NAME_KEY, "" );
		
		spec = builder.build();
	}
	
	public boolean getActive() {
		
		return activeValue.get();
	}
	
	@NotNull
	public String getSpreadsheetID() {
		
		return spreadsheetIdValue.get();
	}
	
	@NotNull
	public String getTabName() {
		
		return tabNameValue.get();
	}
	
	@SubscribeEvent
	public static void onConfigLoad( ModConfigEvent.Loading event ) {
		
		if( instance != null ) {
			instance.spreadsheetWritter.initSheetsService();
		}
	}
	
	@SubscribeEvent
	public static void onConfigReload( ModConfigEvent.Reloading event ) {
		
		if( instance != null ) {
			instance.spreadsheetWritter.initSheetsService();
		}
	}
}
