package nl.tettelaar.rebalanced.screens;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.RebalancedClient;
import nl.tettelaar.rebalanced.util.TimeUtil;

@Environment(EnvType.CLIENT)
public class ConfirmRespawnNightScreen extends Screen {
	public ConfirmRespawnNightScreen(BooleanConsumer confirmRespawn) {
		super(new TranslatableText("rebalanced.respawn.night"));
		this.time = MultilineText.EMPTY;
		this.confirmRespawn = confirmRespawn;
		this.buttons = Lists.newArrayList();
	}

	private MultilineText time;
	private int buttonEnableTimer;
	protected final BooleanConsumer confirmRespawn;
	private final List<ButtonWidget> buttons;

	public Text getNarratedTitle() {
		return ScreenTexts.joinSentences(super.getNarratedTitle(), this.title);
	}

	protected void init() {
		super.init();

		this.buttons.clear();
		this.addButtons(this.height / 2);
	}

	protected void addButtons(int y) {
		this.addButton(new ButtonWidget(this.width / 2 - 155, y, 150, 20, new TranslatableText("rebalanced.respawn.now"), (button) -> {
			this.confirmRespawn.accept(true);
		}));
		this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, y, 150, 20, new TranslatableText("rebalanced.respawn.quit"), (button) -> {
			this.confirmRespawn.accept(false);
		}));
	}

	protected void addButton(ButtonWidget button) {
		this.buttons.add((ButtonWidget) this.addDrawableChild(button));
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 70, 16777215);
		this.time = MultilineText.create(this.textRenderer, Text.of(Long.toString(((RebalancedClient.earliestRespawnTime - this.client.world.getTimeOfDay())) / 20 * Rebalanced.timeMultiplier)), this.width - 50);
		this.time.drawCenterWithShadow(matrices, this.width / 2, 90);
		super.render(matrices, mouseX, mouseY, delta);
	}

	public void disableButtons(int ticks) {
		this.buttonEnableTimer = ticks;

		ButtonWidget buttonWidget;
		for (Iterator<ButtonWidget> var2 = this.buttons.iterator(); var2.hasNext(); buttonWidget.active = false) {
			buttonWidget = (ButtonWidget) var2.next();
		}

	}

	public void tick() {
		super.tick();
		ButtonWidget buttonWidget;
		
		if (TimeUtil.isIdealTimeToRespawn(this.client.world)) {
			this.confirmRespawn.accept(true);
		}
		if (--this.buttonEnableTimer == 0) {
			for (Iterator<ButtonWidget> var1 = this.buttons.iterator(); var1.hasNext(); buttonWidget.active = true) {
				buttonWidget = (ButtonWidget) var1.next();
			}
		}

	}

	public boolean shouldCloseOnEsc() {
		return false;
	}

	public boolean isPauseScreen() {
		return false;
	}

	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			this.confirmRespawn.accept(false);
			return true;
		} else {
			return super.keyPressed(keyCode, scanCode, modifiers);
		}
	}
}
