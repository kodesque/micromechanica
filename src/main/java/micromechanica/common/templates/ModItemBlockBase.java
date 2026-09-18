package micromechanica.common.templates;

import micromechanica.common.init.ModItems;
import micromechanica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ModItemBlockBase extends ItemBlock{

    public ModItemBlockBase(Block block) {
        super(block);

        this.setRegistryName(block.getRegistryName());
        this.setTranslationKey(block.getTranslationKey());

        this.setCreativeTab(Main.tabMod);

        ModItems.ITEMS.add(this);
    }
}
