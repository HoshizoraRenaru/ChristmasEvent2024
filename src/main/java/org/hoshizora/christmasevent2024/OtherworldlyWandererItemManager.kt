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
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&l&kL &r&f➶&9-͙&f˚&9 ༘&f✶ &1&l이계의 &f&l방랑자 &r&9｡･:*&f˚&9:✧&f｡ &5&l&kL&f"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ${ChatColor.RED}${ChatColor.BOLD}이계의 워프 ${ChatColor.YELLOW}${ChatColor.BOLD}RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.YELLOW}20 ${ChatColor.GRAY}경험치를 소모하여 범위 내의 가장",
                "${ChatColor.RESET}${ChatColor.GRAY}가까운 적의 뒤로 텔레포트한다.",
                "${ChatColor.GRAY}쿨다운: ${ChatColor.GREEN}15초",
                "",
                "${ChatColor.GOLD}액티브 스킬: ${ChatColor.RED}${ChatColor.BOLD}Hit Combo",
                "${ChatColor.RESET}${ChatColor.YELLOW}5초 이내에 동일한 적을 3번 이상 때릴 시,",
                "${ChatColor.GRAY}신속 1과 공격력 증가 1 효과를 10초간 부여합니다."
            )
            hoe.itemMeta = it
        }

        // 인챈트 추가
        hoe.addUnsafeEnchantment(Enchantment.MENDING, 1)
        hoe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10)
        hoe.addUnsafeEnchantment(Enchantment.DURABILITY, 3)

        return hoe
    }
}
