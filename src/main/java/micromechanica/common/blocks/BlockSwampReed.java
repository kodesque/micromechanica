package micromechanica.common.blocks;

import micromechanica.common.init.ModBlocks;
import micromechanica.root.Main;
import net.minecraft.block.BlockTallGrass;

public class BlockSwampReed extends BlockTallGrass {

    public BlockSwampReed(String id) {
        this.setRegistryName(Main.MODID, id);
        this.setTranslationKey(Main.MODID + "." + id);

        ModBlocks.BLOCKS.add(this);
    }

}
