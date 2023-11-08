package net.fabricmc.waila.mixin;

import btw.community.waila.WailaAddon;
import mcp.mobius.waila.gui.BaseWindowGui;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiAchievement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiAchievement.class)
public abstract class GuiAchievementMixin {
    private WailaAddon instance;
    @Inject(method = "updateAchievementWindow", at = @At("RETURN"))
    public void updateAchievementWindow(CallbackInfo info) {
        if(instance == null) {
            instance = new WailaAddon();
            instance.load();
        }
        if(Minecraft.getMinecraft().theWorld != null) {
            instance.checkKeybind();
        }
    }
}
