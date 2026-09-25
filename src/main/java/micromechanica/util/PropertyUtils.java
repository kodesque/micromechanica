package micromechanica.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import micromechanica.root.Main;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.oredict.OreDictionary;

public class PropertyUtils {

    public static String PROPID = Main.MODID + "." + "properties";

    public static TextComponentTranslation lightness_lang = new TextComponentTranslation("tooltip." + PROPID + "." + PropertyBundle.lightness_key);
    public static TextComponentTranslation stiffness_lang = new TextComponentTranslation("tooltip." + PROPID + "." + PropertyBundle.stiffness_key);
    public static TextComponentTranslation hardness_lang = new TextComponentTranslation("tooltip." + PROPID + "." + PropertyBundle.hardness_key);

    public static TextComponentTranslation spare_durability_lang = new TextComponentTranslation("tooltip." + PROPID + "." + "spare_durability");

    public static String durability_key = "durability";
    public static String damage_key = "damage";
    public static String attack_speed_key = "attack_speed";
    public static String movement_speed_key = "movement_speed";
    public static String protection_key = "protection";

    public static enum UseType {
        BREAK,
        ATTACK,
        DEFEND,
        OTHER
    }

    public static ArrayList<ItemStack> scanCraftingMatrix(IInventory inv) {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (isIngotOrGem(stack)) {
                list.add(stack);
            }
        }

        return list;
    }

    public static void applyRelatedAttributes(ItemStack stack, UseType found, PropertyBundle props) {

        NBTTagCompound nbt = stack.getSubCompound(PROPID);

        //general logic, processing in *PropertyHandler#useSpareDurability*
        nbt.setInteger(PropertyUtils.durability_key, (int)(props.stiffness * 100));

        switch(found) {
            case BREAK:

                //processing additional logic in *PropertyHandler#addMiningSpeed*
                break;
            case DEFEND:

                EntityEquipmentSlot applicable = ((ItemArmor)stack.getItem()).getEquipmentSlot();

                double baseArmor = findBaseAttribute(SharedMonsterAttributes.ARMOR.getName(), stack, applicable)
                        .getAmount();

                UUID id_moveSpeed = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f00" + applicable.getSlotIndex() + "1");
                AttributeModifier attrMoveSpeed = new AttributeModifier(id_moveSpeed, movement_speed_key, 0.005 * props.lightness, 0);
                stack.addAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), attrMoveSpeed, applicable);

                UUID id_protect = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f00" + applicable.getIndex() + "2");
                AttributeModifier attrProtection = new AttributeModifier(id_protect, protection_key, baseArmor + (0.5 * props.hardness), 0);
                stack.addAttributeModifier(SharedMonsterAttributes.ARMOR.getName(), attrProtection, applicable);

                break;
            case ATTACK:

                double baseSpeed = findBaseAttribute(SharedMonsterAttributes.ATTACK_SPEED.getName(), stack, EntityEquipmentSlot.MAINHAND)
                .getAmount();

                UUID id_attackSpeed = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0003");
                AttributeModifier attrAttackSpeed = new AttributeModifier(id_attackSpeed, attack_speed_key, baseSpeed + (props.lightness), 0);
                stack.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getName(), attrAttackSpeed, EntityEquipmentSlot.MAINHAND);

                double baseAttack = findBaseAttribute(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), stack, EntityEquipmentSlot.MAINHAND)
                        .getAmount();

                UUID id_attack = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0004");
                AttributeModifier attrAttack = new AttributeModifier(id_attack, damage_key, baseAttack + (3 * props.hardness), 0);
                stack.addAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), attrAttack, EntityEquipmentSlot.MAINHAND);

                break;
            case OTHER:
                //no additional logic provided, skipping
                break;
        }
    }

    public static AttributeModifier findBaseAttribute(String attributeName, ItemStack stack, EntityEquipmentSlot slot) {

        Collection<AttributeModifier> attrs = null;
        String[] armor_id = {
                "845DB27C-C624-495F-8C9F-6020A9A58B6B",
                "D8499B04-0E66-4726-AB29-64469D734E0D",
                "9F3D476D-C118-4544-8365-64846904B48E",
                "2AD3F246-FEE1-4E67-B886-69FD380BB150"
        };
        String weapon_id = null;

        attrs = stack.getAttributeModifiers(slot).get((attributeName));

        //        if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
        //
        //            if (attributeName.equals(SharedMonsterAttributes.ATTACK_SPEED.getName())) {
        //                weapon_id = "FA233E1C-4180-4865-B01B-BCCE9785ACA3";
        //            } else if (attributeName.equals(SharedMonsterAttributes.ATTACK_DAMAGE.getName())) {
        //                weapon_id = "CB3F55D3-645C-4F38-A497-9C13A33DB5CF";
        //            }
        //
        //            if (weapon_id == null) return null;
        //
        //            for (AttributeModifier attr : attrs) {
        //                if (attr.getID().equals(UUID.fromString(weapon_id)))
        //                    return attr;
        //            }
        //
        //        } else {
        //
        //            for (AttributeModifier attr : attrs) {
        //                for (String id : armor_id) {
        //                    if (attr.getID().equals(UUID.fromString(id)))
        //                        return attr;
        //                }
        //            }
        //        }

        if (attributeName.equals(SharedMonsterAttributes.ATTACK_SPEED.getName())) {

            for (AttributeModifier attr : attrs) {
                if (attr.getID().equals(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3")))
                    return attr;
            }

        } else if (attributeName.equals(SharedMonsterAttributes.ATTACK_DAMAGE.getName())) {

            attrs = stack.getItem().getItemAttributeModifiers(slot).get(attributeName);

            for (AttributeModifier attr : attrs) {
                if (attr.getID().equals(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")))
                    return attr;
            }

        } else if (attributeName.equals(SharedMonsterAttributes.ARMOR.getName())) {

            for (AttributeModifier attr : attrs) {
                for (String id : armor_id) {
                    if (attr.getID().equals(UUID.fromString(id)))
                        return attr;
                }
            }
        }

        return null;
    }

    public static UseType isToolOrArmor(ItemStack stack) {

        Set<String> classes = stack.getItem().getToolClasses(stack);

        if (classes.contains("pickaxe") || classes.contains("axe") || classes.contains("shovel"))
            return UseType.BREAK;
        else if (stack.getItem() instanceof ItemSword)
            return UseType.ATTACK;
        else if (stack.getItem() instanceof ItemArmor)
            return UseType.DEFEND;
        else if (stack.getItem() instanceof ItemTool)
            return UseType.OTHER;
        return null;
    }

    public static boolean isIngotOrGem(ItemStack stack) {
        if (stack.isEmpty()) return false;

        for (int id : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(id);
            if (name.startsWith("ingot") || name.startsWith("gem"))
                return true;
        }
        return false;
    }

    public static boolean isOre(ItemStack stack) {
        if (stack.isEmpty()) return false;

        for (int id : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(id);
            if (name.startsWith("ore"))
                return true;
        }
        return false;
    }

    public static boolean isDust(ItemStack stack) {
        if (stack.isEmpty()) return false;

        for (int id : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(id);
            if (name.startsWith("dust"))
                return true;
        }
        return false;
    }

    public static void rollAndWriteProperties(ItemStack stack, Random rand) {

        PropertyBundle toAdd = new PropertyBundle((rand.nextInt(11) / 10.0), (rand.nextInt(11) / 10.0), (rand.nextInt(11) / 10.0));

        writeProperties(stack, toAdd);
    }

    public static PropertyBundle aggregateProperties(ArrayList<ItemStack> stacks) {

        double lightness = 0;
        double durability = 0;
        double hardness = 0;

        for (ItemStack stack : stacks) {
            NBTTagCompound nbt = stack.getSubCompound(PROPID);
            if (nbt == null) {
                continue;
            }

            lightness = cutDouble(lightness + nbt.getDouble(PropertyBundle.lightness_key));
            durability = cutDouble(durability + nbt.getDouble(PropertyBundle.stiffness_key));
            hardness = cutDouble(hardness + nbt.getDouble(PropertyBundle.hardness_key));
        }

        return new PropertyBundle(lightness, durability, hardness);
    }

    public static PropertyBundle readProperties(ItemStack stack) {

        NBTTagCompound nbt = stack.getSubCompound(PROPID);
        if (nbt == null) return returnEmptyBundle();

        double lightness = nbt.getDouble(PropertyBundle.lightness_key);
        double durability = nbt.getDouble(PropertyBundle.stiffness_key);
        double hardness = nbt.getDouble(PropertyBundle.hardness_key);

        return new PropertyBundle(lightness, durability, hardness);
    }

    public static void writeProperties(ItemStack stack, PropertyBundle properties) {
        NBTTagCompound nbt = stack.getOrCreateSubCompound(PROPID);

        nbt.setDouble(PropertyBundle.lightness_key, properties.lightness);
        nbt.setDouble(PropertyBundle.stiffness_key, properties.stiffness);
        nbt.setDouble(PropertyBundle.hardness_key, properties.hardness);
    }

    public static double cutDouble(double value) {

        return BigDecimal.valueOf(value).setScale(1, RoundingMode.FLOOR).doubleValue();
    }

    public static PropertyBundle returnEmptyBundle() {
        return new PropertyBundle(0, 0, 0);
    }

    public static class PropertyBundle {

        public static String lightness_key = "lightness";
        public static String stiffness_key = "stiffness";
        public static String hardness_key = "hardness";

        public double lightness = 0;
        public double stiffness = 0;
        public double hardness = 0;

        public PropertyBundle(double lightness, double stiffness, double hardness) {
            this.lightness = lightness;
            this.stiffness = stiffness;
            this.hardness = hardness;
        }

        public boolean isEmpty() {
            return this.lightness == 0 && this.stiffness == 0 && this.hardness == 0;
        }

    }

}
