package de.geheimagentnr1.last_played_logger.elements.gametests;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;


@GameTestHolder( "last_played_logger" )
public class LastPlayedLoggerGameTests {

    @GameTest( templateNamespace = "neoforge", template = "floor_3x3x3" )
    public static void modLoadsSuccessfully( GameTestHelper helper ) {

        helper.succeed();
    }
}
