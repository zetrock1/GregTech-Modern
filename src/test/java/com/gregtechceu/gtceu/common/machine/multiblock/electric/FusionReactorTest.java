package com.gregtechceu.gtceu.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.machine.storage.QuantumTankMachine;
import com.gregtechceu.gtceu.gametest.util.TestUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(false)
@GameTestHolder(GTCEu.MOD_ID)
public class FusionReactorTest {

    public static void insertFluid(GameTestHelper helper, QuantumTankMachine machine, FluidStack stack) {
        machine.getFluidHandlerCap(Direction.UP, false).fill(stack,
                IFluidHandler.FluidAction.EXECUTE);
    }

    public static boolean isTankFluidStackEqual(QuantumTankMachine tank, FluidStack stack) {
        return tank.getStored().getFluid() == stack.getFluid() && tank.getStoredAmount() == stack.getAmount();
    }

    @GameTest(template = "fusionreactor", batch = "fusionReactorTest", required = false)
    public static void fusionReactorTest(GameTestHelper helper) {
        QuantumTankMachine tank1 = (QuantumTankMachine) TestUtils.setMachine(helper, new BlockPos(6, 3, 0),
                GTMachines.SUPER_TANK[1]);
        QuantumTankMachine tank2 = (QuantumTankMachine) TestUtils.setMachine(helper, new BlockPos(8, 3, 0),
                GTMachines.SUPER_TANK[1]);
        QuantumTankMachine tank3 = (QuantumTankMachine) TestUtils.setMachine(helper, new BlockPos(9, 2, 0),
                GTMachines.SUPER_TANK[1]);
        FusionReactorMachine machine = (FusionReactorMachine) ((MetaMachineBlockEntity) helper
                .getBlockEntity(new BlockPos(7, 2, 0)))
                .getMetaMachine();

        insertFluid(helper, tank1, GTMaterials.Deuterium.getFluid(125));
        insertFluid(helper, tank2, GTMaterials.Tritium.getFluid(125));
        machine.setWorkingEnabled(true);

        helper.runAtTickTime(5, () -> {
            helper.assertTrue(machine.isFormed(), "fusion reactor didn't formed");
        });

        helper.runAtTickTime(20, () -> {
            helper.assertTrue(isTankFluidStackEqual(tank3, GTMaterials.Helium.getFluid(FluidStorageKeys.PLASMA, 125)),
                    "fusion reactor didn't generate Helium plasma ");
            helper.succeed();
        });
    }
}
