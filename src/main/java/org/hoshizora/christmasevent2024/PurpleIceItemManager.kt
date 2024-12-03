package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.ItemFlag
import net.md_5.bungee.api.ChatColor

object PurpleIceItemManager {

    fun createPurpleIce(): ItemStack {
        val ice = ItemStack(Material.ICE)
        val meta: ItemMeta? = ice.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d보랏빛 얼음"))
            it.lore = listOf(
                "",
                ChatColor.translateAlternateColorCodes('&', "&d크&9리&d스&9마&d스 &f이벤트 아이템")
            )
            it.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
            it.isUnbreakable = true
            ice.itemMeta = it
        }
        ice.addUnsafeEnchantment(Enchantment.FROST_WALKER, 1)

        return ice
    }
}
