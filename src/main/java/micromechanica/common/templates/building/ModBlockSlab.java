package micromechanica.common.templates.building;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;

public class ModBlockSlab extends net.minecraft.block.BlockSlab{

    private Block parent;

    public ModBlockSlab(Block parent) {
        super(parent.getDefaultState().getMaterial());

        this.parent = parent;
    }

    @Override
    public String getTranslationKey(int meta) {
        return super.getTranslationKey();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return null;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return null;
    }

}
