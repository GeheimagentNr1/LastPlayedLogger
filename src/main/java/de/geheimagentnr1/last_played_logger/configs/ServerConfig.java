package de.geheimagentnr1.last_played_logger.configs;

import de.geheimagentnr1.last_played_logger.google_integration.SpreadsheetHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.config.AbstractConfig;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;


public class ServerConfig extends AbstractConfig {
	
	
	private static final String ACTIVE_KEY = "active";
	
	private static final String SPREADSHEET_ID_KEY = "spreadsheetID";
	
	private static final String TAB_NAME_KEY = "tab_name";
	
	public ServerConfig( @NotNull ModConfig.Type _type, @NotNull AbstractMod _abstractMod ) {
		
		super( _type, _abstractMod );
	}
	
	@Override
	protected void registerConfigValues() {
		
		registerConfigValue( "Shall the mod be active or not?", ACTIVE_KEY, false );
		registerConfigValue( "ID of the Spreadsheet.", SPREADSHEET_ID_KEY, "" );
		registerConfigValue( "Name of the Spreadsheet tab.", TAB_NAME_KEY, "" );
	}
	
	public boolean getActive() {
		
		return getValue( Boolean.class, ACTIVE_KEY ).orElseThrow();
	}
	
	public String getSpreadsheetID() {
		
		return getValue( String.class, SPREADSHEET_ID_KEY ).orElseThrow();
	}
	
	public String getTabName() {
		
		return getValue( String.class, TAB_NAME_KEY ).orElseThrow();
	}
}