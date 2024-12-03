package org.hoshizora.christmasevent2024

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin

class SugarBlockRecipe(private val plugin: JavaPlugin) {

    fun register() {
        val sugarBlock = SugarBlockItemManager.createSugarBlock()
        sugarBlock.amount = 1
        val key = NamespacedKey(plugin, "sugar_block")
        val recipe = ShapelessRecipe(key, sugarBlock).apply {
            addIngredient(9, Material.SUGAR)
        }
        Bukkit.addRecipe(recipe)
    }
}