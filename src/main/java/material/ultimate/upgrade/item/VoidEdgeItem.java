package material.ultimate.upgrade.item;

import material.ultimate.upgrade.ducks.PlayerEntityDuck;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class VoidEdgeItem extends Item {
    public VoidEdgeItem(Settings settings) {
        super(settings);
    }

    public static void onHit(PlayerEntity playerEntity, World world, Hand hand, Entity entity, EntityHitResult entityHitResult) {
        ItemStack stack = playerEntity.getStackInHand(hand);
        if (stack.getItem() instanceof VoidEdgeItem && entity instanceof LivingEntity) {
            int fireAspect = EnchantmentHelper.getFireAspect(playerEntity);
            List<LivingEntity> livingEntities = world.getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), playerEntity.getBoundingBox().expand(5, 2, 5), livingEntity -> livingEntity.isAlive() && livingEntity.distanceTo(playerEntity) < 5 && !livingEntity.isTeammate(playerEntity));
            for (LivingEntity livingEntity : livingEntities){
                livingEntity.setOnFireFor(fireAspect * (fireAspect + 1));
                livingEntity.damage(DamageSource.player(playerEntity), (float) Objects.requireNonNull(playerEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getValue());
                Vec3d delta = playerEntity.getPos().relativize(livingEntity.getPos());
                double distance = livingEntity.distanceTo(playerEntity);
                livingEntity.takeKnockback(1 + EnchantmentHelper.getKnockback(playerEntity) * 0.5, -delta.x/distance, -delta.z/distance);
            }
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient) {
            float f = -MathHelper.sin(user.getYaw() * ((float) Math.PI / 180)) * MathHelper.cos(user.getPitch() * ((float) Math.PI / 180));
            float g = -MathHelper.sin((user.getPitch()) * ((float) Math.PI / 180));
            float h = MathHelper.cos(user.getYaw() * ((float) Math.PI / 180)) * MathHelper.cos(user.getPitch() * ((float) Math.PI / 180));
            Vec3d pos = user.getPos();
            pos.add(new Vec3d(f, g, h).normalize().multiply(8));
            user.teleport(pos.x, pos.y, pos.z);
            if (user instanceof PlayerEntity player) {
                ((PlayerEntityDuck) player).dash(5);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
