package micromechanica.common.items;

import micromechanica.common.templates.ModItemBase;
import micromechanica.network.Network;
import micromechanica.root.Main;
import micromechanica.util.PropertyUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.lwjgl.input.Mouse;

public class ItemMagnifier extends ModItemBase {
    public ItemMagnifier(String name) {
        super(name);
    }

    public static void bindAttributeMold(GuiScreenEvent.MouseInputEvent event) {
        if (Mouse.getEventButton() == 1) {

            Slot slot = ((GuiContainer)event.getGui()).getSlotUnderMouse();
            if (slot != null) {

                ItemStack stackLying = slot.getStack();
                ItemStack stackCarried = Minecraft.getMinecraft().player.inventory.getItemStack();

                if (stackCarried.getItem() instanceof ItemMagnifier) {

                    if (PropertyUtils.isOre(stackLying)) {

                    } else if (PropertyUtils.isIngotOrGem(stackLying)) {

                    } else if (PropertyUtils.isDust(stackLying)) {

                    }

                    event.setCanceled(true);
                }
            }
        }
    }

    //items(gems, ingots) should be treated differently than ores

    //scanning is conducted in the inventory (just like with Thaumometer)
}
