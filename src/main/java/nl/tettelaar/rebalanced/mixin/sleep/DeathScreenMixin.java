package nl.tettelaar.rebalanced.mixin.sleep;

import java.util.List;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import nl.tettelaar.rebalanced.network.NetworkingServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import nl.tettelaar.rebalanced.network.NetworkingClient;
import nl.tettelaar.rebalanced.screens.ConfirmRespawnNightScreen;
import nl.tettelaar.rebalanced.util.TimeUtil;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {

	@Shadow
	@Final
	private boolean hardcore;
	@Shadow
	@Final
	private Component causeOfDeath;

	@Unique private boolean checkIfHasSpawn = false;
	
	protected DeathScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Redirect(method = "init()V", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
	private boolean replaceRespawnButton(List<Button> buttons, Object widget) {
		buttons.remove(widget);
		this.removeWidget((Button) widget);
		return buttons.add((Button) this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 72, 200, 20, this.hardcore ? new TranslatableComponent("deathScreen.spectate") : new TranslatableComponent("deathScreen.respawn"), (button) -> {
			if (TimeUtil.isIdealTimeToRespawn(this.minecraft.level)) {
				respawn();
			} else {
				checkRespawn();
				
			}
		})));
	}

	private void onConfirm(Boolean respawnNow) {
		if (respawnNow) {
			respawn();
		} else {
			DeathScreen deathScreen = new DeathScreen(this.causeOfDeath, this.hardcore);
			this.minecraft.setScreen(deathScreen);
		}
	}

	public void checkRespawn() {
		FriendlyByteBuf buf = PacketByteBufs.empty();
		ClientPlayNetworking.send(NetworkingClient.PLAYER_HAS_SPAWNPOINT_ID, buf);
		checkIfHasSpawn = true;
	}
	
	private void respawn() {
		this.minecraft.player.respawn();
		this.minecraft.setScreen((Screen) null);
	}
	
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void tick(CallbackInfo ci) {
		if (checkIfHasSpawn && NetworkingClient.hasSpawnPoint != null) {
			checkIfHasSpawn = false;
			if (!NetworkingClient.hasSpawnPoint) {
				ConfirmRespawnNightScreen confirmRespawnNightScreen = new ConfirmRespawnNightScreen(this::onConfirm);
				this.minecraft.setScreen(confirmRespawnNightScreen);
			} else {
				respawn();
			}
			NetworkingClient.hasSpawnPoint = null;
		}
	}

}
