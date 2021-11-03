package nl.tettelaar.rebalanced.screens;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import nl.tettelaar.rebalanced.Rebalanced;
import nl.tettelaar.rebalanced.RebalancedClient;
import nl.tettelaar.rebalanced.util.TimeUtil;

@Environment(EnvType.CLIENT)
public class ConfirmRespawnNightScreen extends Screen {
	public ConfirmRespawnNightScreen(BooleanConsumer confirmRespawn) {
		super(new TranslatableComponent("rebalanced.respawn.night"));
		this.time = MultiLineLabel.EMPTY;
		this.confirmRespawn = confirmRespawn;
		this.buttons = Lists.newArrayList();
	}

	private MultiLineLabel time;
	private int buttonEnableTimer;
	protected final BooleanConsumer confirmRespawn;
	private final List<Button> buttons;

	public Component getNarrationMessage() {
		return CommonComponents.joinForNarration(super.getNarrationMessage(), this.title);
	}

	protected void init() {
		super.init();

		this.buttons.clear();
		this.addButtons(this.height / 2);
	}

	protected void addButtons(int y) {
		this.addButton(new Button(this.width / 2 - 155, y, 150, 20, new TranslatableComponent("rebalanced.respawn.now"), (button) -> {
			this.confirmRespawn.accept(true);
		}));
		this.addButton(new Button(this.width / 2 - 155 + 160, y, 150, 20, new TranslatableComponent("rebalanced.respawn.quit"), (button) -> {
			this.confirmRespawn.accept(false);
		}));
	}

	protected void addButton(Button button) {
		this.buttons.add((Button) this.addRenderableWidget(button));
	}

	public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		drawCenteredString(matrices, this.font, this.title, this.width / 2, 70, 16777215);
		this.time = MultiLineLabel.create(this.font, Component.nullToEmpty(Long.toString(((RebalancedClient.earliestRespawnTime - this.minecraft.level.getDayTime())) / 20 * Rebalanced.timeMultiplier)), this.width - 50);
		this.time.renderCentered(matrices, this.width / 2, 90);
		super.render(matrices, mouseX, mouseY, delta);
	}

	public void disableButtons(int ticks) {
		this.buttonEnableTimer = ticks;

		Button buttonWidget;
		for (Iterator<Button> var2 = this.buttons.iterator(); var2.hasNext(); buttonWidget.active = false) {
			buttonWidget = (Button) var2.next();
		}

	}

	public void tick() {
		super.tick();
		Button buttonWidget;
		
		if (TimeUtil.isIdealTimeToRespawn(this.minecraft.level)) {
			this.confirmRespawn.accept(true);
		}
		if (--this.buttonEnableTimer == 0) {
			for (Iterator<Button> var1 = this.buttons.iterator(); var1.hasNext(); buttonWidget.active = true) {
				buttonWidget = (Button) var1.next();
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
