package nl.tettelaar.rebalanced.mixin.ai.animal;

import java.util.Random;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Rabbit.class)
public abstract class MoreRabbitMixin extends Animal{

	Random random = new Random();
	
	protected MoreRabbitMixin(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void spawnChildFromBreeding(ServerLevel serverLevel, Animal other) {
		int amount = random.nextInt(2) + 3;
		for (int i = 0; i < amount; i++) {
			AgeableMob ageableMob = this.getBreedOffspring(serverLevel, other);
			if (ageableMob != null) {
		         this.setAge(6000);
		         other.setAge(6000);
		         this.resetLove();
		         other.resetLove();
		         ageableMob.setBaby(true);
		         ageableMob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
		         serverLevel.addFreshEntityWithPassengers(ageableMob);
		         serverLevel.broadcastEntityEvent(this, (byte)18);


		      }
		}
		super.spawnChildFromBreeding(serverLevel, other);
	}
	
	
}
