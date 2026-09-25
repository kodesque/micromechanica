package micromechanica.events.back;

import micromechanica.util.PropertyUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DebugEventHandler {

    //TODO: don't forget to remove on release
    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickItem event) {
        ItemStack item = event.getItemStack();
        if (!event.getWorld().isRemote) {
            if (PropertyUtils.isIngotOrGem(item)) {
                PropertyUtils.rollAndWriteProperties(item, event.getWorld().rand);
                System.out.print(item.getSubCompound(PropertyUtils.PROPID));
            }
        }
    }

}
