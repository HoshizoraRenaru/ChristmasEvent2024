package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

class FastGlassRecipe(private val plugin: JavaPlugin) {
    fun register() {
        val glass = ItemStack(Material.GLASS, 8)

        val key = NamespacedKey(plugin, "fast_glass")
        val recipe = ShapedRecipe(key, glass)

        recipe.shape("SSS", "SCS", "SSS")
        recipe.setIngredient('S', Material.SAND)
        recipe.setIngredient('C', Material.COAL)

        plugin.server.addRecipe(recipe)
    }
}
