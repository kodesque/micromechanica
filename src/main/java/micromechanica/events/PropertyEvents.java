package micromechanica.events;

import java.util.ArrayList;
import java.util.List;

import micromechanica.util.PropertyUtils;
import micromechanica.util.PropertyUtils.PropertyBundle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class PropertyEvents {

    public static void modifyCraftingResult(ItemCraftedEvent event) {
        IInventory inv = event.craftMatrix;
        ItemStack stack = event.crafting;

        PropertyUtils.UseType found = PropertyUtils.isToolOrArmor(stack);
        if (found == null) return;

        ArrayList<ItemStack> list = PropertyUtils.scanCraftingMatrix(inv);
        if (list.isEmpty()) return;

        PropertyBundle props = PropertyUtils.aggregateProperties(list);
        PropertyUtils.writeProperties(stack, props);

        PropertyUtils.applyRelatedAttributes(stack, found, props);

    }

    public static void useSpareDurability(PlayerDestroyItemEvent event) {

        ItemStack original = event.getOriginal();
        ItemStack safe = original.copy();
        EntityPlayer player = event.getEntityPlayer();
        NBTTagCompound nbt = safe.getSubCompound(PropertyUtils.PROPID);

        if (original.getItem() instanceof ItemArmor) return;

        if ((nbt == null) || (nbt.getInteger(PropertyUtils.durability_key) <= 0)) return;

        safe.setItemDamage(original.getItemDamage() - nbt.getInteger(PropertyUtils.durability_key));
        nbt.setInteger(PropertyUtils.durability_key, Math.max((nbt.getInteger(PropertyUtils.durability_key) - original.getItemDamage()), 0));

        if (!player.addItemStackToInventory(safe)) {
            player.dropItem(safe, false);
        }
    }

    public static void addMiningSpeed(PlayerEvent.BreakSpeed event) {

        ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
        PropertyBundle props = PropertyUtils.readProperties(stack);
        if (props.isEmpty()) return;
        if (PropertyUtils.isToolOrArmor(stack) != PropertyUtils.UseType.BREAK) return;

        float toModify = event.getNewSpeed();

        event.setNewSpeed((float)(toModify + (props.hardness * 4)));
    }

    public static void renderPropertiesTooltip(ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();
        NBTTagCompound nbt = stack.getSubCompound(PropertyUtils.PROPID);

        if (nbt == null) return;

        double lightness = nbt.getDouble(PropertyBundle.lightness_key);
        double stiffness = nbt.getDouble(PropertyBundle.stiffness_key);
        double hardness = nbt.getDouble(PropertyBundle.hardness_key);

        if (nbt.getInteger(PropertyUtils.durability_key) != 0) {
            tips.add(PropertyUtils.spare_durability_lang.getFormattedText() + " " + TextFormatting.GREEN + nbt.getInteger(PropertyUtils.durability_key));
        }

        tips.add(PropertyUtils.lightness_lang.getFormattedText() + " " + TextFormatting.YELLOW + lightness);
        tips.add(PropertyUtils.stiffness_lang.getFormattedText() + " " + TextFormatting.YELLOW + stiffness);
        tips.add(PropertyUtils.hardness_lang.getFormattedText() + " " + TextFormatting.YELLOW + hardness);
    }

}
