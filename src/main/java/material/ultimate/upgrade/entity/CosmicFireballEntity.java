package material.ultimate.upgrade.entity;

import material.ultimate.upgrade.registry.UltimateEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;

public class CosmicFireballEntity extends AbstractFireballEntity {
    public int power;
    public int age = 500;

    public CosmicFireballEntity(EntityType<? extends CosmicFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public CosmicFireballEntity(World world, LivingEntity livingEntity, int velocityX, int velocityY, int velocityZ, int power) {
        super(UltimateEntityTypes.COSMIC_FIREBALL, livingEntity, velocityX, velocityY, velocityZ, world);
        this.power = power;
    }

    @Override
    public void tick() {
        super.tick();
        this.age--;
        ArrayList<Entity> entityList = (ArrayList<Entity>) world.getOtherEntities(this.getOwner(), this.getBoundingBox().expand(10), entity -> this.getOwner() != null ? entity.isAlive() && !this.getOwner().isTeammate(entity) : entity.isAlive());
        for (Entity entity: entityList){
            if (entity instanceof LivingEntity livingEntity){
                Vec3d difference = this.getPos().subtract(livingEntity.getPos()).normalize().multiply(0.3);
                livingEntity.addVelocity(difference.x, difference.y, difference.z);
            }
        }
        if (this.age <= 0){
            createExplosion();
        }
    }
    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        if (state.getCollisionShape(world, this.getBlockPos()) != VoxelShapes.empty()) createExplosion();
    }

    protected void createExplosion(){
        if (!this.world.isClient) {
            boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            this.world.createExplosion(this.getOwner(), this.getX(), this.getY(), this.getZ(), (float)this.power, false, bl ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
            this.discard();
        }
    }

    @Override
    protected float getDrag() {
        return 1.0F;
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    public void setLifetime(double v) {
        age = (int) v;
    }
}
