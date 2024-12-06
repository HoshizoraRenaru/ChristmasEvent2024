package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object SophisticatedAmethystItemManager {

    fun createSophisticatedAmethyst(): ItemStack {
        val block = ItemStack(Material.AMETHYST_CLUSTER)
        val meta: ItemMeta? = block.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f세련된 자수정"))
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
