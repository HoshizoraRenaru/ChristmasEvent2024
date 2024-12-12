package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object ChristmasRandomBoxFragmentItemManager {

    fun createFragment(): ItemStack {
        val block = ItemStack(Material.PURPLE_DYE)
        val meta: ItemMeta? = block.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l&kL &9&l크리스마스 &5&l랜덤박스 &f&l파편 &c&l&kL"))
            it.lore = listOf(
                "",
                ChatColor.translateAlternateColorCodes('&', "&d크&9리&d스&9마&d스 &f이벤트 아이템")
            )
            it.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
            it.addEnchant(Enchantment.MENDING, 1, true)
            it.isUnbreakable = true
            block.itemMeta = it
        }
        return block
    }
}
