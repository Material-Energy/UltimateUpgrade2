package material.ultimate.upgrade.registry;

import material.ultimate.upgrade.block.screen.VoidAssemblerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

import static material.ultimate.upgrade.UltimateUpgrade.id;

public class UltimateScreen {
    public static final ScreenHandlerType<VoidAssemblerScreenHandler> VOID_ASSEMBLER = Registry.register(Registry.SCREEN_HANDLER, id("void_assembler"), new ScreenHandlerType<>(VoidAssemblerScreenHandler::new));

    public static void init() {
    }
}
