package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object CaramelItemManager {

    fun createCaramel(): ItemStack {
        val block = ItemStack(Material.HONEYCOMB_BLOCK)
        val meta: ItemMeta? = block.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f캐러멜"))
            it.lore = listOf(
                "",
                ChatColor.translateAlternateColorCodes('&', "&d크&9리&d스&9마&d스 &f이벤트 아이템")
            )
            it.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
            it.isUnbreakable = true
            block.itemMeta = it
        }
        return block
    }
}
