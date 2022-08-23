package apple.voltskiya.miscellaneous.gms.colors;

import apple.mc.utilities.player.chat.SendMessage;
import apple.nms.decoding.iregistry.DecodeIRegistry;
import apple.nms.decoding.world.DecodeBiome;
import apple.nms.decoding.world.DecodeMinecraftKey;
import apple.voltskiya.miscellaneous.VoltskiyaPlugin;
import apple.voltskiya.miscellaneous.gms.PluginCommands;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Split;
import co.aikar.commands.annotation.Subcommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("keldo")
@CommandPermission("keldo.keldo")
public class CommandKeldo extends BaseCommand implements SendMessage {

    public CommandKeldo() {
        PaperCommandManager commandManager = VoltskiyaPlugin.get().getCommandManager();
        commandManager.registerCommand(this);
        commandManager.getCommandCompletions()
            .registerCompletion("custom_biomes", this::customBiomes);
        commandManager.getCommandCompletions().registerCompletion("normal_biomes", c -> {
            List<String> completions = new ArrayList<>(Biome.values().length * 2);
            for (Biome biome : Biome.values()) {
                String s = biome.name().toLowerCase(Locale.ROOT);
                completions.add("minecraft:" + s);
                completions.add(s);
            }
            return completions;
        });
    }

    private Collection<String> customBiomes(BukkitCommandCompletionContext context) {
        List<String> completions = new ArrayList<>();
        Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = DecodeBiome.getBiomeRegistry(
            context.getPlayer().getWorld());
        for (ResourceKey<net.minecraft.world.level.biome.Biome> biome : DecodeIRegistry.list(
            biomeRegistry)) {
            if (!DecodeMinecraftKey.isMinecraft(biome.location()))
                completions.add(biome.toString());
        }
        return completions;
    }

    @Subcommand("biomes")
    @CommandCompletion("@custom_biomes @range:1-100 @custom_biomes @range:1-100 @custom_biomes @range:1-100 @custom_biomes @range:1-100 @custom_biomes @range:1-100 @nothing")
    public void setPercentages(Player player, @Split(" ") String[] args) {
        @NotNull WandKeldo wand = getWand(player);
        List<String> biomes = new ArrayList<>();
        List<Integer> percs = new ArrayList<>();
        Iterator<String> argsIterator = Arrays.stream(args).iterator();
        while (argsIterator.hasNext()) {
            biomes.add(argsIterator.next().toLowerCase(Locale.ROOT));
            if (!argsIterator.hasNext()) {
                red(player, "Make sure each biome listed has a weight associated with it.");
                return;
            }
            String arg = argsIterator.next();
            try {
                percs.add(Integer.parseInt(arg));
            } catch (NumberFormatException e) {
                red(player, "'%s' is not a number", arg);
                return;
            }
        }
        if (biomes.isEmpty()) {
            red(player, "Please list biomes and weights");
            return;
        }
        List<String> fails = wand.setPercentages(player, biomes, percs);
        if (!fails.isEmpty()) {
            red(player, "The following biomes failed to be found: '%s' ",
                String.join("', '", fails));
        } else {
            info(player);
        }
    }

    private WandKeldo getWand(Player player) {
        return PluginCommands.WAND_KELDO.getWand(player);
    }

    @Subcommand("brushsize")
    public void brushSize(Player player, int size) {
        @NotNull WandKeldo wand = getWand(player);
        wand.setBrushSize(size);
        info(player);
    }

    @Subcommand("wand")
    public void wand(Player player) {
        player.getInventory()
            .addItem(PluginCommands.WAND_KELDO.createItem(Material.ORANGE_DYE, "ORANGE"));
    }

    @Subcommand("replacement")
    @CommandCompletion("@custom_biomes|@normal_biomes")
    public void replacement(Player player, String replacement) {
        @NotNull WandKeldo wand = getWand(player);
        boolean success = wand.setReplacement(player, replacement.toLowerCase(Locale.ROOT));
        if (success)
            info(player);
        else
            red(player, "could not set the replacement biome");
    }

    @Subcommand("resetreplacement")
    public void resetReplacement(Player player) {
        @NotNull WandKeldo wand = getWand(player);
        wand.resetReplacement();
        info(player);
    }

    @Subcommand("info")
    public void info(Player player) {
        @NotNull WandKeldo wand = getWand(player);
        ResourceLocation replacement = wand.getReplacement();
        String message = String.format("""
                Brush size: %d,
                Biomes: %s
                Replacement: %s
                """, wand.getBrushSize(),
            wand.getBiomes().stream().map(Object::toString).collect(Collectors.joining(" ")),
            replacement == null ? "None" : replacement.toString());
        aqua(player, message);
    }
}
