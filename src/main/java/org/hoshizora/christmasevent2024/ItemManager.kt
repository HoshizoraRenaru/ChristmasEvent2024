package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.ChatColor

object ItemManager {
    fun createSoulOverseer(): ItemStack {
        val soulOverseer = ItemStack(Material.DIAMOND_SHOVEL)
        val meta: ItemMeta? = soulOverseer.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&k&lL &b&l영혼 &9&l지휘봉 &f&k&lL&r&f"))
            soulOverseer.itemMeta = it
        }

        return soulOverseer
    }
}