package net.neonmarko2.skeletonmessenger;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.neonmarko2.skeletonmessenger.component.ModDataComponentTypes;
import net.neonmarko2.skeletonmessenger.entity.ModEntities;
import net.neonmarko2.skeletonmessenger.entity.custom.SkeletonMessengerEntity;
import net.neonmarko2.skeletonmessenger.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkeletonMessenger implements ModInitializer {
	public static final String MOD_ID = "skeletonmessenger";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModEntities.registerModEntities();
		ModDataComponentTypes.registerDataComponents();
		FabricDefaultAttributeRegistry.register(ModEntities.SKELETON_MESSENGER, SkeletonMessengerEntity.createAttributes());
	}
}