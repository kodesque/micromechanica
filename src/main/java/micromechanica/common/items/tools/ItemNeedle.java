package micromechanica.common.items.tools;

import micromechanica.common.templates.ModMachineBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemNeedle extends ModMachineBase {
    public ItemNeedle(String name) {
        super(name);
        addSlotsWithBackground(Items.STRING);
    }

    @Override
    public void conductClickedSelf(ItemStack self, ItemStack other) {

    }

    @Override
    public void conductClickedExternal(ItemStack self, ItemStack other) {

    }

    @Override
    public List<String> addInfoToTooltip() {
        return Collections.emptyList();
    }
}
