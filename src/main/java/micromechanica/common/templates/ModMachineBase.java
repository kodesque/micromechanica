package micromechanica.common.templates;

import micromechanica.util.StorageTypes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ModMachineBase extends ModItemCapableBase {

    //items used for slot backgrounds, if defined
    private Item[] slotLayout;
    boolean clickWith;
    boolean specialCase;

            /*
                true -> the recipe is initiated when the player clicks on the machine with this item. Item is never consumed.
                used for virtual items, such as flywheel for spindle and pestle for mortar

                false -> on this item with a machine. Item is always consumed.
                used for actual items, such as when stitching a piece of leather or welding iron plates
            */

    public ModMachineBase(String name, boolean clickWith) {
        super(name);

        this.clickWith = clickWith;
        this.setMaxStackSize(1);
    }

    public boolean isClickWith() {
        return clickWith;
    }

    public void setSpecialCase() {
        specialCase = true;
    }

    public boolean isSpecialCase() {
        return specialCase;
    }

//    public void conductClickedSelf(ItemStack self, ItemStack other) {
//
//    }
//
//    public void conductClickedExternal(ItemStack self, ItemStack other) {
//
//    }

    public void addSlots(int size) {

        this.addStorage(StorageTypes.ITEMS, size);
    }

    public void addSlotsWithBackground(Item... slots) {

        this.addStorage(StorageTypes.ITEMS, slots.length);

        this.slotLayout = slots;
    }

    public void addTanks(int amount, int size) {

        for (int i = 0; i < amount; i++) {
            this.addStorage(StorageTypes.FLUID, size);
        }
    }

    public void addBattery(int capacity) {
        this.addStorage(StorageTypes.ENERGY, capacity);
    }

    public Item[] getSlotLayout() {
        return slotLayout;
    }
}
