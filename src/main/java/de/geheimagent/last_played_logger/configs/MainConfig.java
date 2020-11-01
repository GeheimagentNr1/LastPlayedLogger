package de.geheimagent.last_played_logger.configs;

import de.geheimagent.last_played_logger.google_integration.SpreadsheetHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainConfig {
	
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final String mod_name = "Last Played Logger";
	
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	
	public static final ForgeConfigSpec CONFIG;
	
	private static final ForgeConfigSpec.BooleanValue ACTIVE;
	
	private static final ForgeConfigSpec.ConfigValue<String> SPREADSHEETID;
	
	private static final ForgeConfigSpec.ConfigValue<String> TAB_NAME;
	
	static {
		
		ACTIVE = BUILDER.comment( "Shall the mod be active or not?" ).define( "active", false );
		SPREADSHEETID = BUILDER.comment( "ID of the Spreadsheet." ).define( "spreadsheetID", "" );
		TAB_NAME = BUILDER.comment( "Name of the Spreadsheet tab." ).define( "tab_name", "" );
		
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
		
		LOGGER.info( "Loading \"{}\" Config", mod_name );
		LOGGER.info( "{} = {}", ACTIVE.getPath(), ACTIVE.get() );
		LOGGER.info( "{} = {}", SPREADSHEETID.getPath(), SPREADSHEETID.get() );
		LOGGER.info( "{} = {}", TAB_NAME.getPath(), TAB_NAME.get() );
		LOGGER.info( "\"{}\" Config loaded", mod_name );
	}
	
	public static String getModName() {
		
		return mod_name;
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
