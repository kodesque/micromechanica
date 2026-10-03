package micromechanica.common.items.tools;

import micromechanica.common.templates.ModItemCapableBase;
import micromechanica.common.templates.ModMachineBase;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemSpindle extends ModMachineBase {
    public ItemSpindle(String name) {
        super(name, true);
        this.addSlots(4);
    }
}
