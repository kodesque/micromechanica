package micromechanica.events.front;

import micromechanica.common.items.tools.ItemMagnifier;
import micromechanica.common.templates.ModMachineBase;
import micromechanica.util.RenderMachineInfo;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ModEventHandler {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        PropertyEvents.modifyCraftingResult(event);
    }

    @SubscribeEvent
    public static void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        PropertyEvents.useSpareDurability(event);
    }

    @SubscribeEvent
    public static void onPlayerBreak(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        PropertyEvents.addMiningSpeed(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onMouseInput(GuiScreenEvent.MouseInputEvent event) {
        ModMachineBase.handleRightClick(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        ItemMagnifier.revealProperties(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderTick(TickEvent.RenderTickEvent event) {

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onTextRender(GuiScreenEvent.DrawScreenEvent.Post event) {
        RenderMachineInfo.drawRevealBar(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onTooltipDraw(ItemTooltipEvent event) {
        PropertyEvents.renderPropertiesTooltip(event);
    }

}
