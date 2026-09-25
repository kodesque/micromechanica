package micromechanica.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InvUtils {

    public static void putItem(ItemStack what, ItemStack where) {

        if (where.getItem() instanceof IStoreItems) {

            IItemHandler handler = ((IStoreItems) (where.getItem())).getItemStackHandler(where);

            for (int i = 0; i < handler.getSlots(); i++) {
                if (handler.insertItem(i, what, true).isEmpty()) {
                    return;
                }
            }
        }
    }

    public static void putItemManual(ItemStack what, ItemStack where, int slot) {
        ((IStoreItems) (where.getItem())).getItemStackHandler(where).insertItem(slot, what, false);
    }

}
