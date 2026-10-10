package micromechanica.common.templates;

import akka.japi.Pair;
import micromechanica.api.IChargeStorage;
import micromechanica.api.ICraftProgress;
import micromechanica.common.capability.ChargeStorage;
import micromechanica.common.capability.CraftProgress;
import micromechanica.util.StorageTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.HashMap;

public class ModItemCapableBase extends ModItemBase {

    private final HashMap<ModMachineBase, Pair<StorageTypes, Integer>> expectedStorage = new HashMap<>();

    public ModItemCapableBase(String name) {
        super(name);
    }

    public void addStorage(ModMachineBase machine, StorageTypes type, int size) {
        expectedStorage.put(machine, new Pair<>(type, size));
    }

    public boolean hasExpectedStorage(ModMachineBase machine, StorageTypes type) {
        return expectedStorage.get(machine) != null && expectedStorage.get(machine).first().equals(type);
    }

    public <T> T getRealStorage(ItemStack stack, StorageTypes type) {
        switch (type) {
            case ITEMS:
                return (T)stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            case ENERGY:
                return (T)stack.getCapability(CapabilityEnergy.ENERGY, null);
            case FLUID:
                return (T)stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            case PROGRESS:
                return (T)stack.getCapability(CraftProgress.CRAFT_PROGRESS, null);
            case CHARGE:
                return (T)stack.getCapability(ChargeStorage.CHARGE_STORAGE, null);
        }
        return null;
    }

    public Pair<StorageTypes, Integer> getExpectedStorage(ModMachineBase item) {
        return expectedStorage.get(item);
    }
}
