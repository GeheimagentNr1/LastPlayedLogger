package de.geheimagentnr1.last_played_logger;

import de.geheimagentnr1.last_played_logger.configs.ServerConfig;
import de.geheimagentnr1.last_played_logger.google_integration.SpreadsheetWritter;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;


@Mod( LastPlayedLogger.MODID )
public class LastPlayedLogger extends AbstractMod {
	
	
	@NotNull
	public static final String MODID = "last_played_logger";
	
	@NotNull
	@Override
	public String getModId() {
		
		return MODID;
	}
	
	@Override
	protected void initMod() {
		
		DistExecutor.safeRunWhenOn(
			Dist.DEDICATED_SERVER,
			() -> () -> {
				SpreadsheetWritter spreadsheetWritter =
					registerEventHandler( new SpreadsheetWritter( this ) );
				registerConfig( abstractMod -> new ServerConfig( abstractMod, spreadsheetWritter ) );
			}
		);
	}
}
