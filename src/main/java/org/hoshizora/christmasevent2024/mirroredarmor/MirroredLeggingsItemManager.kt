package org.hoshizora.christmasevent2024.mirroredarmor

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import net.md_5.bungee.api.ChatColor
import org.bukkit.enchantments.Enchantment
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlot
import java.util.UUID

object MirroredLeggingsItemManager {
    fun create(): ItemStack {
        val leggings = ItemStack(Material.LEATHER_LEGGINGS)
        val meta = leggings.itemMeta as LeatherArmorMeta

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l&kL &r&1｡&f✧&1:&f˚&1*:&f･｡ &9&l거울상 &f&l레깅스 &r&1｡･&f:&1*&f˚&1:✧&f｡ &d&l&kL&f"))
        meta.lore = listOf(
            "",
            "${ChatColor.GOLD}효과:",
            "${ChatColor.AQUA}적의 True Damage를 1 감소"
        )
        meta.setColor(Color.fromRGB(79, 42, 201))

        // 방어력 설정 (네더라이트 레깅스와 동일)
        val armorModifier = AttributeModifier(UUID.randomUUID(), "generic.armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier)

        // 방어력 강도 설정
        val toughnessModifier = AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier)

        // 넉백 저항 설정
        val knockbackResistanceModifier = AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS)
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceModifier)

        leggings.itemMeta = meta

        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
        leggings.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4)
        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 10)
        leggings.addUnsafeEnchantment(Enchantment.MENDING, 1)
        leggings.addUnsafeEnchantment(Enchantment.SWIFT_SNEAK, 3)

        return leggings
    }
}
