package material.ultimate.upgrade.registry;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

import static material.ultimate.upgrade.UltimateUpgrade.id;

public class UltimateEntityAttributes {
    public static final EntityAttribute BONUS_BOW_DAMAGE = register("player.bow_damage_bonus", new ClampedEntityAttribute("attribute.name.bow_damage_bonus", 0.0D, 0.0D, 4096.0D).setTracked(true));

    public static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, id(id), attribute);
    }

    public static void init(){

    }
}
