package org.hoshizora.customitems

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object CeremonialSwordItemManager {

    fun createRecyclingSword(): ItemStack {
        val sword = ItemStack(Material.WOODEN_SWORD)
        val meta: ItemMeta? = sword.itemMeta

        meta?.let {
            it.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&l제사용 검"))
            it.lore = listOf(
                "",
                "${ChatColor.GOLD}스킬: ",
            )
            sword.itemMeta = it
        }

        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3)
        sword.addUnsafeEnchantment(Enchantment.DURABILITY, 5)

        return sword
    }
}
