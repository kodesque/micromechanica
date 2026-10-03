package micromechanica.common.items.tools;

import micromechanica.common.templates.ModItemBase;
import micromechanica.common.templates.ModItemCapableBase;
import micromechanica.common.templates.ModMachineBase;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemMortar extends ModMachineBase {
    public ItemMortar(String name) {
        super(name, true);
        this.addSlots(4);
    }
}
