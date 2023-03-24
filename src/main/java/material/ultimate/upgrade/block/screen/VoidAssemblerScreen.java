package material.ultimate.upgrade.block.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static material.ultimate.upgrade.UltimateUpgrade.id;

public class VoidAssemblerScreen extends HandledScreen<VoidAssemblerScreenHandler> {

    public static final Identifier ASSEMBLER_UI = id("textures/gui/void_assembler.png");

    public VoidAssemblerScreen(VoidAssemblerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        backgroundHeight = 236;
        backgroundWidth = 190;
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ASSEMBLER_UI);

        int middleX = (width - backgroundWidth) / 2;
        int middleY = (height - backgroundHeight) / 2;

        drawTexture(matrices, middleX, middleY, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    public void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        assert client != null;
        int middleX = backgroundWidth / 2 - client.textRenderer.getWidth(title) / 2;
        textRenderer.draw(matrices, title, (float) middleX - 3, (float) titleY, 4210752);
    }
}
