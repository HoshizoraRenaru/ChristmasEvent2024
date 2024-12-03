package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.ItemFlag
import net.md_5.bungee.api.ChatColor

object ReflectiveCoreItemManager {
    fun createReflectiveCore(): ItemStack {
        val shard = ItemStack(Material.ECHO_SHARD)
        val meta: ItemMeta? = shard.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&l&kL &r&1&l반${ChatColor.of("#1f1fde")}&l사&9&l체 &f&l&kL"))
            it.lore = listOf(
                "",
                ChatColor.translateAlternateColorCodes('&', "&d크&9리&d스&9마&d스 &f이벤트 아이템")
            )
            it.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
            it.isUnbreakable = true
            shard.itemMeta = it
        }
        shard.addUnsafeEnchantment(Enchantment.FROST_WALKER, 1)

        return shard
    }
}
