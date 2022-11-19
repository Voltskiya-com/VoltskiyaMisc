package com.voltskiya.misc.admin;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.misc.admin.console.command.CommandIntercepter;
import com.voltskiya.misc.admin.console.command.CommandListenerCommand;
import com.voltskiya.misc.admin.console.monitor.CommandTaskManager;
import com.voltskiya.misc.admin.reload.CommandReload;
import org.bukkit.event.HandlerList;

public class PluginAdmin extends AbstractModule {

    private static PluginAdmin instance;
    private CommandIntercepter listener = null;

    @Override
    public void enable() {
        instance = this;
        new CommandListenerCommand();
        new CommandTaskManager();
        new CommandReload();
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
