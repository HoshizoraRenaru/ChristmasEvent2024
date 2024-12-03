package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.FireworkMeta

object SantasTradeSecretItemManager {

    fun createSantasTradeSecrets(): ItemStack {
        val firework = ItemStack(Material.FIREWORK_ROCKET)
        val meta = firework.itemMeta as? FireworkMeta

        meta?.let {
            it.power = 0

            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&l&kL &r&1｡&f✧&1:&f˚&1*:&f･｡ &9&l산타의 &5&l영업 &d&l기밀 &r&1｡･&f:&1*&f˚&1:✧&f｡ &f&l&kL&f"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ${ChatColor.AQUA}${ChatColor.BOLD}대쉬 ${ChatColor.YELLOW}${ChatColor.BOLD}RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.YELLOW}20 ${ChatColor.GRAY}경험치를 소모하여",
                "${ChatColor.RESET}${ChatColor.GRAY}10블록 앞으로 빠르게 이동합니다.",
                "${ChatColor.GRAY}쿨다운: ${ChatColor.GREEN}10초",
                ""
            )
            firework.itemMeta = it
        }

        // 인챈트 추가 (장식용)
        firework.addUnsafeEnchantment(Enchantment.MENDING, 1)
        firework.addUnsafeEnchantment(Enchantment.DURABILITY, 3)

        return firework
    }
}
