package material.ultimate.upgrade.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

import static material.ultimate.upgrade.UltimateUpgrade.id;

public class UltimateParticles {
    public static DefaultParticleType VOID = FabricParticleTypes.simple();

    public static void init(){
        Registry.register(Registry.PARTICLE_TYPE, id("void"), VOID);
    }
}
