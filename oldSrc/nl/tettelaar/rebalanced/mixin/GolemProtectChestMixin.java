package nl.tettelaar.rebalanced.mixin;


import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;

@Mixin(ChestBlockEntity.class)
public class GolemProtectChestMixin {

	double distance = 1000;

	@Inject(method = "onOpen", at = @At("HEAD"), cancellable = true)
	public void onOpen(PlayerEntity player, CallbackInfo ci) {
		if (!player.isSpectator()) {


			double x = player.getX();
			double y = player.getY();
			double z = player.getZ();

			Box box = new Box(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance);
			List<IronGolemEntity> IronGolems = player.getEntityWorld().getEntitiesByClass(IronGolemEntity.class, box,
					EntityPredicates.VALID_LIVING_ENTITY);

			for (IronGolemEntity irongolem : IronGolems) {
				if (!irongolem.isPlayerCreated()) {
					irongolem.setAngryAt(player.getUuid());
					irongolem.setAngerTime(30);
				}
			}
		}

	}
}
