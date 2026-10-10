package micromechanica.util.foundation.back.capabilities;

import micromechanica.api.IChargeStorage;
import micromechanica.api.ICraftProgress;
import micromechanica.common.capability.ChargeStorage;
import micromechanica.common.capability.CraftProgress;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class ProviderUniform implements ICapabilityProvider {

    private final IItemHandler items;
    private final IEnergyStorage energy;
    private final IFluidHandler fluid;

    private final ICraftProgress progress;
    private final IChargeStorage charge;

    public ProviderUniform(
            IItemHandler items,
            IEnergyStorage energy,
            IFluidHandler fluid,
            ICraftProgress progress,
            IChargeStorage charge) {

        this.items = items;
        this.energy = energy;
        this.fluid = fluid;
        this.progress = progress;
        this.charge = charge;
    }

    @Override
    public boolean hasCapability(
            @NotNull Capability<?> capability,
            EnumFacing facing) {

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return items != null;

        if (capability == CapabilityEnergy.ENERGY)
            return energy != null;

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return fluid != null;

        if (capability == CraftProgress.CRAFT_PROGRESS)
            return progress != null;

        if (capability == ChargeStorage.CHARGE_STORAGE)
            return charge != null;

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(
            @NotNull Capability<T> capability,
            EnumFacing facing) {

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) items;

        if (capability == CapabilityEnergy.ENERGY)
            return (T) energy;

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) fluid;

        if (capability == CraftProgress.CRAFT_PROGRESS)
            return (T) progress;

        if (capability == ChargeStorage.CHARGE_STORAGE)
            return (T) charge;

        return null;
    }
}
