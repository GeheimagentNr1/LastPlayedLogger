package de.geheimagent.last_played_logger.handlers;

import de.geheimagent.last_played_logger.LastPlayedLogger;
import de.geheimagent.last_played_logger.configs.ServerConfig;
import de.geheimagent.last_played_logger.google_integration.SpreadsheetHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;


@Mod.EventBusSubscriber( modid = LastPlayedLogger.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
	
	
	@SubscribeEvent
	public static void handleServerStartedEvent( FMLServerStartedEvent event ) {
		
		if( event.getServer().isDedicatedServer() ) {
			SpreadsheetHelper.initSheetsService();
		}
	}
	
	@SubscribeEvent
	public static void handlePlayerLoggedInEvent( PlayerEvent.PlayerLoggedInEvent event ) {
		
		if( ServerLifecycleHooks.getCurrentServer().isDedicatedServer() && ServerConfig.getActive() ) {
			new Thread( () -> SpreadsheetHelper.insertOrUpdateUser( event.getPlayer().getGameProfile().getName() ) )
				.start();
		}
	}
}
