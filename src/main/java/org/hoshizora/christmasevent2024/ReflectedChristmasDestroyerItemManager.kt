package org.hoshizora.christmasevent2024

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import net.md_5.bungee.api.ChatColor

object ReflectedChristmasDestroyerItemManager {

    fun createMirroredChristmasDestroyer(): ItemStack {
        val sword = ItemStack(Material.NETHERITE_SWORD)
        val meta: ItemMeta? = sword.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&l&kL &r&9｡&f✧&9:&f˚&9*:&f･｡ &1&l반사된 &d&l크&9&l리&d&l스&9&l마&d&l스 &d&l파괴자 &r&9｡･&f:&9*&f˚&9:✧&f｡ &5&l&kL&f"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ${ChatColor.of("#2a00ad")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}R${ChatColor.of("#2c07b6")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#2e0ebf")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}f${ChatColor.of("#3115c8")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}l${ChatColor.of("#331cd1")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#3523da")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}c${ChatColor.of("#382ae3")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}t${ChatColor.of("#3a31ec")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}i${ChatColor.of("#3c38f5")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}o${ChatColor.of("#0000ff")}${ChatColor.BLUE}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}n${ChatColor.WHITE}${ChatColor.BOLD} ⚝ ${ChatColor.YELLOW}${ChatColor.BOLD}RIGHT CLICK",
                "${ChatColor.RESET}${ChatColor.YELLOW}30 ${ChatColor.GRAY}경험치를 소모하여 ${ChatColor.GREEN}10초${ChatColor.GRAY}간 반사 쉴드를",
                "${ChatColor.RESET}${ChatColor.GRAY}적용한다. 쉴드 상태에서 받은 근접 공격 피해를",
                "${ChatColor.RESET}${ChatColor.GRAY}공격자에게 반사한다. 1회 반사 후 쉴드는 파괴된다.",
                "${ChatColor.GRAY}쿨다운: ${ChatColor.GREEN}15초",
                ""
            )
            sword.itemMeta = it
        }

        // 인챈트 추가
        sword.addUnsafeEnchantment(Enchantment.MENDING, 1)
        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8)
        sword.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 3)
        sword.addUnsafeEnchantment(Enchantment.DURABILITY, 3)
        sword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 3)

        return sword
    }
}
