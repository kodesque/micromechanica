package micromechanica.network;

import micromechanica.network.packets.PacketUniformClient;
import micromechanica.network.packets.PacketUniformServer;
import micromechanica.root.Main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

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

    public static void sendToPlayerClient(IMessage msg, EntityPlayerMP player) {
        INSTANCE.sendTo(msg, player);
    }

    public static void sendToPlayerServer(IMessage msg) {
        INSTANCE.sendToServer(msg);
    }

}
