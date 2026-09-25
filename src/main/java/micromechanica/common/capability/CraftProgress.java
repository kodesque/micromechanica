package micromechanica.common.capability;

import micromechanica.api.ICraftProgress;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CraftProgress implements ICraftProgress {

    @CapabilityInject(ICraftProgress.class)
    public static Capability<ICraftProgress> CRAFT_PROGRESS;

    private int value;

    public static void register() {
        CapabilityManager.INSTANCE.register(
                ICraftProgress.class,
                new Capability.IStorage<ICraftProgress>() {

                    @Override
                    public NBTBase writeNBT(
                            Capability<ICraftProgress> capability,
                            ICraftProgress instance,
                            EnumFacing side) {

                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setInteger("value", instance.getValue());
                        return nbt;
                    }

                    @Override
                    public void readNBT(
                            Capability<ICraftProgress> capability,
                            ICraftProgress instance,
                            EnumFacing side,
                            NBTBase nbt) {

                        instance.setValue(
                                ((NBTTagCompound) nbt).getInteger("value")
                        );
                    }
                },
                CraftProgress::new
        );
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}