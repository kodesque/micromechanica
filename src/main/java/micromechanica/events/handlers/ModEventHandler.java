package micromechanica.events.handlers;

import micromechanica.events.PropertyEvents;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
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
    public static void onTooltipDraw(ItemTooltipEvent event) {
        PropertyEvents.renderPropertiesTooltip(event);
    }

}
