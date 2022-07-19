package de.geheimagent.last_played_logger.handlers;

import de.geheimagent.last_played_logger.configs.ServerConfig;
import de.geheimagent.last_played_logger.google_integration.SpreadsheetHelper;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.events.AbstractForgeEventHandler;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.config.ModConfig;


public class ForgeEventHandler extends AbstractForgeEventHandler {
	
	
	private final ServerConfig serverConfig;
	
	private final SpreadsheetHelper spreadsheetHelper;
	
	public ForgeEventHandler( AbstractMod mod ) {
		
		super( mod );
		serverConfig = mod.getConfig( ModConfig.Type.SERVER, ServerConfig.class ).orElseThrow();
		spreadsheetHelper = new SpreadsheetHelper( serverConfig );
	}
	
	@Override
	public void handleServerStartedEvent( ServerStartingEvent event ) {
		
		spreadsheetHelper.initSheetsService();
	}
	
	@Override
	public void handlePlayerLoggedInEvent( PlayerEvent.PlayerLoggedInEvent event ) {
		
		if( serverConfig.getActive() ) {
			new Thread(
				() -> spreadsheetHelper.insertOrUpdateUser( event.getPlayer().getGameProfile().getName() )
			).start();
		}
	}
}
