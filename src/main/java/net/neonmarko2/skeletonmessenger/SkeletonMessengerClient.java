package net.neonmarko2.skeletonmessenger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.neonmarko2.skeletonmessenger.entity.ModEntities;
import net.neonmarko2.skeletonmessenger.entity.client.SkeletonMessengerModel;
import net.neonmarko2.skeletonmessenger.entity.client.SkeletonMessengerRenderer;

public class SkeletonMessengerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(SkeletonMessengerModel.SKELETON_MESSENGER, SkeletonMessengerModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SKELETON_MESSENGER, SkeletonMessengerRenderer::new);
    }
}
