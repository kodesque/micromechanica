package micromechanica.util.foundation.back.blockstates;

import akka.japi.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collection;

public class MetaCodec {

    @SuppressWarnings("unchecked")
    static Pair<Pair<Integer, Integer>, Integer>[] ratios = new Pair[] {
            pack(2, 2, 1),
            pack(3, 4, 2),
            pack(5, 8, 3),
            pack(9, 16, 4)
    };

    static enum EnumWriteOrder {

        POWERED(CommonBlockStates.VAL_POWERED),
        UP(CommonBlockStates.VAL_UP),

        FULL_ROTATION(CommonBlockStates.VAL_FACING),
        VER_ROTATION(CommonBlockStates.VAL_FACING_VERTICAL),
        HOR_ROTATION(CommonBlockStates.VAL_FACING_HORIZONTAL),

        ARB_BOOLEAN(PropertyBool.class),
        ARB_INTEGER(PropertyInteger.class);

        private final Class<? extends IProperty<?>> propertyClass;
        private final PropertyHelper<?> property;

        private EnumWriteOrder(Class<? extends IProperty<?>> propertyClass) {
            this.propertyClass = propertyClass;
            this.property = null;
        }

        private EnumWriteOrder(PropertyHelper<?> property) {
            this.property = property;
            this.propertyClass = null;
        }

        public Object getValue() {
            return this.property != null ? this.property : this.propertyClass;
        }

        public boolean matches(IProperty<?> candidate) {

            if (this.property != null)
                return this.property.equals(candidate);

            return this.propertyClass.isInstance(candidate);
        }

        public static EnumWriteOrder byProperty(IProperty<?> property) {

            for (EnumWriteOrder value : values()) {

                if (value.matches(property))
                    return value;
            }

            return null;
        }

        public static int getByValue(PropertyHelper<?> property) {

            EnumWriteOrder result = byProperty(property);

            return result != null ? result.ordinal() : -1;
        }

        public static int getByClass(IProperty<?> property) {

            EnumWriteOrder result = byProperty(property);

            return result != null ? result.ordinal() : -1;
        }
    }

    public static int encode(IBlockState state) {

        ArrayList<IProperty<?>> properties = new ArrayList<IProperty<?>>(state.getPropertyKeys());

        int comb = 1;

        int bits = 0;
        int meta = 0;

        for (EnumWriteOrder order : EnumWriteOrder.values()) {

            IProperty<?> property = null;

            for (IProperty<?> candidate : properties) {

                Object value = order.getValue();

                if (value instanceof PropertyHelper) {

                    if (candidate.equals(value)) {
                        property = candidate;
                        break;
                    }

                } else if (value instanceof Class) {

                    Class<?> clazz = (Class<?>) value;

                    if (clazz.isInstance(candidate)) {
                        property = candidate;
                        break;
                    }
                }
            }

            if (property == null) {
                continue;
            }

            properties.remove(property);

            if (property instanceof PropertyBool) {

                comb *= 2;

                Boolean value = (Boolean) state.getValue(property);

                meta |= (value ? 1 : 0) << bits;

                bits += 1;

            } else if (property instanceof PropertyInteger) {

                Collection<Integer> col =
                        ((PropertyInteger) property).getAllowedValues();

                int valueCount = col.size();
                int value = (Integer) state.getValue(property);

                comb *= valueCount;

                for (Pair<Pair<Integer, Integer>, Integer> element : ratios) {

                    if ((valueCount >= element.first().first()) &&
                            (valueCount <= element.first().second())) {

                        meta |= value << bits;

                        bits += element.second();

                        break;
                    }
                }

            } else if (property.equals(CommonBlockStates.VAL_FACING)) {

                int valueCount = EnumFacing.values().length;
                int value = ((EnumFacing) state.getValue(property)).ordinal();

                comb *= valueCount;

                for (Pair<Pair<Integer, Integer>, Integer> element : ratios) {

                    if ((valueCount >= element.first().first()) &&
                            (valueCount <= element.first().second())) {

                        meta |= value << bits;

                        bits += element.second();

                        break;
                    }
                }

            } else if (property.equals(CommonBlockStates.VAL_FACING_HORIZONTAL)) {

                int valueCount = EnumFacing.HORIZONTALS.length;
                int value =
                        ((EnumFacing) state.getValue(property))
                                .getHorizontalIndex();

                comb *= valueCount;

                for (Pair<Pair<Integer, Integer>, Integer> element : ratios) {

                    if ((valueCount >= element.first().first()) &&
                            (valueCount <= element.first().second())) {

                        meta |= value << bits;

                        bits += element.second();

                        break;
                    }
                }

            } else if (property.equals(CommonBlockStates.VAL_FACING_VERTICAL)) {

                int valueCount =
                        EnumFacing.VALUES.length -
                                EnumFacing.HORIZONTALS.length;

                EnumFacing facing =
                        (EnumFacing) state.getValue(property);

                int value =
                        facing == EnumFacing.DOWN ? 0 : 1;

                comb *= valueCount;

                for (Pair<Pair<Integer, Integer>, Integer> element : ratios) {

                    if ((valueCount >= element.first().first()) &&
                            (valueCount <= element.first().second())) {

                        meta |= value << bits;

                        bits += element.second();

                        break;
                    }
                }
            }
        }

        if (comb > 16) {
            throw new IndexOutOfBoundsException();
        }

        return meta;
    }

    private static Pair<Pair<Integer, Integer>, Integer> pack(int a, int b, int c) {
        return new Pair<Pair<Integer, Integer>, Integer>(new Pair<Integer, Integer>(a, b), c);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static IBlockState decode(int meta, Block block) {

        IBlockState state = block.getDefaultState();

        int bits = 0;

        for (EnumWriteOrder order : EnumWriteOrder.values()) {

            IProperty<?> property = null;

            for (IProperty<?> candidate : state.getPropertyKeys()) {

                Object value = order.getValue();

                if (value instanceof PropertyHelper) {

                    if (candidate.equals(value)) {
                        property = candidate;
                        break;
                    }

                } else if (value instanceof Class) {

                    Class<?> clazz = (Class<?>) value;

                    if (clazz.isInstance(candidate)) {
                        property = candidate;
                        break;
                    }
                }
            }

            if (property == null) {
                continue;
            }

            if (property instanceof PropertyBool) {

                boolean value = ((meta >> bits) & 1) != 0;

                IProperty prop = property;
                state = state.withProperty(prop, value);

                bits += 1;
            }

            else if (property instanceof PropertyInteger) {

                Collection<Integer> values =
                        ((PropertyInteger) property).getAllowedValues();

                int valueCount = values.size();

                int sizeBits = 0;

                for (Pair<Pair<Integer, Integer>, Integer> r : ratios) {
                    if (valueCount >= r.first().first() &&
                            valueCount <= r.first().second()) {

                        sizeBits = r.second();
                        break;
                    }
                }

                int raw = (meta >> bits) & ((1 << sizeBits) - 1);

                Integer[] arr = values.toArray(new Integer[0]);

                if (raw >= arr.length) {
                    raw = 0;
                }

                IProperty prop = property;
                state = state.withProperty(prop, arr[raw]);

                bits += sizeBits;
            }

            else if (property.equals(CommonBlockStates.VAL_FACING)) {

                int valueCount = EnumFacing.values().length;

                int sizeBits = 0;

                for (Pair<Pair<Integer, Integer>, Integer> r : ratios) {
                    if (valueCount >= r.first().first() &&
                            valueCount <= r.first().second()) {

                        sizeBits = r.second();
                        break;
                    }
                }

                int raw = (meta >> bits) & ((1 << sizeBits) - 1);

                EnumFacing value = EnumFacing.values()[raw];

                IProperty prop = property;
                state = state.withProperty(prop, value);

                bits += sizeBits;
            }

            else if (property.equals(CommonBlockStates.VAL_FACING_HORIZONTAL)) {

                int valueCount = EnumFacing.HORIZONTALS.length;

                int sizeBits = 0;

                for (Pair<Pair<Integer, Integer>, Integer> r : ratios) {
                    if (valueCount >= r.first().first() &&
                            valueCount <= r.first().second()) {

                        sizeBits = r.second();
                        break;
                    }
                }

                int raw = (meta >> bits) & ((1 << sizeBits) - 1);

                EnumFacing value = EnumFacing.HORIZONTALS[raw];

                IProperty prop = property;
                state = state.withProperty(prop, value);

                bits += sizeBits;
            }

            else if (property.equals(CommonBlockStates.VAL_FACING_VERTICAL)) {

                int raw = (meta >> bits) & 1;

                EnumFacing value = raw == 0 ? EnumFacing.DOWN : EnumFacing.UP;

                IProperty prop = property;
                state = state.withProperty(prop, value);

                bits += 1;
            }
        }

        return state;
    }

}