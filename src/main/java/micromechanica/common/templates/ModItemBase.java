package micromechanica.common.templates;

import micromechanica.common.init.ModItems;
import micromechanica.root.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModItemBase extends Item  {

    List<String> tooltipInfo = new ArrayList<>();

    public ModItemBase(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        this.setCreativeTab(Main.tabMod);

        ModItems.ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (this.tooltipInfo != null && !tooltipInfo.isEmpty()) {
            tooltip.addAll(tooltipInfo);
        }
    }

    public void addInfoToTooltip(List<String> tooltipInfo) {
        this.tooltipInfo = tooltipInfo;
    }

}
