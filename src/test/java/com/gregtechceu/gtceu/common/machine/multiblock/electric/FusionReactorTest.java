package com.gregtechceu.gtceu.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.GTCEu;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(false)
@GameTestHolder(GTCEu.MOD_ID)
public class FusionReactorTest {

    @GameTest(template = "fusionreactor", batch = "reactorTests", required = false)
    public static void fusionReactorTest(GameTestHelper helper) {
        // helper.setBlock(6,3,0, GTMachines.SUPER_TANK[1].getBlock());
        helper.runAtTickTime(40, () -> {
            helper.succeed();
        });
    }
}
