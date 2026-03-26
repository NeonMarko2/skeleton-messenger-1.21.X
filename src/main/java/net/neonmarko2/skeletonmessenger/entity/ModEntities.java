package net.neonmarko2.skeletonmessenger.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.neonmarko2.skeletonmessenger.SkeletonMessenger;
import net.neonmarko2.skeletonmessenger.entity.custom.SkeletonMessengerEntity;

public class ModEntities {
    public static final EntityType<SkeletonMessengerEntity> SKELETON_MESSENGER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(SkeletonMessenger.MOD_ID, "skeleton_messenger"),
            EntityType.Builder.create(SkeletonMessengerEntity::new, SpawnGroup.CREATURE)
                    .dimensions(2f,2f).build());
    public static void registerModEntities(){
        SkeletonMessenger.LOGGER.info("Registering mod entities for " + SkeletonMessenger.MOD_ID);
    }
}
