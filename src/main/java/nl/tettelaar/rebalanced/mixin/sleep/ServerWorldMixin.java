package nl.tettelaar.rebalanced.mixin.sleep;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {

	@Shadow
	@Final
	private List<ServerPlayer> players;

	@Inject(method = "wakeUpAllPlayers", at = @At("HEAD"))
	public void wakeUpAllPlayers(CallbackInfo ci) {
		((List<ServerPlayer>) this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList()))
				.forEach((player) -> {
					player.setExperienceLevels(player.experienceLevel - 10);
					player.connection.send(new ClientboundCustomSoundPacket(SoundEvents.PLAYER_LEVELUP.getLocation(), SoundSource.BLOCKS, player.position(), 1f, 1f));
					player.playNotifySound(SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1f, 1f);
		            player.awardStat(Stats.SLEEP_IN_BED);
		            CriteriaTriggers.SLEPT_IN_BED.trigger(player);
				});
	}
}
