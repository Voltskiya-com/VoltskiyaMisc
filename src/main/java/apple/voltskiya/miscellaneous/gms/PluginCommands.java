package apple.voltskiya.miscellaneous.gms;

import apple.lib.pmc.PluginModule;
import apple.mc.utilities.PluginModuleMcUtil;
import apple.mc.utilities.player.wand.WandType;
import apple.voltskiya.miscellaneous.gms.colors.CommandKeldo;
import apple.voltskiya.miscellaneous.gms.colors.WandKeldo;
import apple.voltskiya.miscellaneous.gms.warp.CommandBack;
import apple.voltskiya.miscellaneous.gms.warp.CommandForward;
import apple.voltskiya.miscellaneous.gms.warp.CommandWarp;
import apple.voltskiya.miscellaneous.gms.warp.LocationHistoryDatabase;
import apple.voltskiya.miscellaneous.gms.warp.TeleportListener;
import apple.voltskiya.miscellaneous.gms.warp.WarpDatabase;

public class PluginCommands extends PluginModule implements PluginModuleMcUtil {

    private static PluginModule instance;
    public static WandType<WandKeldo> WAND_KELDO;

    public static PluginModule get() {
        return instance;
    }

    @Override
    public void enable() {
        instance = this;
        LocationHistoryDatabase.initialize();
        WarpDatabase.initialize();
        new CommandBack();
        new CommandForward();
        new CommandWarp();
        new TeleportListener();
        new CommandRepair();
        new CommandItemDetails();
        new CommandSetFlyspeed();
//        new CommandTaskManager();
        new CommandKeldo();
        WAND_KELDO = createWand(WandKeldo::new, "keldo_grass");
    }

    @Override
    public String getName() {
        return "GmCommands";
    }
}
