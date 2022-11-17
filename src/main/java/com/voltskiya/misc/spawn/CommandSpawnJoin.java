package com.voltskiya.misc.spawn;

import com.voltskiya.lib.acf.BaseCommand;
import com.voltskiya.lib.acf.annotation.CommandAlias;
import com.voltskiya.lib.acf.annotation.CommandPermission;
import com.voltskiya.lib.acf.annotation.Default;
import com.voltskiya.lib.acf.annotation.Subcommand;
import apple.mc.utilities.player.chat.SendMessage;
import apple.mc.utilities.world.vector.VectorUtils;
import com.voltskiya.misc.VoltskiyaPlugin;
import com.voltskiya.misc.spawn.gui.gm.SpawnJoinEditGui;
import com.voltskiya.misc.spawn.gui.player.SpawnJoinGui;
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

    @Subcommand("edit")
    @CommandPermission("volt.game.edit.join")
    public class CommandSpawnJoinEdit extends BaseCommand {

        @Default
        @Subcommand("gui")
        public void spawn(Player player) {
            player.openInventory(new SpawnJoinEditGui(player).getInventory());
        }

        @Subcommand("mainWorld")
        public void mainWorld(Player player) {
            PlayerSpawnDatabase.get().setMainWorld(player.getWorld().getUID());
        }

        @Subcommand("boxSpawn")
        public void boxSpawn(Player player) {
            PlayerSpawnDatabase.get().setBoxSpawn(player.getLocation());
        }
    }
}