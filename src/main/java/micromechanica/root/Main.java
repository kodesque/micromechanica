package micromechanica.root;

import micromechanica.common.init.ModCapabilities;
import micromechanica.common.init.ModMiscTooltips;
import micromechanica.network.back.proxy.CommonProxy;
import micromechanica.util.foundation.root.Foundation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.NAME)
public class Main {
    public static final String MODID = "micromechanica";
    public static final String NAME = "Micromechanica";
    public static final String VERSION = "0.0.1-ALPHA";

    public static CreativeTabs tabMod = new ModCreativeTab("tabMicromechanica");

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "micromechanica.network.back.proxy.ClientProxy", serverSide = "micromechanica.network.back.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Main instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        Foundation.preInit(event);

        ModCapabilities.initCapabilities();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Foundation.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Foundation.postInit(event);

        ModMiscTooltips.initTooltips();
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {}
}
