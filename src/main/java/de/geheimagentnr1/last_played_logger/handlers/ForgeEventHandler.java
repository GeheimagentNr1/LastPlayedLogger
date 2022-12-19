package de.geheimagentnr1.last_played_logger.handlers;

import de.geheimagentnr1.last_played_logger.LastPlayedLogger;
import de.geheimagentnr1.last_played_logger.configs.ServerConfig;
import de.geheimagentnr1.last_played_logger.google_integration.SpreadsheetHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(
	modid = LastPlayedLogger.MODID,
	bus = Mod.EventBusSubscriber.Bus.FORGE,
	value = Dist.DEDICATED_SERVER
)
public class ForgeEventHandler {
	
	
	@SubscribeEvent
	public static void handleServerStartedEvent( ServerStartedEvent event ) {
		
		SpreadsheetHelper.initSheetsService();
	}
	
	@SubscribeEvent
	public static void handlePlayerLoggedInEvent( PlayerEvent.PlayerLoggedInEvent event ) {
		
		if( ServerConfig.getActive() ) {
			new Thread(
				() -> SpreadsheetHelper.insertOrUpdateUser( event.getEntity().getGameProfile().getName() )
			).start();
		}
	}
}
