package org.hoshizora.christmasevent2024

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class CaramelRecipe(private val plugin: JavaPlugin) {

    fun register() {
        val caramel = CaramelItemManager.createCaramel()
        val key = NamespacedKey(plugin, "caramel")

        val recipe = ShapedRecipe(key, caramel).apply {
            shape("SSS", "SHS", "SSS")
            setIngredient('S', RecipeChoice.ExactChoice(SugarBlockItemManager.createSugarBlock()))
            setIngredient('H', Material.HONEY_BOTTLE)
        }

        Bukkit.addRecipe(recipe)
    }
}
