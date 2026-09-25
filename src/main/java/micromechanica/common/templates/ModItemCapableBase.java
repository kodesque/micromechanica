package micromechanica.common.templates;

import akka.japi.Pair;
import micromechanica.api.ICraftProgress;
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

    public void addStorage(StorageTypes type, int size) {
        expectedStorage.put((ModMachineBase) this, new Pair<>(type, size));
    }

    public boolean hasExpectedStorage(ModMachineBase machine, StorageTypes type) {
        return expectedStorage.get(machine).first().equals(type);
    }

    public <T> T getRealStorage(ItemStack stack, StorageTypes type) {
        switch (type) {
            case ITEMS:
                return (T)stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            case ENERGY:
                return (T)stack.getCapability(CapabilityEnergy.ENERGY, null);
            case FLUID:
                return (T)stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        }
        return null;
    }

    public boolean hasCraftProgress(ItemStack stack) {
        return stack.getCapability(CraftProgress.CRAFT_PROGRESS, null) != null;
    }

    public ICraftProgress getCraftProgress(ItemStack stack) {
        return stack.getCapability(CraftProgress.CRAFT_PROGRESS, null);
    }

    public Pair<StorageTypes, Integer> getExpectedStorage(ModMachineBase item) {
        return expectedStorage.get(item);
    }
}
