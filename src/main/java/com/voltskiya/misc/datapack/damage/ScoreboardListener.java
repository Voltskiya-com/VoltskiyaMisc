package com.voltskiya.misc.datapack.damage;

import com.voltskiya.misc.VoltskiyaPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardListener implements Listener {

    private Objective damageObjective;

    public ScoreboardListener() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(VoltskiyaPlugin.get(), this::scoreboardTick, 0, 1);
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        damageObjective = scoreboard.getObjective("damage");
        if (damageObjective == null)
            damageObjective = scoreboard.registerNewObjective("damage", Criteria.DUMMY, Component.text("Damage"));
    }

    public void scoreboardTick() {
        Bukkit.getOnlinePlayers().forEach(this::playerScoreboardTick);
    }

    private void playerScoreboardTick(Player player) {
        Score scoreboard = damageObjective.getScore(player);
        if (!scoreboard.isScoreSet())
            return;
        int damageScore = scoreboard.getScore();
        if (damageScore == 0)
            return;
        ((CraftPlayer) player).getHandle().invulnerableTime = 0;
        if (damageScore > 1000)
            hurtExcludeArmor(player, scoreToAmount(damageScore - 1000));
        else if (damageScore > 0)
            hurtIncludeArmor(player, scoreToAmount(damageScore));
        else
            heal(player, scoreToAmount(-damageScore));
        scoreboard.setScore(0);
    }

    private void heal(Player player, float heal) {
        double newHealth = player.getHealth() + heal;
        player.setHealth(normalizeHealth(player, newHealth));
    }

    private double normalizeHealth(Player player, double health) {
        if (health <= 0)
            return 0;
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double maxHealth = attribute == null ? 20 : attribute.getValue();
        return Math.min(health, maxHealth);
    }

    private void hurtIncludeArmor(Player player, float damageAmount) {
        player.damage(damageAmount, player);
    }

    private void hurtExcludeArmor(Player player, float damageAmount) {
        player.damage(damageAmount);
    }

    private float scoreToAmount(int score) {
        return score / 10.0f;
    }
}
