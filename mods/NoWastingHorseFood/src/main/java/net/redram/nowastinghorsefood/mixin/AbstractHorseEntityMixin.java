package net.redram.nowastinghorsefood.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;


@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin {
    
    // NOTE: does not fix mules still being able to eat golden carrots
    // NOTE: does not fix punch + golden carrot love heart spam (also happens with cows and dogs with wheat and meat)
    // NOTE: possible free heal when healing (maybe injured) donkey (prob others) after being tamed
    /**
     * Fix Horses, Donkey, Mules, and Llamas not checking if they actually ate the food
     * before removing the food item.
     */
    @Inject(method = "interactHorse", at = @At("Return"))
    public void NoWastingHorseFood_AbstractHorseEntity_interactHorse(
        PlayerEntity player, ItemStack stack,
        CallbackInfoReturnable<ActionResult> cir) {
        // Mojang is dumb and forgot to check if animal actually ate the food before decrementing
        // this checks if it didnt eat, then increments to undo that
        boolean ate = cir.getReturnValue() == ActionResult.SUCCESS;
        if (!ate && !player.getAbilities().creativeMode) {
            stack.increment(1);
        }
        // what Mojang should've wrote:
        //   boolean ate = this.receiveFood(player, stack);
        //   if (ate && !player.getAbilities().creativeMode) {
        //      stack.decrement(1);
        //   }
    }
}