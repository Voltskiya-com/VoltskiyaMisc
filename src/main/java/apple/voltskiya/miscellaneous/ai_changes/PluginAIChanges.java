package apple.voltskiya.miscellaneous.ai_changes;


import apple.lib.pmc.PluginModule;
import apple.voltskiya.miscellaneous.ai_changes.hit_revenge.HitRevengeListener;

public class PluginAIChanges extends PluginModule {
    @Override
    public void enable() {
        new HitRevengeListener();
    }

    @Override
    public String getName() {
        return "ai_changes";
    }
}
