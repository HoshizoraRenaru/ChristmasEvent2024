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
            it.lore = listOf(
                "${ChatColor.YELLOW}스킬: ${ChatColor.AQUA}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}소울 바이트 ${ChatColor.RESET}${ChatColor.GOLD}${ChatColor.BOLD}RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.GREEN}0.1초${ChatColor.GRAY}당 ${ChatColor.YELLOW}1${ChatColor.GRAY}경험치를 소모하여 주변 몹들에게",
                "${ChatColor.GREEN}1초${ChatColor.GRAY}당${ChatColor.RED}1${ChatColor.GRAY}데미지를 가한다."
            )
            soulOverseer.itemMeta = it
        }

        return soulOverseer
    }
}