package micromechanica.common.items.tools;

import micromechanica.common.templates.ModMachineBase;
import micromechanica.network.back.Network;
import micromechanica.network.back.packets.PacketUniformServer;
import micromechanica.network.front.EnumFunctions;
import micromechanica.util.PropertyUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.Collections;
import java.util.List;

public class ItemMagnifier extends ModMachineBase {

    public static int hoverTimer = 0;
    public static boolean drawing = false;
    public static final String[] dots = {".", "..", "..."};

    public ItemMagnifier(String name) {
        super(name);
    }

    @Override
    public void conductClickedSelf(ItemStack self, ItemStack other) {

    }

    @Override
    public void conductClickedExternal(ItemStack self, ItemStack other) {

    }

    public void conductClickedSelf(ItemStack clickedOn) {}
    public void conductClickedExternal(ItemStack clickedWith) {}

    @Override
    public List<String> addInfoToTooltip() {
        return Collections.emptyList();
    }

    public static void revealProperties(TickEvent.ClientTickEvent event) {

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

                    hoverTimer++;
                    drawing = true;

                    if (hoverTimer >= 100) {
                        Network.sendPacket(new PacketUniformServer(EnumFunctions.ROLL_PROPERTIES_MAGNIFIER, index), null);
                        drawing = false;
                        hoverTimer = 0;
                    }

                    return;

                }
            }
        }

        drawing = false;
        hoverTimer = 0;

    }

}
