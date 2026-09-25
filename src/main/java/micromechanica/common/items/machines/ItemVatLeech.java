package micromechanica.common.items.machines;

import micromechanica.common.templates.ModMachineBase;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemVatLeech extends ModMachineBase {
    public ItemVatLeech(String name) {
        super(name);
    }

    @Override
    public void conductClickedSelf(ItemStack clickedOn) {

    }

    @Override
    public void conductClickedExternal(ItemStack clickedWith) {

    }

    @Override
    public List<String> addInfoToTooltip() {
        return Collections.emptyList();
    }
}
