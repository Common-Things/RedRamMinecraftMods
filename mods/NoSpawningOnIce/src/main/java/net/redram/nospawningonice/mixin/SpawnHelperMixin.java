package net.redram.nospawningonice.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

// I tried to make this fast by avoiding allocations
// didnt do any benchmarking at all
@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {

    // I hate Java. Why is any of this nessessary at all?? Why cant Inject use its
    // eyes and see the arguments below and use that???

    /**
     * Disable spawning on Packed Ice and Blue Ice in The Nether and The End
     */
    @Inject(method = "canSpawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;Lnet/minecraft/util/math/BlockPos$Mutable;D)Z", at = @At("RETURN"), cancellable = true)
    private static void noSpawningOnIceMixin_canSpawn(
        ServerWorld world, SpawnGroup group, StructureAccessor structureAccessor,
        ChunkGenerator chunkGenerator, SpawnSettings.SpawnEntry spawnEntry, BlockPos.Mutable pos,
        double squaredDistance, CallbackInfoReturnable<Boolean> callbackInfo) {

        // Idk why theres getRegistryKey(), getDimension(), and getDimensionType()
        // random internet person said to use getRegistryKey().
        // Avoiding toString() cuz 99% sure it allocates.
        String dim_name = world.getRegistryKey().getValue().getPath();

        if ((dim_name == "the_nether" || dim_name == "the_end")) {
            // explicitly check nether and end instead of not overworld to minimize
            // potential conflicts/undesired disabling with niche mods

            if (callbackInfo.getReturnValue()) {
                // try to minimize looking up block in world cuz prob slow idk
                // also getBlockNameBelow() 90% sure allocates

                // I hate switches and explcit breaks
                String block = getBlockNameBelow(world, pos);
                switch (block) {
                    // cringe case fall thru
                    // case "minecraft:ice": // hostile mobs already dont spawn on this
                    // case "minecraft:frosted_ice": // hostile mobs already dont spawn on this
                    case "minecraft:blue_ice":
                    case "minecraft:packed_ice":
                        // disable all spawning on ice
                        // "Hold on bro I gotta heap allocate a boolean." - Java's infinite wisdom
                        callbackInfo.setReturnValue(false);
                        break;
                    default:
                        // whatever Minecraft decides
                        break;
                }
            }
        }
    }

    private static String getBlockNameBelow(ServerWorld world, BlockPos.Mutable pos) {
        Block block = world.getBlockState(pos.down()).getBlock();
        return Registries.BLOCK.getId(block).toString();
    }

}