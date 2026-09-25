package micromechanica.common.init;

import java.util.ArrayList;
import java.util.List;

import micromechanica.common.items.machines.ItemFoundry;
import micromechanica.common.items.machines.ItemGalvanizer;
import micromechanica.common.items.tools.ItemBloomJar;
import micromechanica.common.items.tools.ItemMagnifier;
import micromechanica.common.items.machines.ItemMixer;
import micromechanica.common.items.tools.ItemMortar;
import micromechanica.common.items.machines.ItemReforger;
import micromechanica.common.items.machines.ItemVatLeech;
import micromechanica.common.items.machines.ItemVatSlag;
import micromechanica.common.templates.ModItemBase;
import micromechanica.root.Main;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static Item DEBUG;

    public static Item MAGNIFIER;
    public static Item CRUSHER;
    public static Item FOUNDRY;
    public static Item VATSLAG;
    public static Item VATLEACH;
    public static Item MIXER;
    public static Item REFORGER;
    public static Item GALVANIZER;

    public static Item DIAMOND_GLASS;
    public static Item DIAMOND_GLASS_BLEND;
    public static Item QUILTED_LEATHER;
    public static Item DOLOMA;
    public static Item YARN;
    public static Item TCHOOGUN_INGOT;
    public static Item TCHOOGUN_BLEND;

    public static Item BLOOMJAR;
    public static Item MORTAR;
    public static Item SPINDLE;
    public static Item NEEDLE;

    public static Item FLYWHEEL_VIRTUAL;
    public static Item PESTLE_VIRTUAL;

    public static List<Item> ITEMS = new ArrayList<Item>();

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

//        registerItem(ModItems.DEBUG = new ItemDebug("debug"));

        registerItem(ModItems.MAGNIFIER = new ItemMagnifier("magnifier"));
        registerItem(ModItems.CRUSHER = new ItemMortar("crusher"));
        registerItem(ModItems.FOUNDRY = new ItemFoundry("foundry"));
        registerItem(ModItems.VATSLAG = new ItemVatSlag("vat_slag"));
        registerItem(ModItems.VATLEACH = new ItemVatLeech("vat_leaching"));
        registerItem(ModItems.MIXER = new ItemMixer("mixer"));
        registerItem(ModItems.REFORGER = new ItemReforger("reforger"));
        registerItem(ModItems.GALVANIZER = new ItemGalvanizer("galvanizer"));

        registerItem(ModItems.DIAMOND_GLASS = new ModItemBase("diamond_glass"));
        registerItem(ModItems.DIAMOND_GLASS_BLEND = new ModItemBase("diamond_glass_blend"));
        registerItem(ModItems.TCHOOGUN_INGOT = new ModItemBase("tchoogun_ingot"));
        registerItem(ModItems.TCHOOGUN_BLEND = new ModItemBase("tchoogun_blend"));
        registerItem(ModItems.QUILTED_LEATHER= new ModItemBase("quilted_leather"));
        registerItem(ModItems.DOLOMA= new ModItemBase("doloma"));
        registerItem(ModItems.YARN= new ModItemBase("yarn"));

        registerItem(ModItems.BLOOMJAR = new ItemBloomJar("bloomjar"));
        registerItem(ModItems.MORTAR = new ItemBloomJar("mortar"));
        registerItem(ModItems.SPINDLE = new ItemBloomJar("spindle"));
        registerItem(ModItems.NEEDLE = new ItemBloomJar("needle"));

    }

    private static Item registerItem(Item item) {
        ForgeRegistries.ITEMS.register(item);

        Main.proxy.registerItemRenderer(item, 0, "inventory");
        return item;
    }

    @SideOnly(Side.CLIENT)
    public static void initModelsAndVariants() {
        for (Item item : ModItems.ITEMS) {
            initModelAndVariants(item);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void initModelAndVariants(Item item) {
        if (!item.getHasSubtypes()) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), null));
        }
    }

}
