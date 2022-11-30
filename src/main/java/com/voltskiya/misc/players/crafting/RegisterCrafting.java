package com.voltskiya.misc.players.crafting;

import com.voltskiya.misc.VoltskiyaPlugin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class RegisterCrafting implements Listener {
    ArrayList<NamespacedKey> recipesNames = new ArrayList<>();

    public RegisterCrafting() {
        VoltskiyaPlugin.get().registerEvents(this);
        recipeFilter();
        recipeChain();
        recipeWool();
        recipeChainArmor();
    }

    private void recipeChain(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(VoltskiyaPlugin.get(), "Chain"), new ItemStack(Material.CHAIN, 2));
        recipe.shape("N", "I", "N");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('N', Material.IRON_NUGGET);
        recipesNames.add(recipe.getKey());
        Bukkit.addRecipe(recipe);
    }
    private void recipeWool(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(VoltskiyaPlugin.get(), "Wool"), new ItemStack(Material.STRING, 4));
        recipe.shape("W");
        recipe.setIngredient('W', new RecipeChoice.MaterialChoice(woolList()));
        recipesNames.add(recipe.getKey());
        Bukkit.addRecipe(recipe);
    }
    private void recipeChainArmor(){
        ShapedRecipe helmet = new ShapedRecipe(new NamespacedKey(VoltskiyaPlugin.get(), "ChainHelmet"), new ItemStack(Material.CHAINMAIL_HELMET, 1));
        helmet.shape("CCC", "C C");
        helmet.setIngredient('C', Material.CHAIN);
        recipesNames.add(helmet.getKey());
        Bukkit.addRecipe(helmet);

        ShapedRecipe chestPlate = new ShapedRecipe(new NamespacedKey(VoltskiyaPlugin.get(), "ChainChestPlate"), new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        chestPlate.shape("C C", "CCC", "CCC");
        chestPlate.setIngredient('C', Material.CHAIN);
        recipesNames.add(chestPlate.getKey());
        Bukkit.addRecipe(chestPlate);

        ShapedRecipe leggings = new ShapedRecipe(new NamespacedKey(VoltskiyaPlugin.get(), "ChainLeggings"), new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        leggings.shape("CCC", "C C", "C C");
        leggings.setIngredient('C', Material.CHAIN);
        recipesNames.add(leggings.getKey());
        Bukkit.addRecipe(leggings);

        ShapedRecipe boots = new ShapedRecipe(new NamespacedKey(VoltskiyaPlugin.get(), "ChainBoots"), new ItemStack(Material.CHAINMAIL_BOOTS, 1));
        boots.shape("C C", "C C");
        boots.setIngredient('C', Material.CHAIN);
        recipesNames.add(boots.getKey());
        Bukkit.addRecipe(boots);
    }

    private void recipeFilter(){
        Iterator<Recipe> recipes = Bukkit.recipeIterator();
        List<Material> bannedItems = new ArrayList<>();
        bannedItems.add(Material.CHAIN);
        bannedItems.add(Material.BUCKET);
        while (recipes.hasNext()) {
            if (bannedItems.contains(recipes.next().getResult().getType())) {
                recipes.remove();
            }
        }
    }

    private List<Material> woolList() {
        List<Material> choices = new ArrayList<>();
        choices.add(Material.WHITE_WOOL);
        choices.add(Material.ORANGE_WOOL);
        choices.add(Material.MAGENTA_WOOL);
        choices.add(Material.LIGHT_BLUE_WOOL);
        choices.add(Material.YELLOW_WOOL);
        choices.add(Material.LIME_WOOL);
        choices.add(Material.PINK_WOOL);
        choices.add(Material.GRAY_WOOL);
        choices.add(Material.LIGHT_GRAY_WOOL);
        choices.add(Material.CYAN_WOOL);
        choices.add(Material.PURPLE_WOOL);
        choices.add(Material.BLUE_WOOL);
        choices.add(Material.BROWN_WOOL);
        choices.add(Material.GREEN_WOOL);
        choices.add(Material.RED_WOOL);
        choices.add(Material.BLACK_WOOL);
        return choices;
    }
    @EventHandler
    private void onPlayerJoinAddRecipes(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.discoverRecipes(recipesNames);
    }

}
