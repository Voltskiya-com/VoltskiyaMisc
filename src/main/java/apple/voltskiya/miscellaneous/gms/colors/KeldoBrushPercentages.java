package apple.voltskiya.miscellaneous.gms.colors;

import apple.nms.decoding.world.DecodeBiome;
import apple.nms.decoding.world.DecodeMinecraftKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.World;

public class KeldoBrushPercentages {

    private static final Random RANDOM = new Random();
    private List<BrushPercentage> percentages = Collections.emptyList();
    private int totalWeight = 0;


    public ResourceLocation getRandomBiome() {
        if (totalWeight <= 0)
            totalWeight = 1;
        int weight = RANDOM.nextInt(totalWeight);
        for (BrushPercentage brushPercentage : percentages) {
            weight -= brushPercentage.weight;
            if (weight <= 0) {
                return brushPercentage.name;
            }
        }
        return null;
    }

    public List<String> setPercentages(World world, List<String> biomes, List<Integer> percs) {
        percentages = new ArrayList<>();
        totalWeight = 0;
        Iterator<String> biomeIterator = biomes.iterator();
        Iterator<Integer> percIterator = percs.iterator();
        List<String> fails = new ArrayList<>();
        while (biomeIterator.hasNext() && percIterator.hasNext()) {
            String biomeNext = biomeIterator.next();
            try {
                Integer percNext = percIterator.next();
                percentages.add(new BrushPercentage(world, percNext, biomeNext));
                totalWeight += percNext;
            } catch (BiomeCreationException e) {
                fails.add(biomeNext);
            }
        }
        return fails;
    }


    public List<BrushPercentage> getBiomes() {
        return percentages == null ? Collections.emptyList() : percentages;
    }

    public static class BrushPercentage {

        private final ResourceLocation name;
        private final Biome biome;
        private final int weight;

        public BrushPercentage(World world, Integer perc, String biome) {
            this.name = DecodeMinecraftKey.makeKey(biome);
            this.biome = DecodeBiome.getBiomeFromKey(world, name);
            if (this.biome == null)
                throw new BiomeCreationException();
            this.weight = perc;
        }

        @Override
        public String toString() {
            return name.toString() + "=" + weight;
        }
    }

    private static class BiomeCreationException extends IllegalArgumentException {

    }
}
