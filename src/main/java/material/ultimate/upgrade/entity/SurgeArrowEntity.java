package material.ultimate.upgrade.entity;

import material.ultimate.upgrade.registry.UltimateEntityTypes;
import material.ultimate.upgrade.registry.UltimateItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.List;

public class SurgeArrowEntity extends PersistentProjectileEntity {

    public SurgeArrowEntity(EntityType<? extends SurgeArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public SurgeArrowEntity(LivingEntity owner, World world) {
        super(UltimateEntityTypes.SURGE_ARROW, owner, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.inGround){
            zap();
        }
    }

    public void zap(){
        if (!world.isClient) {
            Entity owner = this.getOwner() == null ? this : this.getOwner();
            List<Entity> entities = world.getOtherEntities(owner, this.getBoundingBox().expand(5), entity -> entity instanceof LivingEntity && !entity.isTeammate(owner));
            if (entities.size() > 0) {
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity livingEntity && random.nextBoolean()) {
                        livingEntity.damage(DamageSource.LIGHTNING_BOLT, (float) (this.getDamage() * 5.0F));
                    }
                }
            } else {
                ((ServerWorld) world).spawnParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 30, 5, 5, 5, 0.2);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurtTime = 0;
            livingEntity.damage(DamageSource.LIGHTNING_BOLT, (float) (this.getDamage() * this.getVelocity().length()));
        }
        zap();
    }

    @Override
    protected ItemStack asItemStack() {
        return UltimateItems.SURGE_ARROW.getDefaultStack();
    }
}
