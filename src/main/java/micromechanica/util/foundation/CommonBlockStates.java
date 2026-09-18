package micromechanica.util.foundation;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public class CommonBlockStates {

    public static PropertyDirection VAL_FACING;

    public static PropertyDirection VAL_FACING_HORIZONTAL;

    public static PropertyDirection VAL_FACING_VERTICAL;

    public static PropertyBool VAL_POWERED;

    public static PropertyBool VAL_UP;

    public static void initStates() {

        VAL_FACING = PropertyDirection.create("facing");
        VAL_FACING_HORIZONTAL = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        VAL_FACING_VERTICAL = PropertyDirection.create("facing", EnumFacing.Plane.VERTICAL);

        VAL_POWERED = PropertyBool.create("on");
        VAL_UP = PropertyBool.create("upper_part");
    }

}