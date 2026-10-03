package micromechanica.root;

import micromechanica.common.init.ModBlocks;
import micromechanica.common.init.ModItems;
import micromechanica.common.templates.ModItemVirtualBase;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTab extends CreativeTabs {

    public ModCreativeTab(String label) {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(ModItems.MAGNIFIER);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> list)
    {
        {
            for (Block block : ModBlocks.BLOCKS) {
                Item toAdd = Item.getItemFromBlock(block);
                list.add(new ItemStack(toAdd));
            }

            for (Item item : ModItems.ITEMS) {
                if (!(item instanceof ModItemVirtualBase)) {
                    item.getSubItems(Main.tabMod, list);
                }
            }
        }
    }
}
