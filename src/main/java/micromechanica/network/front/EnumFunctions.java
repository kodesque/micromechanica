package micromechanica.network.front;

import micromechanica.util.PropertyUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Random;
import java.util.function.BiConsumer;

public enum EnumFunctions {

    /* methods that should be executed on the opposite side */
    /* keep in mind that these should only require values that are serializable */

    /* ItemStack cannot be transferred! New instance is created on every deserialization! */

    ROLL_PROPERTIES_MAGNIFIER((ctx, args) -> {
        int index = (int) args[0];
        ItemStack stack = ctx.getServerHandler().player.inventory.getStackInSlot(index);

        PropertyUtils.rollAndWriteProperties(stack, new Random());

    });

    private final BiConsumer<MessageContext, Object[]> function;

    EnumFunctions(BiConsumer<MessageContext, Object[]> function) {
        this.function = function;
    }

    public void execute(MessageContext context, Object[] args) {
        function.accept(context, args);
    }
}


