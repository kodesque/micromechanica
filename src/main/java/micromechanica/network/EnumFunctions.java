package micromechanica.network;

import micromechanica.network.packets.PacketUniformServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.BiConsumer;

public enum EnumFunctions {

    /* methods that should be executed on the opposite side */
    /* keep in mind that these should only require values that are serializable */

    FOO((ctx, args) -> {
        ItemStack stack = (ItemStack) args[0];

        EntityPlayerMP player = ctx.getServerHandler().player;

        System.out.println("Hello world!");
    });

    private final BiConsumer<MessageContext, Object[]> function;

    EnumFunctions(BiConsumer<MessageContext, Object[]> function) {
        this.function = function;
    }

    public void execute(MessageContext context, Object[] args) {
        function.accept(context, args);

        ItemStack stack = new ItemStack(Items.DIAMOND);
    }
}

// Example usage:
// Network.sendToPlayerServer(new PacketUniformServer(EnumFunctions.FOO, stack));


