package material.ultimate.upgrade.entity.renderer;

import material.ultimate.upgrade.entity.SurgeArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

import static material.ultimate.upgrade.UltimateUpgrade.id;

public class SurgeArrowEntityRenderer extends ProjectileEntityRenderer<SurgeArrowEntity> {
    public static final Identifier TEXTURE = id("textures/entity/projectiles/surge_arrow.png");

    public static final Identifier TEXTURE_DEBUG = new Identifier("textures/entity/projectiles/spectral_arrow.png");
    public SurgeArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SurgeArrowEntity entity) {
        return TEXTURE;
    }
}
