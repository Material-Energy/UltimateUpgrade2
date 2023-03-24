package material.ultimate.upgrade.item;

import material.ultimate.upgrade.registry.UltimateParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class VoidArmorItem extends ArmorItem {
    public VoidArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public static boolean checkArmor(Entity entity){
        for (ItemStack itemStack : entity.getArmorItems()) {
            if (!(itemStack.getItem() instanceof VoidArmorItem)) return false;
        }
        return true;
    }

    public static void onHit(PlayerEntity playerEntity, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if (checkArmor(playerEntity)){
            DamageSource damageSource = DamageSource.player(playerEntity);
            List<Entity> entities = world.getOtherEntities(playerEntity, playerEntity.getBoundingBox().expand(14));
            for (Entity entity1 : entities){
                if (entity1 instanceof LivingEntity living){
                    living.damage(damageSource, (float) (Math.max(2, playerEntity.getMaxHealth() - playerEntity.getHealth()) + Objects.requireNonNull(playerEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getValue()));
                }
            }
            Vec3d pos = playerEntity.getPos();
            if (!world.isClient) {
                for (int i = 0; i <= 50; i++) {
                    ((ServerWorld) world).spawnParticles((ServerPlayerEntity) playerEntity, UltimateParticles.VOID, true, pos.x, pos.y + 1, pos.z, 50, 4, 4, 4, 0);
                }
            }
        }
    }
}

