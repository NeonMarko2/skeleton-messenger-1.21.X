package net.neonmarko2.skeletonmessenger.entity.client;


import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.neonmarko2.skeletonmessenger.SkeletonMessenger;
import net.neonmarko2.skeletonmessenger.entity.custom.SkeletonMessengerEntity;

public class SkeletonMessengerRenderer extends MobEntityRenderer<SkeletonMessengerEntity, SkeletonMessengerModel<SkeletonMessengerEntity>> {

    public SkeletonMessengerRenderer(EntityRendererFactory.Context context) {
        super(context, new SkeletonMessengerModel<>(context.getPart(SkeletonMessengerModel.SKELETON_MESSENGER)), 1.0f);
    }

    @Override
    public Identifier getTexture(SkeletonMessengerEntity entity) {
        return Identifier.of(SkeletonMessenger.MOD_ID, "textures/entity/skeleton_messenger/skeleton_messenger.png");
    }

    @Override
    public void render(SkeletonMessengerEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
