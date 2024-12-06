package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object ReflectiveStoneItemManager {

    fun createReflectiveStone(): ItemStack {
        val block = ItemStack(Material.SCULK)
        val meta: ItemMeta? = block.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&l&kL &d크&9리&d스&9마&d스&f의 빛나는 &r&1&l반${ChatColor.of("#1f1fde")}&l사&9&l석 &f&l&kL"))
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
