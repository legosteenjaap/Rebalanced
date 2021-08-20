package nl.tettelaar.rebalanced.mixin.sleep;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.tettelaar.rebalanced.screens.ConfirmRespawnNightScreen;
import nl.tettelaar.rebalanced.util.TimeUtil;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {

	@Shadow	@Final private boolean isHardcore;
	@Shadow @Final private Text message;

	protected DeathScreenMixin(Text title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Redirect(method = "init()V", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
	private boolean replaceRespawnButton(List<ButtonWidget> buttons, Object widget) {
		buttons.remove(widget);
		this.remove((ButtonWidget) widget);
		return buttons.add((ButtonWidget) this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 72, 200, 20, this.isHardcore ? new TranslatableText("deathScreen.spectate") : new TranslatableText("deathScreen.respawn"), (button) -> {
			if (TimeUtil.isIdealTimeToRespawn(this.client.world)) {
				respawn();
			} else {
				ConfirmRespawnNightScreen confirmRespawnNightScreen = new ConfirmRespawnNightScreen(this::onConfirm);
				this.client.openScreen(confirmRespawnNightScreen);
			}
		})));
	}

	private void onConfirm(Boolean respawnNow) {
		if (respawnNow) {
			respawn();
		} else {
			DeathScreen deathScreen = new DeathScreen(this.message, this.isHardcore);
			this.client.openScreen(deathScreen);
		}
	}

	private void respawn() {
		this.client.player.requestRespawn();
		this.client.openScreen((Screen) null);
	}

}
