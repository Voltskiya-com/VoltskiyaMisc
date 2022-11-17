package apple.voltskiya.miscellaneous.gms;

import apple.voltskiya.miscellaneous.gms.cmi.CommandFeed;
import apple.voltskiya.miscellaneous.gms.cmi.CommandHeal;
import apple.voltskiya.miscellaneous.gms.cmi.CommandSetFlyspeed;
import apple.voltskiya.miscellaneous.gms.give.tagged.CommandGiveTagged;
import apple.voltskiya.miscellaneous.gms.item.CommandItemDetails;
import apple.voltskiya.miscellaneous.gms.item.CommandRepair;
import apple.voltskiya.miscellaneous.gms.warp.CommandBack;
import apple.voltskiya.miscellaneous.gms.warp.CommandForward;
import apple.voltskiya.miscellaneous.gms.warp.CommandWarp;
import apple.voltskiya.miscellaneous.gms.warp.LocationHistoryDatabase;
import apple.voltskiya.miscellaneous.gms.warp.TeleportListener;
import apple.voltskiya.miscellaneous.gms.warp.WarpDatabase;
import com.voltskiya.lib.AbstractModule;

public class PluginCommands extends AbstractModule {

    private static AbstractModule instance;

    public static AbstractModule get() {
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
        new CommandGiveTagged();
    }

    @Override
    public String getName() {
        return "GmCommands";
    }
}
