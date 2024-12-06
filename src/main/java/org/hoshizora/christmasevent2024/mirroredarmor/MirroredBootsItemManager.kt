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

object MirroredBootsItemManager {
    fun create(): ItemStack {
        val boots = ItemStack(Material.LEATHER_BOOTS)
        val meta = boots.itemMeta as LeatherArmorMeta

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&l&kL &r&1｡&f✧&1:&f˚&1*:&f･｡ &9&l거울상 &f&l부츠 &r&1｡･&f:&1*&f˚&1:✧&f｡ &d&l&kL&f"))
        meta.lore = listOf(
            "",
            "${ChatColor.GOLD}풀 세트 보너스: ${ChatColor.of("#26008e")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}M${ChatColor.of("#29099e")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}i${ChatColor.of("#2d12ae")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}r${ChatColor.of("#301bbe")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}r${ChatColor.of("#3424ce")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}o${ChatColor.of("#372dde")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}r${ChatColor.of("#3b36ee")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("blue")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}r${ChatColor.RESET}${ChatColor.WHITE}${ChatColor.BOLD} ⚝",
            "${ChatColor.GRAY}최대 체력 8을 증가시킨다.",
            "${ChatColor.GRAY}또한 ${ChatColor.DARK_PURPLE}${ChatColor.BOLD}${ChatColor.MAGIC}L ${ChatColor.RESET}${ChatColor.BLUE}｡${ChatColor.WHITE}✧${ChatColor.BLUE}:${ChatColor.WHITE}˚${ChatColor.BLUE}*:${ChatColor.WHITE}･｡ ${ChatColor.DARK_BLUE}${ChatColor.BOLD}반사된 ${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}크${ChatColor.BLUE}${ChatColor.BOLD}리${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}스${ChatColor.BLUE}${ChatColor.BOLD}마${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}스 ${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}파괴자 ${ChatColor.RESET}${ChatColor.BLUE}｡･${ChatColor.WHITE}:${ChatColor.BLUE}*${ChatColor.WHITE}˚${ChatColor.BLUE}:✧${ChatColor.WHITE}｡ ${ChatColor.DARK_PURPLE}${ChatColor.BOLD}${ChatColor.MAGIC}L${ChatColor.WHITE}의 ",
            "${ChatColor.GRAY}쿨다운을 10초 감소시킨다.",
            "",
            "${ChatColor.GOLD}2/4 세트 보너스: ${ChatColor.of("#26008e")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}T${ChatColor.of("#280699")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}r${ChatColor.of("#2b0ca4")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}u${ChatColor.of("#2d12af")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e ${ChatColor.of("#3019ba")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}D${ChatColor.of("#321fc6")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#3525d1")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}f${ChatColor.of("#372cdc")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.of("#3a32e7")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}n${ChatColor.of("#3c38f2")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}c${ChatColor.of("blue")}${ChatColor.BOLD}${ChatColor.ITALIC}${ChatColor.UNDERLINE}e${ChatColor.RESET}${ChatColor.WHITE}${ChatColor.BOLD} ⚝",
            "${ChatColor.GRAY}받는 모든 트루 데미지를 20% 감소시킨다.",
            "",
            "${ChatColor.WHITE}이 아이템은 ${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.MAGIC}L ${ChatColor.LIGHT_PURPLE}크${ChatColor.BLUE}리${ChatColor.LIGHT_PURPLE}스${ChatColor.BLUE}마${ChatColor.LIGHT_PURPLE}스 ${ChatColor.WHITE}랜${ChatColor.BLUE}덤${ChatColor.WHITE}박${ChatColor.BLUE}스 ${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.MAGIC}L",
            "${ChatColor.WHITE}에서 드롭되었습니다!",
            "${ChatColor.BOLD}${ChatColor.WHITE}이 아이템은 ${ChatColor.LIGHT_PURPLE}크${ChatColor.BLUE}리${ChatColor.LIGHT_PURPLE}스${ChatColor.BLUE}마${ChatColor.LIGHT_PURPLE}스 ${ChatColor.BLUE}한정판${ChatColor.WHITE}입니다!",
            "${ChatColor.WHITE}2024 Christmas Limited Edition"
        )
        meta.setColor(Color.fromRGB(174, 0, 255))

        val armorModifier = AttributeModifier(UUID.randomUUID(), "generic.armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier)

        val toughnessModifier = AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET)
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier)

        val knockbackResistanceModifier = AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET)
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceModifier)

        meta.isUnbreakable = true
        boots.itemMeta = meta

        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10)
        boots.addUnsafeEnchantment(Enchantment.MENDING, 1)
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5)
        boots.addUnsafeEnchantment(Enchantment.SOUL_SPEED, 3)

        return boots
    }
}
