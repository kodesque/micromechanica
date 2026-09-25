package micromechanica.common.templates;

import micromechanica.util.InvUtils;
import micromechanica.util.StorageTypes;
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
import org.lwjgl.input.Mouse;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ModMachineBase extends ModItemCapableBase {

    //items used for slot backgrounds, if defined
    private Item[] slotLayout;
    boolean clickWith;

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

    public void conductClickedSelf(ItemStack self, ItemStack other) {

    }

    public void conductClickedExternal(ItemStack self, ItemStack other) {

    }

    public abstract List<String> addInfoToTooltip();

    /*
    +1 slot and additional fluid storage are used for storing recipe results!
    It MUST be handled differently and not being counted as missing a crafting ingredient!
    no additional energy storage is added, as basic recipes aren't expected to generate energy.
    energy storage is ONLY for machines that generate energy

    Slot is not being added when a machine works with items in external inventories
     */

    public void addSlots(int size) {

        int realSize = this.clickWith ? size : size + 1;

        super.addStorage(StorageTypes.ITEMS, realSize);
    }

    public void addSlotsWithBackground(Item... slots) {

        int realSize = this.clickWith ? slots.length : slots.length + 1;

        super.addStorage(StorageTypes.ITEMS, slots.length + 1);

        this.slotLayout = slots;
    }

    public void addTanks(int amount, int size) {

        int realSize = this.clickWith ? amount : amount + 1;

        for (int i = 0; i < realSize; i++) {
            super.addStorage(StorageTypes.FLUID, size);
        }
    }

    public void addBattery(int capacity) {
        super.addStorage(StorageTypes.ENERGY, capacity);
    }

    public Item[] getSlotLayout() {
        return slotLayout;
    }

    public static void handleRightClick(GuiScreenEvent.MouseInputEvent event) {
        if (Mouse.getEventButton() == 1) {

            if (!(event.getGui() instanceof GuiContainer)) {
                return;
            }

            Slot slot = ((GuiContainer) event.getGui()).getSlotUnderMouse();
            if (slot != null) {

                ItemStack stackCarried = Minecraft.getMinecraft().player.inventory.getItemStack();
                ItemStack stackLying = slot.getStack();

                if (stackCarried.getItem() instanceof ModMachineBase) {

                    ModMachineBase carried = ((ModMachineBase) (stackCarried.getItem()));
                    carried.conductClickedSelf(stackCarried, stackLying);
                    event.setCanceled(true);

                } else if (stackLying.getItem() instanceof ModMachineBase) {

                    ModMachineBase lying = ((ModMachineBase) (stackLying.getItem()));
                    InvUtils.putItem(stackLying, stackCarried);
                    lying.conductClickedExternal(stackLying, stackCarried);

                    event.setCanceled(true);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (addInfoToTooltip() != null && !addInfoToTooltip().isEmpty()) {
            tooltip.add(addInfoToTooltip().toString());
        }
    }
}
