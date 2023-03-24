package material.ultimate.upgrade.registry;

import material.ultimate.upgrade.entity.CosmicFireballEntity;
import material.ultimate.upgrade.entity.SurgeArrowEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.registry.Registry;

import static material.ultimate.upgrade.UltimateUpgrade.id;

public class UltimateEntityTypes {
    public static EntityType<SurgeArrowEntity> SURGE_ARROW;
    public static EntityType<CosmicFireballEntity> COSMIC_FIREBALL;

    public static void init(){
        COSMIC_FIREBALL = Registry.register(Registry.ENTITY_TYPE, id("cosmic_fireball"),
                FabricEntityTypeBuilder.<CosmicFireballEntity>create(SpawnGroup.MISC, CosmicFireballEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                        .build()
        );
        SURGE_ARROW = Registry.register(Registry.ENTITY_TYPE, id("surge_arrow"),
                FabricEntityTypeBuilder.<SurgeArrowEntity>create(SpawnGroup.MISC, SurgeArrowEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                        .build()
        );
    }
}
