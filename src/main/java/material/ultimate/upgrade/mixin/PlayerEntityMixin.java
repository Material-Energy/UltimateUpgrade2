package material.ultimate.upgrade.mixin;

import material.ultimate.upgrade.ducks.PlayerEntityDuck;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityDuck {
    @Shadow protected abstract void dropShoulderEntities();

    @Shadow protected abstract void damageShield(float amount);

    public int dashTicks = 0;
    public Vec3d dashVelocity;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void dash(int ticks){
        dashTicks = ticks;
        dashVelocity = this.getVelocity();
        if (!this.world.isClient) {
            this.dropShoulderEntities();
        }
    }

    @Override
    public boolean hasNoGravity() {
        if (dashTicks <= 0) {
            return super.hasNoGravity();
        } else {
            return true;
        }
    }

    @Inject(
            method = "isInvulnerableTo",
            at = @At("HEAD"),
            cancellable = true
    )
    public void dashImmunity(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (dashTicks >= 0) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void removeDash(CallbackInfo ci){
        dashTicks--;
        if (dashTicks > 0){
            this.setVelocity(dashVelocity);
        }
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        if (dashTicks > 0 && !state.isAir()) {
            dashTicks = 0;
            this.setVelocity(0, 0, 0);
            velocityModified = true;
        }
    }
}
