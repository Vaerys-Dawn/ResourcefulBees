package com.resourcefulbees.resourcefulbees.network.packets;

import com.resourcefulbees.resourcefulbees.tileentity.CentrifugeTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DrainCentrifugeTankMessage {
    private final BlockPos pos;
    private final int tank;

    public DrainCentrifugeTankMessage(BlockPos pos, int tank){
        this.pos = pos;
        this.tank = tank;
    }

    public static void encode(DrainCentrifugeTankMessage message, PacketBuffer buffer){
        buffer.writeBlockPos(message.pos);
        buffer.writeInt(message.tank);
    }

    public static DrainCentrifugeTankMessage decode(PacketBuffer buffer){
        return new DrainCentrifugeTankMessage(buffer.readBlockPos(), buffer.readInt());
    }

    public static void handle(DrainCentrifugeTankMessage message, Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                if (player.world.isBlockPresent(message.pos)) {
                    TileEntity tileEntity = player.world.getTileEntity(message.pos);
                    if (tileEntity instanceof CentrifugeTileEntity) {
                        ((CentrifugeTileEntity) tileEntity).drainFluidInTank(message.tank);
                    }
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
