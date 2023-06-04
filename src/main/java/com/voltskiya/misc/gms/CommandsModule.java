package com.voltskiya.misc.gms;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.misc.gms.cmi.CommandFeed;
import com.voltskiya.misc.gms.cmi.CommandHeal;
import com.voltskiya.misc.gms.cmi.CommandSetFlyspeed;
import com.voltskiya.misc.gms.give.tagged.CommandGiveTagged;
import com.voltskiya.misc.gms.item.CommandItemDetails;
import com.voltskiya.misc.gms.item.CommandRepair;
import com.voltskiya.misc.gms.model.CommandPlaceItem;
import com.voltskiya.misc.gms.warp.CommandBack;
import com.voltskiya.misc.gms.warp.CommandForward;
import com.voltskiya.misc.gms.warp.CommandWarp;
import com.voltskiya.misc.gms.warp.LocationHistoryDatabase;
import com.voltskiya.misc.gms.warp.TeleportListener;
import com.voltskiya.misc.gms.warp.WarpDatabase;

public class CommandsModule extends AbstractModule {

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
        new CommandPlaceItem();
    }

    @Override
    public String getName() {
        return "GmCommands";
    }
}
