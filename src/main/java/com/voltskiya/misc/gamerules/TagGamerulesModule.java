package com.voltskiya.misc.gamerules;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.misc.gamerules.arrow.ArrowHitListener;
import com.voltskiya.misc.gamerules.blindness.BlindnessListener;
import com.voltskiya.misc.gamerules.coral.CoralFadeListener;
import com.voltskiya.misc.gamerules.damage.NoDamageCausedListener;
import com.voltskiya.misc.gamerules.damage.NoEntityDamageListener;
import com.voltskiya.misc.gamerules.ice.IceSpreadListener;
import com.voltskiya.misc.gamerules.no_vehicle.NoVehicleEnterListener;
import com.voltskiya.misc.gamerules.revenge.HitRevengeListener;
import com.voltskiya.misc.gamerules.soul_mate.SoulMateDeathListener;

public class TagGamerulesModule extends AbstractModule {

    @Override
    public void enable() {
        new NoEntityDamageListener();
        new NoDamageCausedListener();
        new ArrowHitListener();
        new HitRevengeListener();
        new SoulMateDeathListener();
        new CoralFadeListener();
        new IceSpreadListener();
        new NoVehicleEnterListener();
        new BlindnessListener();
        ReloadCleanup.cleanup();
    }

    @Override
    public String getName() {
        return "Gamerules";
    }
}
