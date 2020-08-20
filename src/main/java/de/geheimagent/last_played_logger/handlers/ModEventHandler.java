package de.geheimagent.last_played_logger.handlers;

import de.geheimagent.last_played_logger.configs.MainConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@SuppressWarnings( "unused" )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventHandler {
	
	
	@SubscribeEvent
	public static void handleModConfigLoadingEvent( ModConfig.Loading event ) {
		
		MainConfig.handleConfigChange();
	}
	
	@SubscribeEvent
	public static void handleModConfigReloadingEvent( ModConfig.Reloading event ) {
		
		MainConfig.handleConfigChange();
	}
}
