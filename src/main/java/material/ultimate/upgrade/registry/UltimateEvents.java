package material.ultimate.upgrade.registry;

import material.ultimate.upgrade.item.VoidArmorItem;
import material.ultimate.upgrade.item.VoidEdgeItem;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class UltimateEvents {
    public static void init(){
        AttackEntityCallback.EVENT.register(UltimateEvents::onHit);
    }

    public static ActionResult onHit(PlayerEntity playerEntity, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        VoidArmorItem.onHit(playerEntity, world, hand, entity, entityHitResult);
        VoidEdgeItem.onHit(playerEntity, world, hand, entity, entityHitResult);

        return ActionResult.PASS;
    }
}
