package micromechanica.network.packets;

import io.netty.buffer.ByteBuf;
import micromechanica.network.EnumFunctions;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;
import java.util.function.Consumer;

public class PacketUniformServer implements IMessage {

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

    public PacketUniformServer() {
    }

    public PacketUniformServer(EnumFunctions function, Object... variables) {
        this.action = function;

        PacketData data = PacketData.collect(variables);

        this.bytes = data.bytes;
        this.shorts = data.shorts;
        this.ints = data.ints;
        this.longs = data.longs;
        this.floats = data.floats;
        this.doubles = data.doubles;
        this.booleans = data.booleans;
        this.chars = data.chars;
        this.strings = data.strings;
        this.itemStacks = data.itemStacks;
        this.nbtTags = data.nbtTags;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        PacketBuffer buffer = new PacketBuffer(buf);

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

        buffer.writeInt(bytes.length);
        for (byte value : bytes) buffer.writeByte(value);

        buffer.writeInt(shorts.length);
        for (short value : shorts) buffer.writeShort(value);

        buffer.writeInt(ints.length);
        for (int value : ints) buffer.writeInt(value);

        buffer.writeInt(longs.length);
        for (long value : longs) buffer.writeLong(value);

        buffer.writeInt(floats.length);
        for (float value : floats) buffer.writeFloat(value);

        buffer.writeInt(doubles.length);
        for (double value : doubles) buffer.writeDouble(value);

        buffer.writeInt(booleans.length);
        for (boolean value : booleans) buffer.writeBoolean(value);

        buffer.writeInt(chars.length);
        for (char value : chars) buffer.writeChar(value);

        buffer.writeInt(strings.length);
        for (String value : strings) buffer.writeString(value);

        buffer.writeInt(itemStacks.length);
        for (ItemStack value : itemStacks) buffer.writeItemStack(value);

        buffer.writeInt(nbtTags.length);
        for (NBTTagCompound value : nbtTags)
            buffer.writeCompoundTag(value);
    }

    public static class Handler
            implements IMessageHandler<PacketUniformServer, IMessage> {

        @Override
        public IMessage onMessage(PacketUniformServer message, MessageContext ctx) {

            if (message.action != null) {
                message.action.execute(ctx, message.buildArguments());
            }

            return null;
        }
    }

    private Object[] buildArguments() {

        Object[] result = new Object[
                bytes.length +
                        shorts.length +
                        ints.length +
                        longs.length +
                        floats.length +
                        doubles.length +
                        booleans.length +
                        chars.length +
                        strings.length +
                        itemStacks.length +
                        nbtTags.length
                ];

        int index = 0;

        for (byte value : bytes) result[index++] = value;
        for (short value : shorts) result[index++] = value;
        for (int value : ints) result[index++] = value;
        for (long value : longs) result[index++] = value;
        for (float value : floats) result[index++] = value;
        for (double value : doubles) result[index++] = value;
        for (boolean value : booleans) result[index++] = value;
        for (char value : chars) result[index++] = value;

        for (String value : strings) result[index++] = value;
        for (ItemStack value : itemStacks) result[index++] = value;
        for (NBTTagCompound value : nbtTags) result[index++] = value;

        return result;
    }

    private static class PacketData {

        byte[] bytes;
        short[] shorts;
        int[] ints;
        long[] longs;
        float[] floats;
        double[] doubles;
        boolean[] booleans;
        char[] chars;

        String[] strings;
        ItemStack[] itemStacks;
        NBTTagCompound[] nbtTags;

        static PacketData collect(Object[] variables) {

            PacketData data = new PacketData();

            java.util.List<Byte> b = new java.util.ArrayList<>();
            java.util.List<Short> s = new java.util.ArrayList<>();
            java.util.List<Integer> i = new java.util.ArrayList<>();
            java.util.List<Long> l = new java.util.ArrayList<>();
            java.util.List<Float> f = new java.util.ArrayList<>();
            java.util.List<Double> d = new java.util.ArrayList<>();
            java.util.List<Boolean> bo = new java.util.ArrayList<>();
            java.util.List<Character> c = new java.util.ArrayList<>();

            java.util.List<String> str = new java.util.ArrayList<>();
            java.util.List<ItemStack> stacks = new java.util.ArrayList<>();
            java.util.List<NBTTagCompound> nbt = new java.util.ArrayList<>();

            for (Object value : variables) {
                if (value instanceof Byte) b.add((Byte) value);
                else if (value instanceof Short) s.add((Short) value);
                else if (value instanceof Integer) i.add((Integer) value);
                else if (value instanceof Long) l.add((Long) value);
                else if (value instanceof Float) f.add((Float) value);
                else if (value instanceof Double) d.add((Double) value);
                else if (value instanceof Boolean) bo.add((Boolean) value);
                else if (value instanceof Character) c.add((Character) value);
                else if (value instanceof String) str.add((String) value);
                else if (value instanceof ItemStack) stacks.add((ItemStack) value);
                else if (value instanceof NBTTagCompound) nbt.add((NBTTagCompound) value);
                else throw new IllegalArgumentException(
                            "Unsupported type: " + value.getClass()
                    );
            }

            data.bytes = new byte[b.size()];
            for (int x = 0; x < b.size(); x++) data.bytes[x] = b.get(x);

            data.shorts = new short[s.size()];
            for (int x = 0; x < s.size(); x++) data.shorts[x] = s.get(x);

            data.ints = new int[i.size()];
            for (int x = 0; x < i.size(); x++) data.ints[x] = i.get(x);

            data.longs = new long[l.size()];
            for (int x = 0; x < l.size(); x++) data.longs[x] = l.get(x);

            data.floats = new float[f.size()];
            for (int x = 0; x < f.size(); x++) data.floats[x] = f.get(x);

            data.doubles = new double[d.size()];
            for (int x = 0; x < d.size(); x++) data.doubles[x] = d.get(x);

            data.booleans = new boolean[bo.size()];
            for (int x = 0; x < bo.size(); x++) data.booleans[x] = bo.get(x);

            data.chars = new char[c.size()];
            for (int x = 0; x < c.size(); x++) data.chars[x] = c.get(x);

            data.strings = str.toArray(new String[0]);
            data.itemStacks = stacks.toArray(new ItemStack[0]);
            data.nbtTags = nbt.toArray(new NBTTagCompound[0]);

            return data;
        }
    }
}
