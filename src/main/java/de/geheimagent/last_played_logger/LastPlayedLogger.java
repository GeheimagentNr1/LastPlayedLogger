package de.geheimagent.last_played_logger;

import de.geheimagent.last_played_logger.configs.MainConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@SuppressWarnings( { "UtilityClassWithPublicConstructor", "unused" } )
@Mod( LastPlayedLogger.MODID )
public class LastPlayedLogger {
    
    
    public static final String MODID = "last_played_logger";
    
    public LastPlayedLogger() {
    
        ModLoadingContext.get().registerConfig( ModConfig.Type.COMMON, MainConfig.CONFIG, MODID + ".toml" );
    }
}
