package nl.tettelaar.rebalanced.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

@Mixin(RabbitEntity.class)
public abstract class MoreRabbitMixin extends AnimalEntity{

	Random random = new Random();
	
	protected MoreRabbitMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void breed(ServerWorld serverWorld, AnimalEntity other) {
		int amount = random.nextInt(2) + 3;
		for (int i = 0; i < amount; i++) {
			PassiveEntity passiveEntity = this.createChild(serverWorld, other);
			if (passiveEntity != null) {
		         this.setBreedingAge(6000);
		         other.setBreedingAge(6000);
		         this.resetLoveTicks();
		         other.resetLoveTicks();
		         passiveEntity.setBaby(true);
		         passiveEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
		         serverWorld.spawnEntityAndPassengers(passiveEntity);
		         serverWorld.sendEntityStatus(this, (byte)18);


		      }
		}
		super.breed(serverWorld, other);
	}
	
	
}
