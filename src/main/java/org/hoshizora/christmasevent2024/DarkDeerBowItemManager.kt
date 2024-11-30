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
                "${ChatColor.GOLD}특징: ${ChatColor.of("#000000")}${ChatColor.BOLD}${ChatColor.ITALIC}좌클릭으로 화살 발사",
                "${ChatColor.RESET}${ChatColor.GRAY}인벤토리에서 화살 확인 후 발사.",
                "${ChatColor.RESET}${ChatColor.GRAY}화살 없으면 메세지 출력.",
                "${ChatColor.GOLD}인챈트 ${ChatColor.RED}힘 ${ChatColor.GRAY}레벨에 따라 데미지 증가.",
                "${ChatColor.RESET}${ChatColor.YELLOW}0.5초 간격으로 발사 가능."
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
