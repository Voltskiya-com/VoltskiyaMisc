package com.voltskiya.misc.gamerules;

import com.voltskiya.lib.AbstractModule;
import com.voltskiya.misc.gamerules.arrow.ArrowHitListener;
import com.voltskiya.misc.gamerules.coral.CoralFadeListener;
import com.voltskiya.misc.gamerules.damage.NoDamageCausedListener;
import com.voltskiya.misc.gamerules.damage.NoEntityDamageListener;
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
    }

    @Override
    public String getName() {
        return "Gamerules";
    }
}
