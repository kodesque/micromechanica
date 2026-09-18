package micromechanica.common.init;

import java.util.ArrayList;
import java.util.List;

import micromechanica.common.items.ItemDebug;
import micromechanica.common.items.ItemFoundry;
import micromechanica.common.items.ItemGalvanizer;
import micromechanica.common.items.ItemMagnifier;
import micromechanica.common.items.ItemMixer;
import micromechanica.common.items.ItemMortar;
import micromechanica.common.items.ItemReforger;
import micromechanica.common.items.ItemVatLeech;
import micromechanica.common.items.ItemVatSlag;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static Item DEBUG;

    public static Item MAGNIFIER;
    public static Item MORTAR;
    public static Item FOUNDRY;
    public static Item VATSLAG;
    public static Item VATLEACH;
    public static Item MIXER;
    public static Item REFORGER;
    public static Item GALVANIZER;

    public static List<Item> ITEMS = new ArrayList<Item>();

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        registerItem(ModItems.DEBUG = new ItemDebug("debug"));

        registerItem(ModItems.MAGNIFIER = new ItemMagnifier("magnifier"));
        registerItem(ModItems.MORTAR = new ItemMortar("mortar"));
        registerItem(ModItems.FOUNDRY = new ItemFoundry("foundry"));
        registerItem(ModItems.VATSLAG = new ItemVatSlag("vat_slag"));
        registerItem(ModItems.VATLEACH = new ItemVatLeech("vat_leaching"));
        registerItem(ModItems.MIXER = new ItemMixer("mixer"));
        registerItem(ModItems.REFORGER = new ItemReforger("reforger"));
        registerItem(ModItems.GALVANIZER = new ItemGalvanizer("galvanizer"));
    }

    private static Item registerItem(Item item) {
        ForgeRegistries.ITEMS.register(item);
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
