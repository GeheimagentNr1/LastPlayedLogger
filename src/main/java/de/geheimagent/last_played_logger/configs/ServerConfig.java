package de.geheimagent.last_played_logger.configs;

import de.geheimagent.last_played_logger.google_integration.SpreadsheetHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ServerConfig {
	
	
	private static final Logger LOGGER = LogManager.getLogger( ServerConfig.class );
	
	private static final String MOD_NAME = ModLoadingContext.get().getActiveContainer().getModInfo().getDisplayName();
	
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	
	public static final ForgeConfigSpec CONFIG;
	
	private static final ForgeConfigSpec.BooleanValue ACTIVE;
	
	private static final ForgeConfigSpec.ConfigValue<String> SPREADSHEETID;
	
	private static final ForgeConfigSpec.ConfigValue<String> TAB_NAME;
	
	static {
		
		ACTIVE = BUILDER.comment( "Shall the mod be active or not?" )
			.define( "active", false );
		SPREADSHEETID = BUILDER.comment( "ID of the Spreadsheet." )
			.define( "spreadsheetID", "" );
		TAB_NAME = BUILDER.comment( "Name of the Spreadsheet tab." )
			.define( "tab_name", "" );
		
		CONFIG = BUILDER.build();
	}
	
	public static void handleConfigChange() {
		
		MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
		
		if( minecraftServer != null && minecraftServer.isDedicatedServer() ) {
			SpreadsheetHelper.initSheetsService();
		}
		printConfig();
	}
	
	private static void printConfig() {
		
		LOGGER.info( "Loading \"{}\" Server Config", MOD_NAME );
		LOGGER.info( "{} = {}", ACTIVE.getPath(), ACTIVE.get() );
		LOGGER.info( "{} = {}", SPREADSHEETID.getPath(), SPREADSHEETID.get() );
		LOGGER.info( "{} = {}", TAB_NAME.getPath(), TAB_NAME.get() );
		LOGGER.info( "\"{}\" Server Config loaded", MOD_NAME );
	}
	
	public static String getModName() {
		
		return MOD_NAME;
	}
	
	public static boolean getActive() {
		
		return ACTIVE.get();
	}
	
	public static String getSpreadsheetID() {
		
		return SPREADSHEETID.get();
	}
	
	public static String getTabName() {
		
		return TAB_NAME.get();
	}
}
