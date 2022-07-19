package de.geheimagent.last_played_logger;

import de.geheimagent.last_played_logger.configs.ServerConfig;
import de.geheimagent.last_played_logger.google_integration.SpreadsheetHelper;
import de.geheimagent.last_played_logger.handlers.ForgeEventHandler;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@Mod( LastPlayedLogger.MODID )
public class LastPlayedLogger extends AbstractMod {
	
	
	public static final String MODID = "last_played_logger";
	
	private SpreadsheetHelper spreadsheetHelper;
	
	public LastPlayedLogger() {
		
		super( MODID );
	}
	
	@Override
	protected void initMod() {
		
		registerConfig( ModConfig.Type.SERVER, ServerConfig::new );
		disableDisplayTest();
		
		registerForgeEventHandler( new ForgeEventHandler( this ) );
	}
	
	
}
