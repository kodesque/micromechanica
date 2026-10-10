package micromechanica.common.capability;

import com.sun.xml.internal.bind.v2.TODO;
import micromechanica.api.IChargeStorage;
import micromechanica.api.ICraftProgress;
import micromechanica.util.foundation.back.capabilities.BaseArithmetics;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ChargeStorage extends BaseArithmetics implements IChargeStorage {

    @CapabilityInject(IChargeStorage.class)
    public static Capability<IChargeStorage> CHARGE_STORAGE;

    //TODO: make their capacity not fixed, but dependent on the machine

    public ChargeStorage() {
        super(0, 200);
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(
                IChargeStorage.class,
                new Capability.IStorage<IChargeStorage>() {

                    @Override
                    public NBTBase writeNBT(
                            Capability<IChargeStorage> capability,
                            IChargeStorage instance,
                            EnumFacing side) {

                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setInteger("value", instance.getValue());
                        return nbt;
                    }

                    @Override
                    public void readNBT(
                            Capability<IChargeStorage> capability,
                            IChargeStorage instance,
                            EnumFacing side,
                            NBTBase nbt) {

                        instance.setValue(
                                ((NBTTagCompound) nbt).getInteger("value")
                        );
                    }
                },
                ChargeStorage::new
        );
    }

}
