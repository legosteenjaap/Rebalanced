package nl.tettelaar.rebalanced.mixin.sleep;

import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.critereon.LocationTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

	@Redirect(method = "method_19504", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;awardStat(Lnet/minecraft/resources/ResourceLocation;)V"), remap = false, require = 0)
	private void stopAwardStatProd(ServerPlayer player, ResourceLocation stat) {
	}

	@Redirect(method = "method_19504", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/LocationTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;)V"), remap = false, require = 0)
	private void stopSweetDreamsProd(LocationTrigger locationTrigger, ServerPlayer player) {
	}

	@Redirect(method = "lambda$startSleepInBed$7", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;awardStat(Lnet/minecraft/resources/ResourceLocation;)V"), remap = false, require = 0)
	private void stopAwardStatDev(ServerPlayer player, ResourceLocation stat) {
	}

	@Redirect(method = "lambda$startSleepInBed$7", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/LocationTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;)V"), remap = false, require = 0)
	private void stopSweetDreamsDev(LocationTrigger locationTrigger, ServerPlayer player) {
	}

}