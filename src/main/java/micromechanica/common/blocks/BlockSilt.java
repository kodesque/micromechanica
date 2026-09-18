package micromechanica.common.blocks;

import micromechanica.common.init.ModBlocks;
import micromechanica.root.Main;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.material.Material;

public class BlockSilt extends BlockSlime {

    public BlockSilt(String id) {
        this.setRegistryName(Main.MODID, id);
        this.setTranslationKey(Main.MODID + "." + id);

        ModBlocks.BLOCKS.add(this);
    }
}
