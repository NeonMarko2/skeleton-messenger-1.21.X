package net.neonmarko2.skeletonmessenger.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.neonmarko2.skeletonmessenger.SkeletonMessenger;
import net.neonmarko2.skeletonmessenger.entity.custom.SkeletonMessengerEntity;

public class SkeletonMessengerModel<T extends SkeletonMessengerEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer SKELETON_MESSENGER = new EntityModelLayer(Identifier.of(SkeletonMessenger.MOD_ID, "skeleton_messenger"), "main");

    private final ModelPart Root;
    public SkeletonMessengerModel(ModelPart root) {
        this.Root = root.getChild("root");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(42, 40).cuboid(-1.0F, 1.0F, 0.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 2.0F));

        ModelPartData pelvis = root.addChild("pelvis", ModelPartBuilder.create().uv(24, 14).cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, 0.0F));

        ModelPartData left_leg = pelvis.addChild("left_leg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(38, 21).cuboid(-1.0F, 6.0F, -2.0F, 3.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 2.0F, -2.0F));

        ModelPartData right_leg = pelvis.addChild("right_leg", ModelPartBuilder.create().uv(0, 38).cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(28, 33).cuboid(-2.0F, 6.0F, -2.0F, 3.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 2.0F, -2.0F));

        ModelPartData upper_spine = root.addChild("upper_spine", ModelPartBuilder.create().uv(42, 31).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 1.0F));

        ModelPartData upperbody = upper_spine.addChild("upperbody", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -5.0F, 8.0F, 9.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, -1.0F));

        ModelPartData left_arm = upperbody.addChild("left_arm", ModelPartBuilder.create().uv(0, 26).cuboid(0.0F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F, new Dilation(0.0F))
                .uv(14, 33).cuboid(0.0F, 8.0F, -2.0F, 3.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, -2.0F));

        ModelPartData right_arm = upperbody.addChild("right_arm", ModelPartBuilder.create().uv(24, 21).cuboid(-3.0F, 0.0F, -2.0F, 3.0F, 8.0F, 4.0F, new Dilation(0.0F))
                .uv(26, 0).cuboid(-3.0F, 8.0F, -2.0F, 3.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.0F, -2.0F));

        ModelPartData head = upper_spine.addChild("head", ModelPartBuilder.create().uv(0, 14).cuboid(-3.0F, -6.0F, -5.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, -1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(SkeletonMessengerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        Root.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return Root;
    }
}
