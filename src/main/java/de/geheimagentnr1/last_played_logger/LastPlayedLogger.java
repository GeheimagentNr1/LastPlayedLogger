package de.geheimagentnr1.last_played_logger;

import de.geheimagentnr1.last_played_logger.configs.ServerConfig;
import de.geheimagentnr1.last_played_logger.google_integration.SpreadsheetLastPlayedWriter;
import de.geheimagentnr1.last_played_logger.google_integration.SpreedsheetEventHandler;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.TestAnnotation;
import de.geheimagentnr1.minecraft_forge_api.config.AbstractConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.function.BiFunction;


@Mod( LastPlayedLogger.MODID )
public class LastPlayedLogger extends AbstractMod {
	
	
	public static final String MODID = "last_played_logger";
	
	public LastPlayedLogger() {
		
		super( MODID, 1, "test" );
	}
	
	@Override
	protected void initMod() {
		
		SpreadsheetLastPlayedWriter spreadsheetLastPlayedWriter = new SpreadsheetLastPlayedWriter( this );
		registerForgeEventHandler( spreadsheetLastPlayedWriter );
		
		registerConfig(
			ModConfig.Type.SERVER,
			( type, abstractMod ) -> new ServerConfig( type, abstractMod, spreadsheetLastPlayedWriter )
		);
	}
	
	@TestAnnotation
	public static void init() {
	
	}
}
