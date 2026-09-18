package micromechanica.common.init;

import java.util.ArrayList;
import java.util.List;

import micromechanica.common.blocks.BlockSilt;
import micromechanica.common.templates.ModItemBlockBase;
import micromechanica.common.templates.ModBlockBase;
import micromechanica.common.templates.building.ModBlockSlab;
import micromechanica.common.templates.building.ModBlockStairs;
import micromechanica.common.templates.building.ModBlockWall;
import micromechanica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    public static Block TILING_TURBID;
    public static Block TILING_TURBID_DARK;
    public static Block TILING_TURBID_CLEAN;

    public static Block BRICKS_TURBID;
    public static Block GLOW_TURBID;

    public static Block SLAB_TURBID;
    public static Block WALL_TURBID;
    public static Block STAIRS_TURBID;

    public static Block BONES_POROUS;

    public static Block PEAT_RAW;
    public static Block PEAT_MOSSY;

    public static Block SILT;
    public static Block SWAMP_REED;

    public static List<Block> BLOCKS = new ArrayList<Block>();

    public static void initBlocks(IForgeRegistry<Block> iForgeRegistry) {

//        registerBlock(ModBlocks.TILING_TURBID = new ModBlockBase("tilingTurbid", Material.ROCK));
//        registerBlock(ModBlocks.TILING_TURBID_DARK = new ModBlockBase("tilingTurbidDark", Material.ROCK));
//        registerBlock(ModBlocks.TILING_TURBID_CLEAN = new ModBlockBase("tilingTurbidClean", Material.ROCK));
//
//        registerBlock(ModBlocks.BRICKS_TURBID = new ModBlockBase("bricksTurbid", Material.ROCK));
//        registerBlock(ModBlocks.GLOW_TURBID = new ModBlockBase("glowTurbid", Material.GLASS));
//
//        registerBlock(ModBlocks.SLAB_TURBID = new ModBlockSlab(TILING_TURBID));
//        registerBlock(ModBlocks.WALL_TURBID = new ModBlockWall(TILING_TURBID));
//        registerBlock(ModBlocks.STAIRS_TURBID = new ModBlockStairs(TILING_TURBID));
//
//        registerBlock(ModBlocks.BONES_POROUS = new ModBlockBase("bonesPorous", Material.SAND));
//
//        registerBlock(ModBlocks.PEAT_RAW = new ModBlockBase("peatRaw", Material.CLAY));
//        registerBlock(ModBlocks.PEAT_MOSSY = new ModBlockBase("peatMossy", Material.CLAY));
//
//        registerBlock(ModBlocks.SILT = new BlockSilt("silt"));
//        registerBlock(ModBlocks.SWAMP_REED = new ModBlockBase("swampReed", Material.PLANTS));

    }

    private static Block registerBlock(Block block, ItemBlock itemBlock) {
        ForgeRegistries.BLOCKS.register(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
//        proxy.registerModel(itemBlock);
        ModItems.ITEMS.add(itemBlock);
        return block;
    }

    private static Block registerBlock(Block block) {
        ItemBlock itemBlock = new ItemBlock(block);
        ModItems.ITEMS.add(itemBlock);
        return registerBlock(block, itemBlock);
    }

    private static Block registerBlockSpecial(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        return block;
    }

    public static void initItemBlocks(IForgeRegistry<Item> iForgeRegistry) {
        for (Block element : ModBlocks.BLOCKS) {
                ModItemBlockBase itemblock = new ModItemBlockBase(element);
                iForgeRegistry.register(itemblock);
                Main.proxy.registerItemBlockRenderer(itemblock);
            }

        }



}
