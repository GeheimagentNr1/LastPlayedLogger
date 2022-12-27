package de.geheimagent.last_played_logger.google_integration;

import de.geheimagent.last_played_logger.configs.ServerConfig;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.events.ForgeEventHandlerInterface;
import de.geheimagentnr1.minecraft_forge_api.events.ModEventHandlerInterface;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import javax.annotation.Nonnull;


@RequiredArgsConstructor
public class SpreedsheetEventHandler implements ForgeEventHandlerInterface, ModEventHandlerInterface {
	
	
	private final AbstractMod abstractMod;
	
	private SpreadsheetLastPlayedWriter spreadsheetLastPlayedWriter;
	
	@Nonnull
	private SpreadsheetLastPlayedWriter getOrBuildSpreadsheetLastPlayedWriter() {
		
		if( spreadsheetLastPlayedWriter == null ) {
			spreadsheetLastPlayedWriter = new SpreadsheetLastPlayedWriter( abstractMod );
		}
		return spreadsheetLastPlayedWriter;
	}
	
	private ServerConfig getServerConfig() {
		
		return abstractMod.getConfig( ModConfig.Type.SERVER, ServerConfig.class ).orElse( null );
	}
	
	@SubscribeEvent
	@Override
	public void handleServerStartedEvent( ServerStartingEvent event ) {
		
		getOrBuildSpreadsheetLastPlayedWriter().initSheetsService();
	}
	
	@SubscribeEvent
	@Override
	public void handlePlayerLoggedInEvent( PlayerEvent.PlayerLoggedInEvent event ) {
		
		if( getServerConfig().getActive() ) {
			new Thread(
				() -> getOrBuildSpreadsheetLastPlayedWriter().insertOrUpdateUser(
					event.getPlayer().getGameProfile().getName()
				)
			).start();
		}
	}
	
	@SubscribeEvent
	@Override
	public void handleModConfigLoadingEvent( ModConfigEvent.Loading event ) {
		
		getOrBuildSpreadsheetLastPlayedWriter().initSheetsService();
	}
	
	@SubscribeEvent
	@Override
	public void handleModConfigReloadingEvent( ModConfigEvent.Reloading event ) {
		
		getOrBuildSpreadsheetLastPlayedWriter().initSheetsService();
	}
}
