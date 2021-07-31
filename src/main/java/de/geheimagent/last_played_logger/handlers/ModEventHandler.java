package de.geheimagent.last_played_logger.handlers;

import de.geheimagent.last_played_logger.LastPlayedLogger;
import de.geheimagent.last_played_logger.configs.ServerConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;


@Mod.EventBusSubscriber(
	modid = LastPlayedLogger.MODID,
	bus = Mod.EventBusSubscriber.Bus.MOD,
	value = Dist.DEDICATED_SERVER
)
public class ModEventHandler {
	
	
	@SubscribeEvent
	public static void handleModConfigLoadingEvent( ModConfigEvent.Loading event ) {
		
		ServerConfig.handleConfigChange();
	}
	
	@SubscribeEvent
	public static void handleModConfigReloadingEvent( ModConfigEvent.Reloading event ) {
		
		ServerConfig.handleConfigChange();
	}
}
