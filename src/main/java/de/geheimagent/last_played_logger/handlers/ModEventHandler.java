package de.geheimagent.last_played_logger.handlers;

import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.events.AbstractModEventHandler;
import net.minecraftforge.fml.event.config.ModConfigEvent;


public class ModEventHandler extends AbstractModEventHandler {
	
	
	public ModEventHandler( AbstractMod mod ) {
		
		super( mod );
	}
	
	@Override
	public void handleModConfigLoadingEvent( ModConfigEvent.Loading event ) {
		
		super.handleModConfigLoadingEvent( event );
	}
	
	@Override
	public void handleModConfigReloadingEvent( ModConfigEvent.Reloading event ) {
		
		super.handleModConfigReloadingEvent( event );
	}
}
