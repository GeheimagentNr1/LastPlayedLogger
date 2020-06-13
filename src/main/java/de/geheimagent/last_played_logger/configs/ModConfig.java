package de.geheimagent.last_played_logger.configs;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import de.geheimagent.last_played_logger.LastPlayedLogger;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ModConfig {
    
    
    private final static Logger LOGGER = LogManager.getLogger();
    
    private final static String mod_name = "Last Played Logger";
    
    private final static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
    private final static ForgeConfigSpec CONFIG;
    
    private final static ForgeConfigSpec.BooleanValue ACTIVE;
    
    private final static ForgeConfigSpec.ConfigValue<String> SPREADSHEETID;
    
    private final static ForgeConfigSpec.ConfigValue<String> TAB_NAME;
    
    static {
        
        ACTIVE = BUILDER.comment( "Shall the mod be active or not?" ).define( "active", false );
        SPREADSHEETID = BUILDER.comment( "ID of the Spreadsheet." ).define( "spreadsheetID", "" );
        TAB_NAME = BUILDER.comment( "Name of the Spreadsheet tab." ).define( "tab_name", "" );
        
        CONFIG = BUILDER.build();
    }
    
    public static void load() {
    
        CommentedFileConfig configData = CommentedFileConfig.builder( FMLPaths.CONFIGDIR.get().resolve(
            LastPlayedLogger.MODID + ".toml" ) ).sync().autosave().writingMode( WritingMode.REPLACE ).build();
        
        LOGGER.info( "Loading \"{}\" Config", mod_name );
        configData.load();
        CONFIG.setConfig( configData );
        LOGGER.info( "{} = {}", ACTIVE.getPath(), ACTIVE.get() );
        LOGGER.info( "{} = {}", SPREADSHEETID.getPath(), SPREADSHEETID.get() );
        LOGGER.info( "{} = {}", TAB_NAME.getPath(), TAB_NAME.get() );
        LOGGER.info( "\"{}\" Config loaded", mod_name );
    }
    
    public static String getModName() {
        
        return mod_name;
    }
    
    public static boolean getActive() {
        
        return ACTIVE.get();
    }
    
    public static String getSpreadsheetID() {
        
        return SPREADSHEETID.get();
    }
    
    public static String getTabName() {
        
        return TAB_NAME.get();
    }
}
