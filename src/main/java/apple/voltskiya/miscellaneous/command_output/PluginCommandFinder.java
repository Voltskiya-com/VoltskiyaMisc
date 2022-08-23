package apple.voltskiya.miscellaneous.command_output;

import org.bukkit.event.HandlerList;
import apple.lib.pmc.PluginModule;

public class PluginCommandFinder extends PluginModule {
    private static PluginCommandFinder instance;
    private CommandIntercepter listener = null;

    @Override
    public void enable() {
        instance = this;
        new CommandListenerCommand();
    }

    public static PluginCommandFinder get() {
        return instance;
    }

    public void enable(boolean shouldRun) {
        if (shouldRun) {
            if (this.listener == null) {
                this.listener = new CommandIntercepter();
            }
        } else if (this.listener != null) {
            HandlerList.unregisterAll(listener);
            this.listener = null;
        }
    }

    @Override
    public String getName() {
        return "command_finder";
    }
}
