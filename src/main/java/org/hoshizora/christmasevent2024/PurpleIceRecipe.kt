package org.hoshizora.christmasevent2024

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin

class PurpleIceRecipe(private val plugin: JavaPlugin) {

    fun register() {
        val purpleIce = PurpleIceItemManager.createPurpleIce()
        purpleIce.amount = 2
        val key = NamespacedKey(plugin, "purple_ice")
        val recipe = ShapelessRecipe(key, purpleIce).apply {
            addIngredient(4, Material.BLUE_ICE)
            addIngredient(1, Material.DRAGON_BREATH)
        }
        Bukkit.addRecipe(recipe)
    }
}