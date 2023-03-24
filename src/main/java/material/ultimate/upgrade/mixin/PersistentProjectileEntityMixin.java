package material.ultimate.upgrade.mixin;

import material.ultimate.upgrade.registry.UltimateEntityAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@SuppressWarnings("InvalidInjectorMethodSignature")
@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {

    @ModifyVariable(
            method = "onEntityHit",
            at = @At("STORE"),
            ordinal = 0
    )
    public float attribute(float value){
        Entity owner = ((PersistentProjectileEntity) (Object)this).getOwner();
        EntityAttributeInstance bonus;
        if (owner instanceof PlayerEntity player && (bonus = player.getAttributeInstance(UltimateEntityAttributes.BONUS_BOW_DAMAGE)) != null) {
            return (float) (value * bonus.getValue());
        } else{
            return value;
        }
    }
}
