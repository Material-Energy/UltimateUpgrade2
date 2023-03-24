package material.ultimate.upgrade.item;

import material.ultimate.upgrade.entity.SurgeArrowEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SurgeArrowItem extends ArrowItem {
    public SurgeArrowItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new SurgeArrowEntity(shooter, world);
    }
}
