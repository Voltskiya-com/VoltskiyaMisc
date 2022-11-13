package apple.voltskiya.miscellaneous.ai_changes;


import apple.lib.pmc.AppleModule;
import apple.voltskiya.miscellaneous.ai_changes.hit_revenge.HitRevengeListener;

public class PluginAIChanges extends AppleModule {
    @Override
    public void enable() {
        new HitRevengeListener();
    }

    @Override
    public String getName() {
        return "ai_changes";
    }
}
