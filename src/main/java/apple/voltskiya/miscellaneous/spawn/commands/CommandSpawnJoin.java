package apple.voltskiya.miscellaneous.spawn.commands;

import apple.mc.utilities.player.chat.SendMessage;
import apple.mc.utilities.world.vector.VectorUtils;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import apple.voltskiya.miscellaneous.spawn.PlayerSpawnDatabase;
import apple.voltskiya.miscellaneous.spawn.gui.player.SpawnJoinGui;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("join")
public class CommandSpawnJoin extends BaseCommand implements SendMessage {
    public CommandSpawnJoin() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Default
    public void join(Player player) {
        double distanceFromSpawn = VectorUtils.distance(PlayerSpawnDatabase.get().respawnBox, player.getLocation());
        boolean isInMainWorld = player.getWorld().getUID().equals(PlayerSpawnDatabase.get().mainWorld);
        boolean isFarFromSpawn = distanceFromSpawn > 100;
        if (isFarFromSpawn && isInMainWorld) {
            red(player, "You already joined in the main world!");
            return;
        }
        player.openInventory(new SpawnJoinGui(player).getInventory());
    }
}
