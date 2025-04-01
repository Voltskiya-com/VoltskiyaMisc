package com.voltskiya.misc.players.resourcepack;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.voltskiya.misc.VoltskiyaPlugin;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HexFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;

public class ResourcePackListener implements Listener {

    private static final String resourcePack = ResourcePackConfig.get().url;

    public ResourcePackListener() {
        VoltskiyaPlugin.get().registerEvents(this);
        if (!ResourcePackConfig.get().isRecentlyUpdated()) {
            try {
                URL url = new URL(resourcePack);
                String hash;
                try (InputStream stream = url.openStream()) {
                    @SuppressWarnings("deprecation") HashCode out = Hashing.sha1().hashBytes(stream.readAllBytes());
                    hash = HexFormat.of().formatHex(out.asBytes());
                }
                ResourcePackConfig.get().update(hash);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Bad resourcepack URL: " + resourcePack, e);
            } catch (IOException e) {
                throw new RuntimeException("Error reading resourcepack: " + resourcePack, e);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> {
            Player player = event.getPlayer();
            player.setResourcePack(resourcePack, ResourcePackConfig.get().hash, true);
            if (isAccepted(player.getResourcePackStatus())) return;
            if (player.getResourcePackStatus() != null) {
                declinedPack(player);
                return;
            }
            VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> {
                if (isAccepted(player.getResourcePackStatus())) return;
                declinedPack(player);
            }, 10 * 20);
        }, 5);
    }

    private boolean isAccepted(Status newStatus) {
        return newStatus == Status.ACCEPTED || newStatus == Status.SUCCESSFULLY_LOADED;
    }

    private void declinedPack(Player player) {
        TextComponent title = Component.text("Resourcepack is required", TextColor.color(0x730202));
        Times times = Times.times(Duration.ZERO, Duration.ofSeconds(7), Duration.ZERO);
        player.showTitle(Title.title(title, Component.empty(), times));
        VoltskiyaPlugin.get().scheduleSyncDelayedTask(() -> {
            if (isAccepted(player.getResourcePackStatus())) return;
            player.kick(title);
        }, 7 * 20);
    }
}
