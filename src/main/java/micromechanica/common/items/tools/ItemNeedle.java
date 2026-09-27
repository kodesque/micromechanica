package micromechanica.common.items.tools;

import micromechanica.common.templates.ModMachineBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemNeedle extends ModMachineBase {
    public ItemNeedle(String name) {
        super(name, false);
        addSlotsWithBackground(Items.STRING);
    }

    @Override
    public List<String> addInfoToTooltip() {
        return Collections.emptyList();
    }
}
