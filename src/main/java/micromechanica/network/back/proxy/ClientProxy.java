
package micromechanica.network.back.proxy;

import micromechanica.root.Main;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        OBJLoader.INSTANCE.addDomain(Main.MODID);
    }

    @Override
    public void registerItemVariants(Item item, int meta, String... names) {
        for (String name : names) {
            ModelBakery.registerItemVariants(item,
                    new ResourceLocation(Main.MODID, name));
        }
    }

    @Override
    public void registerItemBlockRenderer(ItemBlock itemBlock) {
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void registerMetaRenderer(Item item, String name, int meta, String id) {

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, id));
    }

}
