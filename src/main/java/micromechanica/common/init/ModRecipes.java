package micromechanica.common.init;

import micromechanica.common.templates.ModMachineBase;
import micromechanica.root.Main;
import micromechanica.util.recipes.MachineRecipe;
import micromechanica.util.recipes.RecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModRecipes {

    public static final ResourceLocation baseGroup = new ResourceLocation(Main.MODID, "base");

    public static void initRecipes(IForgeRegistry<IRecipe> iForgeRegistry) {
        initCraftingTable();
        initFurnace();
        initModMachines();
    }

    private static void initCraftingTable() {
//        registerShaped(
//                ModItems.QUILTED_LEATHER,
//                " L ",
//                "SNS",
//                " L ",
//                'S', Items.STRING,
//                'L', Items.LEATHER,
//                'N', Items.NETHERBRICK
//        );

        registerShaped(
                ModItems.MAGNIFIER,
                "DI ",
                "IL ",
                "  L",
                'D', ModItems.DIAMOND_GLASS,
                'I', ModItems.TCHOOGUN_INGOT,
                'L', ModItems.QUILTED_LEATHER
        );

//        registerShapeless(ModItems.DIAMOND_GLASS_BLEND, Ingredient.fromItem(Items.SUGAR), Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 15)), Ingredient.fromItem(Items.QUARTZ), Ingredient.fromItems(Items.DIAMOND));
//        registerShapeless(ModItems.TCHOOGUN_BLEND, Ingredient.fromItem(Items.FLINT), Ingredient.fromItems(Items.IRON_INGOT), Ingredient.fromItem(Items.GUNPOWDER), Ingredient.fromItems(Items.BLAZE_POWDER));
    }

    private static void initFurnace() {

//        registerFurnace(ModItems.DIAMOND_GLASS_BLEND, ModItems.DIAMOND_GLASS, 1.0F);
//        registerFurnace(ModItems.TCHOOGUN_BLEND, ModItems.TCHOOGUN_INGOT, 1.0F);

    }

    private static void initModMachines() {
        registerBloomJar(
                ModItems.TCHOOGUN_BLEND,
                ModItems.DOLOMA,
                ModItems.TCHOOGUN_INGOT,
                2400);
        registerBloomJar(
                ModItems.DIAMOND_GLASS_BLEND,
                ModItems.DOLOMA,
                ModItems.DIAMOND_GLASS,
                2400);

        registerMortar(
                Items.FLINT,
                Items.BLAZE_ROD,
                Items.IRON_INGOT,
                Items.GUNPOWDER,
                ModItems.TCHOOGUN_BLEND,
                20);
        registerMortar(
                Items.SUGAR,
                new ItemStack(Items.DYE, 15),
                Items.QUARTZ,
                Items.DIAMOND,
                ModItems.DIAMOND_GLASS_BLEND,
                10);

        registerNeedle(
                Items.LEATHER,
                ModItems.YARN,
                ModItems.QUILTED_LEATHER,
                150);

        registerSpindle(
                Items.GOLD_INGOT,
                Items.NETHERBRICK,
                Items.MAGMA_CREAM,
                Items.STRING,
                ModItems.YARN,
                100);
    }

    private static void registerBloomJar(Object mix, Object flux, Object output, int progress) {
        RecipeHandler.addRecipe(new MachineRecipe.MachineRecipeFactory()
                .begin(new ItemStack(ModItems.BLOOMJAR), progress, false)
                .setItemsIn(mix, flux)
                .setItemsOut(output)
                .write());
    }

    private static void registerMortar(Object i1, Object i2, Object i3, Object i4, Object output, int progress) {
        RecipeHandler.addRecipe(new MachineRecipe.MachineRecipeFactory()
                .begin(new ItemStack(ModItems.MORTAR), progress, false)
                .setItemsIn(i1, i2, i3, i4)
                .setSpecialInput(ModItems.PESTLE_VIRTUAL)
                .setItemsOut(output)
                .write());
    }

    private static void registerSpindle(Object i1, Object i2, Object i3, Object i4, Object output, int progress) {
        RecipeHandler.addRecipe(new MachineRecipe.MachineRecipeFactory()
                .begin(new ItemStack(ModItems.SPINDLE), progress, true)
                .setItemsIn(i1, i2, i3, i4)
                .setSpecialInput(ModItems.FLYWHEEL_VIRTUAL)
                .setItemsOut(output)
                .write());
    }

    private static void registerNeedle(Object input, Object yarn, Object output, int progress) {
                RecipeHandler.addRecipe(new MachineRecipe.MachineRecipeFactory()
                .begin(new ItemStack(ModItems.NEEDLE), progress, true)
                .setItemsIn(yarn)
                .setSpecialInput(input)
                .setItemsOut(output)
                .write());
    }

    private static void registerShaped(Object result, Object... recipe) {

        ItemStack actualResult = ItemStack.EMPTY;

        if (result instanceof Item) {
            actualResult = new ItemStack((Item)result);
        } else if (result instanceof Block) {
            actualResult = new ItemStack((Block)result);
        } else if (result instanceof ItemStack) {
            actualResult = (ItemStack) result;
        }

        GameRegistry.addShapedRecipe(
                new ResourceLocation(Main.MODID, actualResult.getItem().getRegistryName().getPath()),
                baseGroup,
                actualResult,
                recipe
        );
    }

    private static void registerShapeless(Object result, Ingredient... recipe) {

        ItemStack actualResult = ItemStack.EMPTY;

        if (result instanceof Item) {
            actualResult = new ItemStack((Item)result);
        } else if (result instanceof Block) {
            actualResult = new ItemStack((Block)result);
        } else if (result instanceof ItemStack) {
            actualResult = (ItemStack)result;
        }

        GameRegistry.addShapelessRecipe(
                new ResourceLocation(Main.MODID, actualResult.getItem().getRegistryName().getPath()),
                baseGroup,
                actualResult,
                recipe
        );
    }

    private static void registerFurnace(Object input, Object output, float XP) {

        ItemStack actualInput = ItemStack.EMPTY;
        ItemStack actualOutput = ItemStack.EMPTY;

        if (input instanceof Item) {
            actualInput = new ItemStack((Item)input);
        } else if (input instanceof Block) {
            actualInput = new ItemStack((Block)input);
        } else if (input instanceof ItemStack) {
            actualInput = (ItemStack) input;
        }

        if (output instanceof Item) {
            actualOutput = new ItemStack((Item)output);
        } else if (output instanceof Block) {
            actualOutput = new ItemStack((Block)output);
        } else if (output instanceof ItemStack) {
            actualOutput = (ItemStack) output;
        }

        GameRegistry.addSmelting(actualInput, actualOutput, XP);
    }

}

