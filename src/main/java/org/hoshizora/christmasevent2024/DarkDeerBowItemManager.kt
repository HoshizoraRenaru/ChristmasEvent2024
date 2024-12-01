package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import net.md_5.bungee.api.ChatColor

object DarkDeerBowItemManager {

    fun createDarkDeerBow(): ItemStack {
        val bow = ItemStack(Material.BOW)
        val meta: ItemMeta? = bow.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&l&kL &r&9｡&f✧&9:&f˚&9*:&f･｡ &1&l검은 순록 &f&l활 &r&9｡･&f:&9*&f˚&9:✧&f｡ &5&l&kL&f"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ${ChatColor.of("#2a02be")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}S${ChatColor.of("#2d0ac7")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}h${ChatColor.of("#3013d0")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}o${ChatColor.of("#331cd9")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}r${ChatColor.of("#3624e2")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}t${ChatColor.of("#392deb")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}b${ChatColor.of("#3c36f4")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}o${ChatColor.of("blue")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}w${ChatColor.RESET}${ChatColor.WHITE}${ChatColor.BOLD} ⚝${ChatColor.YELLOW}${ChatColor.BOLD} LEFT CLICK",
                "${ChatColor.RESET}${ChatColor.YELLOW}5 ${ChatColor.GRAY}경험치를 소모하여 화살을 즉시 발사한다.",
                "${ChatColor.GRAY}쿨다운: ${ChatColor.GREEN}0.5초",
            )
            bow.itemMeta = it
        }

        // 인챈트 추가
        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 5)
        bow.addUnsafeEnchantment(Enchantment.DURABILITY, 3)
        bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1)

        return bow
    }
}
