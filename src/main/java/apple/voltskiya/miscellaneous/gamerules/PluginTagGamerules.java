package apple.voltskiya.miscellaneous.gamerules;

import com.voltskiya.lib.AbstractModule;
import apple.voltskiya.miscellaneous.gamerules.arrow.ArrowHitListener;
import apple.voltskiya.miscellaneous.gamerules.coral.CoralFadeListener;
import apple.voltskiya.miscellaneous.gamerules.damage.NoDamageCausedListener;
import apple.voltskiya.miscellaneous.gamerules.damage.NoEntityDamageListener;
import apple.voltskiya.miscellaneous.gamerules.revenge.HitRevengeListener;
import apple.voltskiya.miscellaneous.gamerules.soul_mate.SoulMateDeathListener;

public class PluginTagGamerules extends AbstractModule {

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
