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
                "${ChatColor.GOLD}스킬: ${ChatColor.of("#26008e")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}D${ChatColor.of("#2e15b3")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}A${ChatColor.of("#362ad8")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}S${ChatColor.of("blue")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}H${ChatColor.RESET}${ChatColor.WHITE}${ChatColor.BOLD} ⚝${ChatColor.YELLOW}${ChatColor.BOLD} RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.YELLOW}20 ${ChatColor.GRAY}경험치를 소모하여",
                "${ChatColor.RESET}${ChatColor.GREEN}10블록 ${ChatColor.GRAY}앞으로 대쉬한다.",
                "${ChatColor.RESET}${ChatColor.GRAY}대쉬 후 자신에게 ${ChatColor.GREEN}3초${ChatColor.GRAY}간 ${ChatColor.RED}구속 2${ChatColor.GRAY} ",
                "${ChatColor.RESET}${ChatColor.GRAY}효과를 부여한다.",
                "${ChatColor.GRAY}쿨다운: ${ChatColor.GREEN}10초",
                "",
                "${ChatColor.WHITE}이 아이템은 ${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.MAGIC}L ${ChatColor.LIGHT_PURPLE}크${ChatColor.BLUE}리${ChatColor.LIGHT_PURPLE}스${ChatColor.BLUE}마${ChatColor.LIGHT_PURPLE}스 ${ChatColor.WHITE}랜${ChatColor.BLUE}덤${ChatColor.WHITE}박${ChatColor.BLUE}스 ${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.MAGIC}L",
                "${ChatColor.WHITE}에서 드롭되었습니다!",
                "${ChatColor.BOLD}${ChatColor.WHITE}이 아이템은 ${ChatColor.LIGHT_PURPLE}크${ChatColor.BLUE}리${ChatColor.LIGHT_PURPLE}스${ChatColor.BLUE}마${ChatColor.LIGHT_PURPLE}스 ${ChatColor.BLUE}한정판${ChatColor.WHITE}입니다!",
                "${ChatColor.WHITE}2024 Christmas Limited Edition"
            )
            firework.itemMeta = it
        }

        firework.addUnsafeEnchantment(Enchantment.MENDING, 1)
        firework.addUnsafeEnchantment(Enchantment.DURABILITY, 3)

        return firework
    }
}
