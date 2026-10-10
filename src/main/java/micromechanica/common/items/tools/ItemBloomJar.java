package micromechanica.common.items.tools;

import micromechanica.common.init.ModItems;
import micromechanica.common.templates.ModMachineBase;

public class ItemBloomJar extends ModMachineBase {
    public ItemBloomJar(String name) {
        super(name, false);
        this.addSlotsWithBackground(this, ModItems.DIAMOND_GLASS_BLEND, ModItems.DOLOMITE);
        this.setSpecialCase();
    }
}
