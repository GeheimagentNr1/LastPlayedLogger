package de.geheimagent.last_played_logger.handlers;

import de.geheimagent.last_played_logger.LastPlayedLogger;
import de.geheimagent.last_played_logger.configs.ServerConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@Mod.EventBusSubscriber( modid = LastPlayedLogger.MODID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventHandler {
	
	
	@SubscribeEvent
	public static void handleModConfigLoadingEvent( ModConfig.Loading event ) {
		
		ServerConfig.handleConfigChange();
	}
	
	@SubscribeEvent
	public static void handleModConfigReloadingEvent( ModConfig.ConfigReloading event ) {
		
		ServerConfig.handleConfigChange();
	}
}
