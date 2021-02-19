package de.geheimagent.last_played_logger;

import de.geheimagent.last_played_logger.configs.MainConfig;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;


@SuppressWarnings( { "UtilityClassWithPublicConstructor", "unused" } )
@Mod( LastPlayedLogger.MODID )
public class LastPlayedLogger {
	
	
	public static final String MODID = "last_played_logger";
	
	public LastPlayedLogger() {
		
		ModLoadingContext.get().registerConfig( ModConfig.Type.SERVER, MainConfig.CONFIG );
		ModLoadingContext.get().registerExtensionPoint(
			ExtensionPoint.DISPLAYTEST,
			() -> Pair.of(
				() -> FMLNetworkConstants.IGNORESERVERONLY,
				( remote, isServer ) -> true
			)
		);
	}
}
