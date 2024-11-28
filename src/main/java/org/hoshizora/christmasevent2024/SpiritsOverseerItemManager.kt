package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import net.md_5.bungee.api.ChatColor

object SpiritsOverseerItemManager {
    fun createSoulOverseer(): ItemStack {
        val soulOverseer = ItemStack(Material.DIAMOND_SHOVEL)
        val meta: ItemMeta? = soulOverseer.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&k&lL &f&l얼어붙은 &b&l정령 &9&l감독관 &f&k&lL&r&f"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ${ChatColor.AQUA}${ChatColor.BOLD}정령의 축복 ${ChatColor.YELLOW}${ChatColor.BOLD}RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.GREEN}0.1초${ChatColor.GRAY}당 ${ChatColor.YELLOW}1 ${ChatColor.GRAY}경험치를 소모하여 주변의 적에게",
                "${ChatColor.GREEN}1초${ChatColor.GRAY}당 ${ChatColor.RED}3 ${ChatColor.GRAY}데미지를 가한다. 또한 범위 내의 ",
                "${ChatColor.GRAY}적에게 ${ChatColor.RED}구속 2 ${ChatColor.GRAY}효과를 부여한다."
            )
            soulOverseer.itemMeta = it
        }

        soulOverseer.durability = 0

        return soulOverseer
    }
}