package net.redram.enchantableshears.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShieldItem;

// these comments are a mess...

// NOTE: silktouch on shears is buggy
// it can be applied with anvil but with not enchantment table and not commands (wtf?)
// silktouch is also useless as all blocks shears break already drop its block
// it'll still work on grass, etc, tho
// wiki has mega wrong info on shears: https://minecraft.fandom.com/wiki/Enchanting_mechanics#Step_three_%E2%80%93_Select_a_set_of_enchantments_from_the_list

// Enchantment.isAcceptableItem() = allowed for anvils? I noticed EfficiencyEnchantment has override to allow shears
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    // Fragile. cir.setReturnValue(VALUE) breaks mc with "Failed to handle packet" and enchants dont show
    // clicking or clicking off and back shows them and works
    /**
     * Get list of possible enchantments of an item at an enchantment table.
     */
    @Inject(method = "getPossibleEntries", at = @At("RETURN"))
    private static void enchantableShearsMixin_EnchantmentHelper_getPossibleEntries(
        int power, ItemStack stack, boolean treasureAllowed,
        CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {

        // list of enchantments, and level
        List<EnchantmentLevelEntry> list = cir.getReturnValue(); // MUTATED LATER
        ArrayList<Enchantment> newEnchantments = new ArrayList<>(2);

        if ((stack.getItem() instanceof ShearsItem)) {
            newEnchantments.add(Enchantments.EFFICIENCY);
            newEnchantments.add(Enchantments.UNBREAKING);
            // newShearEnchants.add(Enchantments.SILK_TOUCH); // buggy/weird

        } else if ((stack.getItem() instanceof ShieldItem)) {
            newEnchantments.add(Enchantments.UNBREAKING);
        }

        // copied and adapted from EnchantmentHelper.getPossibleEntries()
        // I'm not even gonna pretend how this works
        block0: for (Enchantment enchantment : newEnchantments) {

            // limit max unbreaking level of shields to 2
            int max = enchantment.getMaxLevel();
            if ((stack.getItem() instanceof ShieldItem)
                && (enchantment == Enchantments.UNBREAKING)) {
                // sometimes still does 3 somehow. Which is maybe what I want?
                max = 2;
            }

            for (int i = max; i > enchantment.getMinLevel() - 1; --i) {
                if (power < enchantment.getMinPower(i) || power > enchantment.getMaxPower(i))
                    continue;
                list.add(new EnchantmentLevelEntry(enchantment, i));
                continue block0;
            }
        }
    }
}
