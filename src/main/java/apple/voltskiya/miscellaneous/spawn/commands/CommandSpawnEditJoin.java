package apple.voltskiya.miscellaneous.spawn.commands;

import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import apple.voltskiya.miscellaneous.spawn.PlayerSpawnDatabase;
import apple.voltskiya.miscellaneous.spawn.gui.gm.SpawnJoinEditGui;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("join_edit ")
public class CommandSpawnEditJoin extends BaseCommand {
    public CommandSpawnEditJoin() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Subcommand("gui")
    public void spawn(Player player) {
        player.openInventory(new SpawnJoinEditGui(player).getInventory());
    }

    @Subcommand("mainworld")
    public void mainWorld(Player player) {
        PlayerSpawnDatabase.get().setMainWorld(player.getWorld().getUID());
    }

    @Subcommand("boxSpawn")
    public void boxSpawn(Player player) {
        PlayerSpawnDatabase.get().setBoxSpawn(player.getLocation());
    }
}
