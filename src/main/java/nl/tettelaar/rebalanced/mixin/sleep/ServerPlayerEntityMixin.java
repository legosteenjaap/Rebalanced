package nl.tettelaar.rebalanced.mixin.sleep;

import java.util.function.Consumer;

import net.minecraft.advancement.criterion.LocationArrivalCriterion;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

	@Redirect(method = "method_19504", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V"))
	private void stopIncrementStat(ServerPlayerEntity player, Identifier stat) {
	}
	
	@Redirect(method = "method_19504", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/LocationArrivalCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;)V"))
	private void stopSweetDreams(LocationArrivalCriterion ocationArrivalCriterion, ServerPlayerEntity player) {
	}

}
