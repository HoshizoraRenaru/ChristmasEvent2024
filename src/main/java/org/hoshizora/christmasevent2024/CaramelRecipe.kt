package org.hoshizora.christmasevent2024

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin

class CaramelRecipe(private val plugin: JavaPlugin) {

    fun register() {
        val caramel = CaramelItemManager.createCaramel()
        caramel.amount = 2
        val key = NamespacedKey(plugin, "caramel")

        val recipe = ShapelessRecipe(key, caramel).apply {
            addIngredient(RecipeChoice.ExactChoice(SugarBlockItemManager.createSugarBlock()))
            addIngredient(RecipeChoice.ExactChoice(SugarBlockItemManager.createSugarBlock()))
            addIngredient(Material.HONEY_BOTTLE)
        }

        Bukkit.addRecipe(recipe)
    }
}
