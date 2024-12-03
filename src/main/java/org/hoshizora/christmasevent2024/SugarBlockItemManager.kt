package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object SugarBlockItemManager {

    fun createSugarBlock(): ItemStack {
        val block = ItemStack(Material.WHITE_CONCRETE_POWDER)
        val meta: ItemMeta? = block.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f설탕 블록"))
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
