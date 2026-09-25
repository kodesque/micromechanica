package micromechanica.network.back.packets;

import io.netty.buffer.ByteBuf;
import micromechanica.network.front.EnumFunctions;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketUniformClient implements IMessage {

    private transient EnumFunctions action;

    private byte[] bytes;
    private short[] shorts;
    private int[] ints;
    private long[] longs;
    private float[] floats;
    private double[] doubles;
    private boolean[] booleans;
    private char[] chars;

    private String[] strings;
    private ItemStack[] itemStacks;
    private NBTTagCompound[] nbtTags;

    public PacketUniformClient() {

    }

    public PacketUniformClient(EnumFunctions function, Object... variables) {
        this.action = function;
        collectVariables(variables);
    }

    private void collectVariables(Object[] variables) {

        List<Byte> bytesList = new ArrayList<>();
        List<Short> shortsList = new ArrayList<>();
        List<Integer> intsList = new ArrayList<>();
        List<Long> longsList = new ArrayList<>();
        List<Float> floatsList = new ArrayList<>();
        List<Double> doublesList = new ArrayList<>();
        List<Boolean> booleansList = new ArrayList<>();
        List<Character> charsList = new ArrayList<>();

        List<String> stringsList = new ArrayList<>();
        List<ItemStack> itemStacksList = new ArrayList<>();
        List<NBTTagCompound> nbtList = new ArrayList<>();

        for (Object variable : variables) {

            if (variable instanceof Byte) {
                bytesList.add((Byte) variable);

            } else if (variable instanceof Short) {
                shortsList.add((Short) variable);

            } else if (variable instanceof Integer) {
                intsList.add((Integer) variable);

            } else if (variable instanceof Long) {
                longsList.add((Long) variable);

            } else if (variable instanceof Float) {
                floatsList.add((Float) variable);

            } else if (variable instanceof Double) {
                doublesList.add((Double) variable);

            } else if (variable instanceof Boolean) {
                booleansList.add((Boolean) variable);

            } else if (variable instanceof Character) {
                charsList.add((Character) variable);

            } else if (variable instanceof String) {
                stringsList.add((String) variable);

            } else if (variable instanceof ItemStack) {
                itemStacksList.add((ItemStack) variable);

            } else if (variable instanceof NBTTagCompound) {
                nbtList.add((NBTTagCompound) variable);

            } else {
                throw new IllegalArgumentException(
                        "Unsupported packet variable: " +
                                variable.getClass().getName()
                );
            }
        }

        bytes = toByteArray(bytesList);
        shorts = toShortArray(shortsList);
        ints = toIntArray(intsList);
        longs = toLongArray(longsList);
        floats = toFloatArray(floatsList);
        doubles = toDoubleArray(doublesList);
        booleans = toBooleanArray(booleansList);
        chars = toCharArray(charsList);

        strings = stringsList.toArray(new String[0]);
        itemStacks = itemStacksList.toArray(new ItemStack[0]);
        nbtTags = nbtList.toArray(new NBTTagCompound[0]);
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        PacketBuffer buffer = new PacketBuffer(buf);

        action = EnumFunctions.values()[buffer.readInt()];

        bytes = new byte[buffer.readInt()];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = buffer.readByte();

        shorts = new short[buffer.readInt()];
        for (int i = 0; i < shorts.length; i++)
            shorts[i] = buffer.readShort();

        ints = new int[buffer.readInt()];
        for (int i = 0; i < ints.length; i++)
            ints[i] = buffer.readInt();

        longs = new long[buffer.readInt()];
        for (int i = 0; i < longs.length; i++)
            longs[i] = buffer.readLong();

        floats = new float[buffer.readInt()];
        for (int i = 0; i < floats.length; i++)
            floats[i] = buffer.readFloat();

        doubles = new double[buffer.readInt()];
        for (int i = 0; i < doubles.length; i++)
            doubles[i] = buffer.readDouble();

        booleans = new boolean[buffer.readInt()];
        for (int i = 0; i < booleans.length; i++)
            booleans[i] = buffer.readBoolean();

        chars = new char[buffer.readInt()];
        for (int i = 0; i < chars.length; i++)
            chars[i] = buffer.readChar();

        strings = new String[buffer.readInt()];
        for (int i = 0; i < strings.length; i++)
            strings[i] = buffer.readString(32767);

        try {
            itemStacks = new ItemStack[buffer.readInt()];
            for (int i = 0; i < itemStacks.length; i++)
                itemStacks[i] = buffer.readItemStack();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            nbtTags = new NBTTagCompound[buffer.readInt()];
            for (int i = 0; i < nbtTags.length; i++)
                nbtTags[i] = buffer.readCompoundTag();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void toBytes(ByteBuf buf) {

        PacketBuffer buffer = new PacketBuffer(buf);

        buffer.writeInt(action.ordinal());

        buffer.writeInt(bytes.length);
        for (byte value : bytes)
            buffer.writeByte(value);

        buffer.writeInt(shorts.length);
        for (short value : shorts)
            buffer.writeShort(value);

        buffer.writeInt(ints.length);
        for (int value : ints)
            buffer.writeInt(value);

        buffer.writeInt(longs.length);
        for (long value : longs)
            buffer.writeLong(value);

        buffer.writeInt(floats.length);
        for (float value : floats)
            buffer.writeFloat(value);

        buffer.writeInt(doubles.length);
        for (double value : doubles)
            buffer.writeDouble(value);

        buffer.writeInt(booleans.length);
        for (boolean value : booleans)
            buffer.writeBoolean(value);

        buffer.writeInt(chars.length);
        for (char value : chars)
            buffer.writeChar(value);

        buffer.writeInt(strings.length);
        for (String value : strings)
            buffer.writeString(value);

        buffer.writeInt(itemStacks.length);
        for (ItemStack value : itemStacks)
            buffer.writeItemStack(value);

        buffer.writeInt(nbtTags.length);
        for (NBTTagCompound value : nbtTags)
            buffer.writeCompoundTag(value);
    }

    public static class Handler implements IMessageHandler<PacketUniformClient, IMessage> {

        @Override
        public IMessage onMessage(PacketUniformClient message, MessageContext ctx) {

            if (message.action != null) {
                message.action.execute(ctx, message.buildArguments());
            }

            return null;
        }
    }

    private Object[] buildArguments() {

        List<Object> result = new ArrayList<>();

        for (byte value : bytes) result.add(value);
        for (short value : shorts) result.add(value);
        for (int value : ints) result.add(value);
        for (long value : longs) result.add(value);
        for (float value : floats) result.add(value);
        for (double value : doubles) result.add(value);
        for (boolean value : booleans) result.add(value);
        for (char value : chars) result.add(value);

        result.addAll(Arrays.asList(strings));
        result.addAll(Arrays.asList(itemStacks));
        result.addAll(Arrays.asList(nbtTags));

        return result.toArray();
    }

    private static byte[] toByteArray(List<Byte> list) {
        byte[] result = new byte[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }

    private static short[] toShortArray(List<Short> list) {
        short[] result = new short[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }

    private static int[] toIntArray(List<Integer> list) {
        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }

    private static long[] toLongArray(List<Long> list) {
        long[] result = new long[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }

    private static float[] toFloatArray(List<Float> list) {
        float[] result = new float[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }

    private static double[] toDoubleArray(List<Double> list) {
        double[] result = new double[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }

    private static boolean[] toBooleanArray(List<Boolean> list) {
        boolean[] result = new boolean[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }

    private static char[] toCharArray(List<Character> list) {
        char[] result = new char[list.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = list.get(i);
        return result;
    }
}
