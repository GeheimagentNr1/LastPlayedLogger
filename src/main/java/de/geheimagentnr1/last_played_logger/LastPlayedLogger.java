package de.geheimagentnr1.last_played_logger;

import de.geheimagentnr1.last_played_logger.configs.ServerConfig;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.TestAnnotation;
import de.geheimagentnr1.minecraft_forge_api.events.ModEventHandlerInterface;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.annotation.ElementType;
import java.util.function.Consumer;


@Mod( LastPlayedLogger.MODID )
public class LastPlayedLogger /*extends AbstractMod*/ {
	
	
	public static final String MODID = "last_played_logger";
	
	/*public LastPlayedLogger() {
		
		super( MODID );
	}
	
	@Override
	protected void initMod() {
		
		ModLoadingContext.get().registerConfig( ModConfig.Type.SERVER, ServerConfig.CONFIG );
		registerConfig( ModConfig.Type.SERVER, ServerConfig::new );
		disableDisplayTest();
		registerModEventHandler( new ModEventHandlerInterface() {
			
			@Override
			@SubscribeEvent
			public void handleModConfigLoadingEvent( ModConfigEvent.Loading event ) {
				
				ModLoadingContext.get()
					.getActiveContainer()
					.getModInfo()
					.getOwningFile()
					.getFile()
					.getScanResult()
					.getAnnotations()
					.forEach(
						new Consumer<ModFileScanData.AnnotationData>() {
							
							@Override
							public void accept( ModFileScanData.AnnotationData annotationData ) {
								
								if( Type.getType( TestAnnotation.class )
									.equals( annotationData.annotationType() ) && annotationData.targetType() == ElementType.METHOD ) {
									annotationData = annotationData;
								}
							}
						}
					);
				ModEventHandlerInterface.super.handleModConfigLoadingEvent( event );
			}
			
			@SubscribeEvent
			public void handle( GatherDataEvent gatherDataEvent ) {
				
				try {
					Class.forName( "com.google.api.services.sheets.v4.SheetsScopes" );
				} catch( ClassNotFoundException e ) {
					throw new RuntimeException( e );
				}
			}
		} );
		*//*SpreedsheetEventHandler spreedsheetEventHandler = new SpreedsheetEventHandler( this );
		registerForgeEventHandler( spreedsheetEventHandler );
		registerModEventHandler( spreedsheetEventHandler );*//*
	}*/
	
	@TestAnnotation
	public static void init() {
	
	}
}
