# No Wasting Horse Food

Stop Horses, Donkeys, Mules, Camels, Llamas, and Trader Llamas from deleting food when
already full health or in love mode. Fixes a 2 year old [bug](https://bugs.mojang.com/browse/MC-233276)
submitted in 2021. Mojang forgot to add a check to see if the animal ate the
food before removing the item. This mod fixes that. This should fix all animals
that inherit from `AbstractHorseEntity`, including modded animals. Provided they
don't have their own fix, in which case the item would be duplicated.

## Notes
- Does not fix mules being able to eat golden carrots and showing love particles despite not breeding
- Does not fix punching and using golden carrots showing love particles again
    - also happens with cows and dogs with wheat and meat, and other animals
- Pig are unaffected, they already don't waste food