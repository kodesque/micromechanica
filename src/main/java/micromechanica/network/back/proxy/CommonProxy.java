package micromechanica.network.back.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void registerItemVariants(Item item, int meta, String... names) {}

    public void registerItemBlockRenderer(ItemBlock itemblock) {};

    public void registerItemRenderer(Item item, int meta, String id) {}

    public void registerMetaRenderer(Item item, String name, int meta, String id) {}

}
