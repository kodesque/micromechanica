package micromechanica.common.templates;

import micromechanica.util.StorageTypes;
import micromechanica.util.recipes.RecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.input.Mouse;

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

    public abstract List<String> addInfoToTooltip();

    public void addSlots(int size) {

        super.addStorage(StorageTypes.ITEMS, size);
    }

    public void addSlotsWithBackground(Item... slots) {

        super.addStorage(StorageTypes.ITEMS, slots.length);

        this.slotLayout = slots;
    }

    public void addTanks(int amount, int size) {

        for (int i = 0; i < amount; i++) {
            super.addStorage(StorageTypes.FLUID, size);
        }
    }

    public void addBattery(int capacity) {
        super.addStorage(StorageTypes.ENERGY, capacity);
    }

    public Item[] getSlotLayout() {
        return slotLayout;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (addInfoToTooltip() != null && !addInfoToTooltip().isEmpty()) {
            tooltip.add(addInfoToTooltip().toString());
        }
    }
}
