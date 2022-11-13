package apple.voltskiya.miscellaneous.gms;

import apple.lib.pmc.AppleModule;
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

public class PluginCommands extends AppleModule implements PluginModuleMcUtil {

    private static AppleModule instance;
    public static WandType<WandKeldo> WAND_KELDO;

    public static AppleModule get() {
        return instance;
    }

    @Override
    public void enable() {
        instance = this;
        LocationHistoryDatabase.initialize();
        WarpDatabase.initialize();
        new CommandGamemode();
        new CommandHeal();
        new CommandFeed();
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
