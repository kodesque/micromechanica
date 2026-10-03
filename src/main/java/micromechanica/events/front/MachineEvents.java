package micromechanica.events.front;

import micromechanica.common.items.tools.ItemMagnifier;
import micromechanica.common.templates.ModMachineBase;
import micromechanica.util.foundation.back.network.Network;
import micromechanica.util.foundation.back.network.packets.PacketUniformServer;
import micromechanica.network.front.EnumFunctions;
import micromechanica.util.PropertyUtils;
import micromechanica.util.StorageTypes;
import micromechanica.util.recipes.MachineRecipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.input.Mouse;

public class MachineEvents {

    public static void handleMouseClick(GuiScreenEvent.MouseInputEvent event) {
        if (Mouse.getEventButton() == 1) {

            if (!(event.getGui() instanceof GuiContainer)) {
                return;
            }

            Slot slot = ((GuiContainer) event.getGui()).getSlotUnderMouse();
            if (slot != null) {

                ItemStack stackCarried = Minecraft.getMinecraft().player.inventory.getItemStack();
                ItemStack stackLying = slot.getStack();

                if (stackCarried.getItem() instanceof ModMachineBase || stackLying.getItem() instanceof ModMachineBase) {

                    if (stackCarried.getItem() instanceof ModMachineBase && ((ModMachineBase) stackCarried.getItem()).getRealStorage(stackCarried, StorageTypes.ITEMS) != null) {

                        ModMachineBase carried = ((ModMachineBase) (stackCarried.getItem()));

                        if (!carried.isSpecialCase()) {
                            MachineRecipeManager.tryCraft(stackCarried, stackLying);
                            event.setCanceled(true);
                        }

                    } else if (stackLying.getItem() instanceof ModMachineBase &&  ((ModMachineBase) stackLying.getItem()).getRealStorage(stackLying, StorageTypes.ITEMS) != null) {

                        ModMachineBase lying = ((ModMachineBase) (stackLying.getItem()));

                        IItemHandler itemHandler = ((IItemHandler) lying.getRealStorage(stackLying, StorageTypes.ITEMS));

                        for (int i = 0; i < itemHandler.getSlots(); i++) {
                            if (itemHandler.insertItem(i, stackCarried, true).isEmpty()) {
                                break;
                            }
                        }

                        if (!lying.isSpecialCase()) {
                            ItemStack[] result = MachineRecipeManager.tryCraft(stackLying, stackCarried);

                            for (int i = 0; i < result.length; i++) {
                                if (!Minecraft.getMinecraft().player.inventory.addItemStackToInventory(result[i])) {
                                    Minecraft.getMinecraft().player.dropItem(result[i], false);
                                }
                            }
                        }
                    }

                    event.setCanceled(true);
                }
            }
        }
    }

    public static void handleMouseHold(TickEvent.ClientTickEvent event) {

        EntityPlayerSP player = Minecraft.getMinecraft().player;

        if (player == null) return;

        Container container = player.openContainer;
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;

        if (container == null || !(gui instanceof GuiContainer)) return;

        GuiContainer guiContainer = (GuiContainer)gui;
        Slot slot = guiContainer.getSlotUnderMouse();

        if (slot == null) return;

        if (Mouse.isButtonDown(1)) {

            ItemStack stackLying = slot.getStack();
            int index = slot.getSlotIndex();
            ItemStack stackCarried = Minecraft.getMinecraft().player.inventory.getItemStack();

            if (stackCarried.getItem() instanceof ItemMagnifier && PropertyUtils.isIngotOrGem(stackLying) && PropertyUtils.readProperties(stackLying).isEmpty()) {

//                if (PropertyUtils.isOre(stackLying)) {
//
//                } else if (PropertyUtils.isIngotOrGem(stackLying)) {
//
//                } else if (PropertyUtils.isDust(stackLying)) {
//
//                }

                if (PropertyUtils.isIngotOrGem(stackLying)) {

                    ItemMagnifier.hoverTimer++;
                    ItemMagnifier.drawing = true;

                    if (ItemMagnifier.hoverTimer >= 100) {
                        Network.sendPacket(new PacketUniformServer(EnumFunctions.ROLL_PROPERTIES_MAGNIFIER, index), null);
                        ItemMagnifier.drawing = false;
                        ItemMagnifier.hoverTimer = 0;
                    }

                    return;

                }
            }
        }

        ItemMagnifier.drawing = false;
        ItemMagnifier.hoverTimer = 0;

    }

}
