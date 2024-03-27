package googy.betterwithenchanting.mixin;

import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Packet.class, remap = false)
public interface PacketMixin
{
	@Invoker("addIdClassMapping")
	static void callAddIdClassMapping(int id, boolean clientPacket, boolean serverPacket, Class<? extends Packet> packetClass) {
		throw new UnsupportedOperationException();
	}
}
