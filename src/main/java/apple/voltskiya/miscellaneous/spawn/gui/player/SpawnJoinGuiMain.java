package apple.voltskiya.miscellaneous.spawn.gui.player;

import apple.mc.utilities.inventory.gui.acd.page.InventoryGuiPageImplACD;
import apple.voltskiya.miscellaneous.spawn.PlayerSpawnDatabase;
import apple.voltskiya.miscellaneous.spawn.PlayerSpawnpoints;
import java.util.Map;

public class SpawnJoinGuiMain extends InventoryGuiPageImplACD<SpawnJoinGui> {

    public SpawnJoinGuiMain(SpawnJoinGui parent) {
        super(parent);
    }

    @Override
    public void initialize() {
        for (Map.Entry<Integer, PlayerSpawnpoints> spawnpoint : PlayerSpawnDatabase.get()
            .getSpawnPoints().entrySet()) {
            setSlot(slotImpl(e -> spawnpoint.getValue().spawn(e.getWhoClicked()),
                    spawnpoint.getValue().toItem()),
                spawnpoint.getKey()
            );
        }
    }

    @Override
    public String getName() {
        return "Voltskiya";
    }

    @Override
    public int size() {
        return 36;
    }

}
