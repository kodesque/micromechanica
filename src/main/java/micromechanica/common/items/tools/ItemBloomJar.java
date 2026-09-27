package micromechanica.common.items.tools;

import micromechanica.common.templates.ModItemCapableBase;
import micromechanica.common.templates.ModMachineBase;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemBloomJar extends ModMachineBase {
    public ItemBloomJar(String name) {
        super(name, false);
        this.setSpecialCase();
    }

    @Override
    public List<String> addInfoToTooltip() {
        return Collections.emptyList();
    }
}
