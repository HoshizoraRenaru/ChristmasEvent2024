package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import net.md_5.bungee.api.ChatColor

object EndOfFairytaleItemManager {

    fun createEndOfFairytale(): ItemStack {
        val book = ItemStack(Material.BOOK)
        val meta: ItemMeta? = book.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&l&kL &r&9｡&f✧&9:&f˚&9*:&f･｡ &d&l동화의 &5&l결말 &r&9｡･&f:&9*&f˚&9:✧&f｡ &f&l&kL&f"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ${ChatColor.of("#26008e")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}비${ChatColor.of("#29099e")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}비${ChatColor.of("#2d12ae")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}디${ChatColor.of("#301bbe")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}바${ChatColor.of("#3424ce")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}비${ChatColor.of("#372dde")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}디${ChatColor.of("#3b36ee")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}부${ChatColor.of("blue")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}!${ChatColor.RESET}${ChatColor.WHITE}${ChatColor.BOLD} ⚝${ChatColor.YELLOW}${ChatColor.BOLD} RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.YELLOW}30 ${ChatColor.GRAY}경험치를 소모하여 50% 확률로",
                "${ChatColor.RESET}${ChatColor.GREEN}버프${ChatColor.GRAY} 또는 ${ChatColor.RED}디버프${ChatColor.GRAY} 효과가 적용된다.",
                "${ChatColor.GRAY}쿨다운: ${ChatColor.GREEN}30초",
                "",
                "${ChatColor.WHITE}이 아이템은 ${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.MAGIC}L ${ChatColor.LIGHT_PURPLE}크${ChatColor.BLUE}리${ChatColor.LIGHT_PURPLE}스${ChatColor.BLUE}마${ChatColor.LIGHT_PURPLE}스 ${ChatColor.WHITE}랜${ChatColor.BLUE}덤${ChatColor.WHITE}박${ChatColor.BLUE}스 ${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.MAGIC}L",
                "${ChatColor.WHITE}에서 드롭되었습니다!",
                "${ChatColor.BOLD}${ChatColor.WHITE}이 아이템은 ${ChatColor.LIGHT_PURPLE}크${ChatColor.BLUE}리${ChatColor.LIGHT_PURPLE}스${ChatColor.BLUE}마${ChatColor.LIGHT_PURPLE}스 ${ChatColor.BLUE}한정판${ChatColor.WHITE}입니다!",
                "${ChatColor.WHITE}2024 Christmas Limited Edition"
            )
            it.addEnchant(Enchantment.MENDING, 1, true)
            it.isUnbreakable = true
            book.itemMeta = it
        }

        return book
    }
}
