# No Wasting Horse Food

Stop rideable animals from deleting food when already full. Fixes a 2 year old 
[bug](https://bugs.mojang.com/browse/MC-233276) submitted in 2021. Mojang forgot to add a check to see if the animal ate the
food before removing the item. This mod fixes that. This should fix all animals
that inherit from `AbstractHorseEntity`, including modded animals. Provided they
don't have their own fix, in which case the item would be duplicated.