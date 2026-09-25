package micromechanica.root;

import micromechanica.common.capability.CraftProgress;
import micromechanica.common.init.ModBlocks;
import micromechanica.common.init.ModCapabilities;
import micromechanica.common.init.ModItems;
import micromechanica.network.back.Network;
import micromechanica.network.back.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.NAME)
public class Main {
    public static final String MODID = "micromechanica";
    public static final String NAME = "Micromechanica";
    public static final String VERSION = "0.0.1-ALPHA";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "micromechanica.network.back.proxy.ClientProxy", serverSide = "micromechanica.network.back.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Main instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModCapabilities.initCapabilities();

        Network.registerPackets();
        proxy.preInit(event);
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    }

    public static CreativeTabs tabMod = new CreativeTabs("tabMicromechanica") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Items.DIAMOND);
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
                    item.getSubItems(Main.tabMod, list);
                }
            }
        }
    };
}
