package micromechanica.util.foundation.root;

import micromechanica.common.init.ModMiscTooltips;
import micromechanica.util.foundation.back.network.Network;
import micromechanica.root.Main;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class Foundation {
    public static final String VERSION = "1.2";
    public static final String BASEMOD_ID = Main.MODID;

    public static void preInit(FMLPreInitializationEvent event) {
        Network.registerPackets();
    }

    public static void init(FMLInitializationEvent event) {}

    public static void postInit(FMLPostInitializationEvent event) {
        ModMiscTooltips.initTooltips();
    }

    public static void serverLoad(FMLServerStartingEvent event) {}
}
