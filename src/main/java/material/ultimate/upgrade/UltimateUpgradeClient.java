package material.ultimate.upgrade;

import material.ultimate.upgrade.block.screen.VoidAssemblerScreen;
import material.ultimate.upgrade.entity.renderer.SurgeArrowEntityRenderer;
import material.ultimate.upgrade.particle.SpellParticle;
import material.ultimate.upgrade.registry.UltimateEntityTypes;
import material.ultimate.upgrade.registry.UltimateItems;
import material.ultimate.upgrade.registry.UltimateParticles;
import material.ultimate.upgrade.registry.UltimateScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

public class UltimateUpgradeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(UltimateScreen.VOID_ASSEMBLER, VoidAssemblerScreen::new);

        EntityRendererRegistry.register(UltimateEntityTypes.COSMIC_FIREBALL, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(UltimateEntityTypes.SURGE_ARROW, SurgeArrowEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(UltimateParticles.VOID, UltimateUpgradeClient.VoidFactory::new);

        ModelPredicateProviderRegistry.register(UltimateItems.ACCELERATOR, new Identifier("pull"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / (float)itemStack.getMaxUseTime();
        });

        ModelPredicateProviderRegistry.register(UltimateItems.ACCELERATOR, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
    }


    @Environment(EnvType.CLIENT)
    public static class VoidFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public VoidFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            SpellParticle spellParticle = new SpellParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            spellParticle.setColor(0.0F, 0.0F, 0.0F);
            return spellParticle;
        }
    }
}
