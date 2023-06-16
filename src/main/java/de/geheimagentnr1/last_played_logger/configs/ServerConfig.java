package de.geheimagentnr1.last_played_logger.configs;

import de.geheimagentnr1.last_played_logger.google_integration.SpreadsheetWritter;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.config.AbstractConfig;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;


public class ServerConfig extends AbstractConfig {
	
	
	@NotNull
	private static final String ACTIVE_KEY = "active";
	
	@NotNull
	private static final String SPREADSHEET_ID_KEY = "spreadsheetID";
	
	@NotNull
	private static final String TAB_NAME_KEY = "tab_name";
	
	@NotNull
	private final SpreadsheetWritter spreadsheetWritter;
	
	public ServerConfig( @NotNull AbstractMod _abstractMod, @NotNull SpreadsheetWritter _spreadsheetWritter ) {
		
		super( _abstractMod );
		spreadsheetWritter = _spreadsheetWritter;
	}
	
	@NotNull
	@Override
	public ModConfig.Type type() {
		
		return ModConfig.Type.SERVER;
	}
	
	@Override
	public boolean isEarlyLoad() {
		
		return false;
	}
	
	@Override
	protected void registerConfigValues() {
		
		registerConfigValue( "Shall the mod be active or not?", ACTIVE_KEY, false );
		registerConfigValue( "ID of the Spreadsheet.", SPREADSHEET_ID_KEY, "" );
		registerConfigValue( "Name of the Spreadsheet tab.", TAB_NAME_KEY, "" );
	}
	
	@Override
	protected void handleConfigChanging() {
		
		spreadsheetWritter.initSheetsService();
	}
	
	public boolean getActive() {
		
		return getValue( Boolean.class, ACTIVE_KEY );
	}
	
	@NotNull
	public String getSpreadsheetID() {
		
		return getValue( String.class, SPREADSHEET_ID_KEY );
	}
	
	@NotNull
	public String getTabName() {
		
		return getValue( String.class, TAB_NAME_KEY );
	}
}
