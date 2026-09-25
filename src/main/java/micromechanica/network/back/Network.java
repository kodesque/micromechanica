package micromechanica.network.back;

import micromechanica.network.back.packets.PacketUniformClient;
import micromechanica.network.back.packets.PacketUniformServer;
import micromechanica.root.Main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class Network {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
    private static int packetId = 0;

    public static void registerPackets() {

        INSTANCE.registerMessage(
                PacketUniformClient.Handler.class,
                PacketUniformClient.class,
                packetId++,
                Side.CLIENT
        );

        INSTANCE.registerMessage(
                PacketUniformServer.Handler.class,
                PacketUniformServer.class,
                packetId++,
                Side.SERVER
        );

    }

    public static void sendPacket(IMessage msg, @Nullable EntityPlayerMP player) {
        if (msg instanceof PacketUniformClient) {
            sendToPlayerClient(msg, player);
        } else if (msg instanceof PacketUniformServer) {
            sendToPlayerServer(msg);
        }
    }

    private static void sendToPlayerClient(IMessage msg, EntityPlayerMP player) {
        INSTANCE.sendTo(msg, player);
    }

    private static void sendToPlayerServer(IMessage msg) {
        INSTANCE.sendToServer(msg);
    }

}
