package org.hoshizora.christmasevent2024

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object NewtonItemManager {

    fun createNewton(): ItemStack {
        val sword = ItemStack(Material.DIAMOND_SWORD)
        val meta: ItemMeta? = sword.itemMeta

        meta?.let {
            it.setDisplayName("${ChatColor.GOLD}${ChatColor.BOLD}${ChatColor.MAGIC}H ${ChatColor.of("#f7007f")}N${ChatColor.of("#d21b94")}e${ChatColor.of("#ae36a9")}w${ChatColor.of("#8951bf")}t${ChatColor.of("#656cd4")}o${ChatColor.of("#4087e9")}n ${ChatColor.of("#1ca2ff")}☄ ${ChatColor.GOLD}${ChatColor.BOLD}${ChatColor.MAGIC}S")
            it.lore = listOf(
                "",
                "${ChatColor.of("#F7007F")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}S${ChatColor.of("#D31B94")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}p${ChatColor.of("#AE36AA")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#8A51BF")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}c${ChatColor.of("#656CD4")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}t${ChatColor.of("#4187EA")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}o${ChatColor.of("#1CA2FF")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}r${ChatColor.WHITE}${ChatColor.BOLD} 스킬: ${ChatColor.of("#1ca2ff")}${ChatColor.BOLD}すいせい☄${ChatColor.YELLOW}${ChatColor.BOLD} RIGHT CLICK",
                "${ChatColor.DARK_GRAY}스이세이(彗星)/혜성",
                "${ChatColor.RESET}${ChatColor.YELLOW}200 ${ChatColor.GRAY}경험치와 ${ChatColor.YELLOW}${ChatColor.BOLD}スターの${ChatColor.of("#1ca2ff")}${ChatColor.BOLD}原石 ${ChatColor.GRAY}1개를 소모하여 ",
                "${ChatColor.GREEN}10블록${ChatColor.GRAY} 앞으로 텔레포트 한다. ${ChatColor.GRAY}이후 범위 내의 ",
                "${ChatColor.GRAY}적에게 최대 ${ChatColor.RED}25 ${ChatColor.GRAY}데미지를 가한다. 또한 ${ChatColor.GREEN}5초 ",
                "${ChatColor.GRAY}동안 ${ChatColor.RED}흡수 ${ChatColor.GRAY}쉴드를 적용 후 남은 쉴드의 양 ",
                "${ChatColor.GRAY}만큼 체력을 회복한다.",
                "",
                "${ChatColor.WHITE}Season 1 ${ChatColor.of("#61E0EC")}hololive ${ChatColor.WHITE}Limited Edition"
            )
            it.addEnchant(Enchantment.MENDING, 1, true)
            it.isUnbreakable = true
            sword.itemMeta = it
        }

        return sword
    }
}
