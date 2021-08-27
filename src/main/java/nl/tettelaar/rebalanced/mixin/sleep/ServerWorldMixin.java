package nl.tettelaar.rebalanced.mixin.sleep;

import java.util.List;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

	@Shadow
	@Final
	private List<ServerPlayerEntity> players;

	@Inject(method = "wakeSleepingPlayers", at = @At("HEAD"))
	public void wakeSleepingPlayers(CallbackInfo ci) {
		((List<ServerPlayerEntity>) this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList()))
				.forEach((player) -> {
					player.setExperienceLevel(player.experienceLevel - 10);
					player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(SoundEvents.ENTITY_PLAYER_LEVELUP.getId(), SoundCategory.BLOCKS, player.getPos(), 1f, 1f));
					player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1f, 1f);
				});
	}
}
