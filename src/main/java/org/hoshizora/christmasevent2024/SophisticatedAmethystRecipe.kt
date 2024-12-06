package org.hoshizora.christmasevent2024

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin

class SophisticatedAmethystRecipe(private val plugin: JavaPlugin) {

    fun register() {
        val sophisticatedAmethyst = SophisticatedAmethystItemManager.createSophisticatedAmethyst()
        sophisticatedAmethyst.amount = 1
        val key = NamespacedKey(plugin, "sophisticated_amethyst")
        val recipe = ShapelessRecipe(key, sophisticatedAmethyst).apply {
            addIngredient(9, Material.AMETHYST_SHARD)
        }
        Bukkit.addRecipe(recipe)
    }
}