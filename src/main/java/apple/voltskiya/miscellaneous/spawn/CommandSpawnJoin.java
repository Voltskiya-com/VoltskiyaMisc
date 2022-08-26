package apple.voltskiya.miscellaneous.spawn;

import apple.mc.utilities.player.chat.SendMessage;
import apple.mc.utilities.world.vector.VectorUtils;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import apple.voltskiya.miscellaneous.spawn.gui.gm.SpawnJoinEditGui;
import apple.voltskiya.miscellaneous.spawn.gui.player.SpawnJoinGui;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("join")
public class CommandSpawnJoin extends BaseCommand implements SendMessage {

    public CommandSpawnJoin() {
        VoltskiyaPlugin.get().registerCommand(this);
    }

    @Default
    public void join(Player player) {
        double distanceFromSpawn = VectorUtils.distance(PlayerSpawnDatabase.get().respawnBox,
            player.getLocation());
        boolean isInMainWorld = player.getWorld().getUID()
            .equals(PlayerSpawnDatabase.get().mainWorld);
        boolean isFarFromSpawn = distanceFromSpawn > 100;
        if (isFarFromSpawn && isInMainWorld) {
            red(player, "You already joined in the main world!");
            return;
        }
        player.openInventory(new SpawnJoinGui(player).getInventory());
    }

    @CommandPermission("gm.edit_join")
    @Subcommand("edit")
    public class CommandSpawnJoinEdit extends BaseCommand {

        @Default
        @CommandPermission("gm.edit_join")
        @Subcommand("gui")
        public void spawn(Player player) {
            player.openInventory(new SpawnJoinEditGui(player).getInventory());
        }

        @CommandPermission("gm.edit_join")
        @Subcommand("mainWorld")
        public void mainWorld(Player player) {
            PlayerSpawnDatabase.get().setMainWorld(player.getWorld().getUID());
        }

        @CommandPermission("gm.edit_join")
        @Subcommand("boxSpawn")
        public void boxSpawn(Player player) {
            PlayerSpawnDatabase.get().setBoxSpawn(player.getLocation());
        }
    }
}