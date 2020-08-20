package de.geheimagent.last_played_logger.handlers;

import de.geheimagent.last_played_logger.configs.MainConfig;
import de.geheimagent.last_played_logger.google_integration.SpreadsheetHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;


@SuppressWarnings( "unused" )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
    
    
    @SubscribeEvent
    public static void handleServerStarted( FMLServerStartedEvent event ) {
        
        if( event.getServer().isDedicatedServer() ) {
            SpreadsheetHelper.initSheetsService();
        }
    }
    
    @SubscribeEvent
    public static void handlePlayerLoggedInEvent( PlayerEvent.PlayerLoggedInEvent event ) {
    
        if( ServerLifecycleHooks.getCurrentServer().isDedicatedServer() && MainConfig.getActive() ) {
            new Thread( () -> SpreadsheetHelper.insertOrUpdateUser( event.getPlayer().getGameProfile().getName() ) )
                .start();
        }
    }
}
