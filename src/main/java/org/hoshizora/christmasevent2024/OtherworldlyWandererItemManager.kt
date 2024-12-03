package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import net.md_5.bungee.api.ChatColor

object OtherworldlyWandererItemManager {

    fun createOtherworldlyWanderer(): ItemStack {
        val hoe = ItemStack(Material.NETHERITE_HOE)
        val meta: ItemMeta? = hoe.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&l&kL &r&9｡&f✧&9:&f˚&9*:&f･｡ &1&l이계의 &f&l방랑자 &r&9｡･&f:&9*&f˚&9:✧&f｡ &5&l&kL&f"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ${ChatColor.of("#3500ba")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}이${ChatColor.of("#370fcb")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}계${ChatColor.of("#3a1fdc")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}의 ${ChatColor.of("#3c2fed")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}워${ChatColor.BLUE}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}프${ChatColor.RESET}${ChatColor.WHITE}${ChatColor.BOLD} ⚝${ChatColor.YELLOW}${ChatColor.BOLD} RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.YELLOW}20 ${ChatColor.GRAY}경험치를 소모하여 범위 내의 가장",
                "${ChatColor.RESET}${ChatColor.GRAY}가까운 적의 뒤로 텔레포트한다.",
                "${ChatColor.GRAY}쿨다운: ${ChatColor.GREEN}15초",
                "",
                "${ChatColor.GOLD}패시브 스킬: ${ChatColor.DARK_PURPLE}${ChatColor.BOLD}Hit Combo",
                "${ChatColor.RESET}${ChatColor.GREEN}5초 ${ChatColor.GRAY}이내에 동일한 적을 ${ChatColor.YELLOW}3번 ${ChatColor.GRAY}이상 타격시,",
                "${ChatColor.RED}신속 1${ChatColor.GRAY}과 ${ChatColor.RED}힘 1${ChatColor.GRAY}효과를 ${ChatColor.GREEN}10초${ChatColor.GRAY}간 부여한다.",
                "${ChatColor.GREEN}15초 ${ChatColor.GRAY}이내 다른 타격이 없을 시 콤보는 ",
                "${ChatColor.GRAY}초기화된다."
            )
            hoe.itemMeta = it
        }

        hoe.addUnsafeEnchantment(Enchantment.MENDING, 1)
        hoe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 14)
        hoe.addUnsafeEnchantment(Enchantment.DURABILITY, 3)

        return hoe
    }
}
