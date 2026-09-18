package micromechanica.common.templates;

import micromechanica.common.init.ModBlocks;
import micromechanica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlockBase extends Block{

    public ModBlockBase(String name, Material materialIn) {
        super(materialIn);

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        this.setCreativeTab(Main.tabMod);

        ModBlocks.BLOCKS.add(this);
    }

}
