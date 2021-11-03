package nl.tettelaar.rebalanced.mixin.village;


import java.util.List;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public class GolemProtectChestMixin {

	double distance = 1000;

	@Inject(method = "startOpen", at = @At("HEAD"), cancellable = true)
	public void startOpen(Player player, CallbackInfo ci) {
		if (!player.isSpectator()) {


			double x = player.getX();
			double y = player.getY();
			double z = player.getZ();

			AABB box = new AABB(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance);
			List<IronGolem> IronGolems = player.getCommandSenderWorld().getEntitiesOfClass(IronGolem.class, box,
					EntitySelector.LIVING_ENTITY_STILL_ALIVE);

			for (IronGolem irongolem : IronGolems) {
				if (!irongolem.isPlayerCreated()) {
					irongolem.setPersistentAngerTarget(player.getUUID());
					irongolem.setRemainingPersistentAngerTime(30);
				}
			}
		}

	}
}
