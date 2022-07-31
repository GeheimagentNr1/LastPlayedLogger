/*
package de.geheimagent.last_played_logger;

import de.geheimagent.last_played_logger.google_integration.SpreadsheetLastPlayedWriter;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;


@Mod.EventBusSubscriber( modid = LastPlayedLogger.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE )
public class TestForgeLoader {
	
	
	@SubscribeEvent
	public static void handle( FMLConstructModEvent event ) {
		
		if( ModLoadingContext.get().getActiveContainer().getMod() instanceof LastPlayedLogger mod ) {
			System.out.println( "TESTTESTTEST" );
			SpreadsheetLastPlayedWriter spreadsheetLastPlayedWriter = new SpreadsheetLastPlayedWriter( mod );
			mod.registerForgeEventHandler( spreadsheetLastPlayedWriter );
			mod.registerModEventHandler( spreadsheetLastPlayedWriter );
		}
	}
}
*/
