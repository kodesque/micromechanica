package micromechanica.common.templates;

import micromechanica.common.init.ModItems;
import micromechanica.root.Main;
import net.minecraft.item.Item;

public class ModItemBase extends Item  {

    public ModItemBase(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        this.setCreativeTab(Main.tabMod);

        ModItems.ITEMS.add(this);
    }

}
