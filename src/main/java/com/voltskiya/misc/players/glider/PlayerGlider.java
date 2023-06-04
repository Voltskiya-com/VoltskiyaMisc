package com.voltskiya.misc.players.glider;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerGlider {

    private static final Map<Player, PlayerGlider> glider = new HashMap<>();
    private final Player player;
    private Vector velocity;
    private boolean isGliding = false;

    public PlayerGlider(Player player) {
        this.player = player;
        this.velocity = player.getVelocity();
    }

    public static void tickAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            glider.computeIfAbsent(player, PlayerGlider::new).tick();
        }
    }

    private void tick() {
        if (player.isGliding()) glidingTick();
        else this.isGliding = false;
    }

    private void glidingTick() {
        if (!this.isGliding) {
            this.velocity = player.getVelocity();
            this.isGliding = true;
            return;
        }
        Vector difference = player.getVelocity().subtract(this.velocity);
        double DIVIDE = 2;
        difference.divide(new Vector(DIVIDE, 1, DIVIDE));
        this.velocity.add(difference);
        player.setVelocity(velocity);
    }
}
