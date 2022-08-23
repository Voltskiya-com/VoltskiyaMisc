package apple.voltskiya.miscellaneous.gms.colors;

import apple.mc.utilities.player.chat.SendMessage;
import apple.mc.utilities.player.wand.Wand;
import apple.mc.utilities.world.vector.VectorUtils;
import apple.nms.decoding.world.DecodeBiome;
import apple.nms.decoding.world.DecodeMinecraftKey;
import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WandKeldo extends Wand implements SendMessage {

    private int brushSize = 5;
    private final KeldoBrushPercentages percentages = new KeldoBrushPercentages();
    private ResourceLocation replacement = null;

    public WandKeldo(Player player) {
        super(player);
    }

    public static void load() {
    }

    @Override
    public void onEvent(PlayerInteractEvent event) {
        if (event.getAction().isLeftClick())
            countBiomes(event);
        else
            doSetBiomes(event);
    }

    private void doSetBiomes(PlayerInteractEvent event) {
        if (percentages == null) {
            event.getPlayer().sendMessage(
                ChatColor.RED + "Set the biome weights you want before trying to use this brush!");
            return;
        }
        Player player = event.getPlayer();
        Location location = getHitLocation(player);
        if (location == null)
            return;

        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();
        CraftWorld world = (CraftWorld) location.getWorld();
        int successCount = 0;
        Map<ResourceLocation, Integer> biomesPresent = new HashMap<>();
        for (int xi = -brushSize; xi <= brushSize; xi++) {
            for (int yi = -brushSize; yi <= brushSize; yi++) {
                for (int zi = -brushSize; zi <= brushSize; zi++) {
                    if (VectorUtils.magnitude(xi, yi, zi) > brushSize)
                        continue;
                    ResourceLocation biome = percentages.getRandomBiome();
                    if (biome == null)
                        continue;
                    int xFinal = x + xi;
                    int yFinal = y + yi;
                    int zFinal = z + zi;
                    Holder<Biome> replaced = DecodeBiome.getBiome(world, xFinal, yFinal, zFinal);
                    boolean isReplacementSame = Objects.equal(replaced.unwrapKey().get().location(),
                        replacement);
                    if (replacement == null || isReplacementSame) {
                        DecodeBiome.setBiomeAt(world, biome, xFinal, yFinal, zFinal);
                        successCount++;
                        ResourceLocation key = DecodeBiome.getBiomeKey(world, xFinal, yFinal,
                            zFinal).location();
                        biomesPresent.compute(key, (k, c) -> c == null ? 1 : c + 1);
                    }
                }
            }
        }

        green(player, "Replaced %d block's biome", successCount);
        aqua(player, makeCountMessage(biomesPresent));
    }

    private void countBiomes(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = getHitLocation(player);
        if (location == null)
            return;
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        Map<ResourceLocation, Integer> biomesPresent = new HashMap<>();
        for (int xi = -brushSize; xi <= brushSize; xi++) {
            for (int yi = -brushSize; yi <= brushSize; yi++) {
                for (int zi = -brushSize; zi <= brushSize; zi++) {
                    if (VectorUtils.magnitude(xi, yi, zi) > brushSize)
                        continue;
                    ResourceLocation key = DecodeBiome.getBiomeKey(world, x + xi, y + yi, z + zi)
                        .location();
                    biomesPresent.compute(key, (k, c) -> c == null ? 1 : c + 1);
                }
            }
        }
        SendMessage.get().aqua(player, makeCountMessage(biomesPresent));
    }

    @NotNull
    private String makeCountMessage(Map<ResourceLocation, Integer> biomesPresent) {
        return biomesPresent.entrySet().stream()
            .map(entry -> entry.getKey().toString() + ": " + entry.getValue())
            .collect(Collectors.joining("\n"));
    }

    @Nullable
    private Location getHitLocation(Player player) {
        return VectorUtils.getHitLocation(player.getLocation(), 300, FluidCollisionMode.NEVER,
            false);
    }

    public List<String> setPercentages(Player player, List<String> biomes, List<Integer> percs) {
        return percentages.setPercentages(player.getWorld(), biomes, percs);
    }

    public void setBrushSize(int size) {
        this.brushSize = size;
    }

    public boolean setReplacement(Player player, String replacement) {
        ResourceLocation replacementName = DecodeMinecraftKey.makeKey(replacement);
        Biome biomeBase = DecodeBiome.getBiomeFromKey(player.getWorld(), replacementName);
        if (biomeBase == null)
            return false;
        this.replacement = replacementName;
        return true;
    }

    public void resetReplacement() {
        this.replacement = null;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public List<KeldoBrushPercentages.BrushPercentage> getBiomes() {
        return percentages.getBiomes();
    }

    public ResourceLocation getReplacement() {
        return this.replacement;
    }
}
