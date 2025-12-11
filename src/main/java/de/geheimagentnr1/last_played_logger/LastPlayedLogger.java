package de.geheimagentnr1.last_played_logger;

import de.geheimagentnr1.last_played_logger.configs.ServerConfig;
import de.geheimagentnr1.last_played_logger.google_integration.SpreadsheetWritter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;


@Mod( LastPlayedLogger.MODID )
public class LastPlayedLogger {
	
	
	@NotNull
	public static final String MODID = "last_played_logger";
	
	@NotNull
	public static final String MOD_NAME = "Last Played Logger";
	
	public LastPlayedLogger( IEventBus modEventBus, ModContainer modContainer ) {
		
		if( FMLEnvironment.dist.isDedicatedServer() ) {
			SpreadsheetWritter spreadsheetWritter = new SpreadsheetWritter();
			NeoForge.EVENT_BUS.register( spreadsheetWritter );
			
			ServerConfig serverConfig = new ServerConfig( spreadsheetWritter );
			modContainer.registerConfig( ModConfig.Type.SERVER, serverConfig.getSpec() );
			spreadsheetWritter.setServerConfig( serverConfig );
		}
	}
}
