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
        super(name, false);
        this.setSpecialCase();
    }

    @Override
    public List<String> addInfoToTooltip() {
        return Collections.emptyList();
    }

}
