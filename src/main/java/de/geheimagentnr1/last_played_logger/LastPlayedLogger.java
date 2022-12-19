package de.geheimagentnr1.last_played_logger;

import de.geheimagentnr1.last_played_logger.configs.ServerConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@SuppressWarnings( "UtilityClassWithPublicConstructor" )
@Mod( LastPlayedLogger.MODID )
public class LastPlayedLogger {
	
	
	public static final String MODID = "last_played_logger";
	
	public LastPlayedLogger() {
		
		ModLoadingContext.get().registerConfig( ModConfig.Type.SERVER, ServerConfig.CONFIG );
	}
}
