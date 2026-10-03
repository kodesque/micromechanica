package micromechanica.common.templates;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModItemVirtualBase extends ModItemBase {
    public ModItemVirtualBase(String name) {
        super(name);

        this.setMaxStackSize(1);
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        stack.shrink(1);
    }
}
