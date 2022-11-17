package apple.voltskiya.miscellaneous.admin;

import com.voltskiya.lib.AbstractModule;
import apple.voltskiya.miscellaneous.admin.console.command.CommandIntercepter;
import apple.voltskiya.miscellaneous.admin.console.command.CommandListenerCommand;
import apple.voltskiya.miscellaneous.admin.console.monitor.CommandTaskManager;
import org.bukkit.event.HandlerList;

public class PluginAdmin extends AbstractModule {

    private static PluginAdmin instance;
    private CommandIntercepter listener = null;

    @Override
    public void enable() {
        instance = this;
        new CommandListenerCommand();
        new CommandTaskManager();
    }

    public static PluginAdmin get() {
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
        return "Admin";
    }
}