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
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(85, 85).cuboid(-2.0F, -3.0F, 0.0F, 4.0F, 13.0F, 3.0F, new Dilation(-0.002F)), ModelTransform.pivot(0.0F, -3.0F, 2.0F));

        ModelPartData pelvis = root.addChild("pelvis", ModelPartBuilder.create().uv(48, 28).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 14.0F, 0.0F));

        ModelPartData left_leg = pelvis.addChild("left_leg", ModelPartBuilder.create().uv(83, 2).cuboid(-2.0F, 0.0F, -3.0F, 5.0F, 15.0F, 6.0F, new Dilation(-0.001F)), ModelTransform.of(4.0F, -2.0F, -4.0F, -0.4069F, -0.6562F, -0.2129F));

        ModelPartData lower_leg = left_leg.addChild("lower_leg", ModelPartBuilder.create().uv(79, 44).cuboid(-2.0F, 0.0F, -3.0F, 5.0F, 15.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 15.0F, 0.0F, 2.1398F, 0.0736F, 0.047F));

        ModelPartData right_leg = pelvis.addChild("right_leg", ModelPartBuilder.create().uv(3, 78).cuboid(-3.0F, 0.0F, -3.0F, 5.0F, 15.0F, 6.0F, new Dilation(-0.001F)), ModelTransform.of(-4.0F, -2.0F, -4.0F, -1.5005F, 0.618F, 0.1608F));

        ModelPartData r_lower_leg2 = right_leg.addChild("r_lower_leg2", ModelPartBuilder.create().uv(59, 68).cuboid(-3.0F, 0.0F, -3.0F, 5.0F, 15.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 15.0F, 0.0F, 1.5274F, 0.0421F, 0.0113F));

        ModelPartData upper_spine = root.addChild("upper_spine", ModelPartBuilder.create().uv(85, 67).cuboid(-2.0F, -17.0F, -2.0F, 4.0F, 16.0F, 3.0F, new Dilation(-0.001F)), ModelTransform.of(0.0F, -2.0F, 2.0F, 0.3473F, -0.0283F, 0.1278F));

        ModelPartData upperbody = upper_spine.addChild("upperbody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -12.0F, -2.0F));

        ModelPartData ribcage_r1 = upperbody.addChild("ribcage_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -12.0F, -6.0F, 16.0F, 18.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 12.0F, -4.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData left_arm = upperbody.addChild("left_arm", ModelPartBuilder.create().uv(2, 54).cuboid(0.0F, 0.0F, -3.0F, 5.0F, 16.0F, 6.0F, new Dilation(-0.002F)), ModelTransform.of(8.0F, 0.0F, -4.0F, -0.4702F, -0.1001F, -0.1942F));

        ModelPartData left_lower_arm = left_arm.addChild("left_lower_arm", ModelPartBuilder.create().uv(30, 68).cuboid(-2.0F, 0.0F, -3.0F, 5.0F, 16.0F, 6.0F, new Dilation(-0.001F)), ModelTransform.of(2.0F, 16.0F, 0.0F, -0.2746F, 0.0741F, -0.036F));

        ModelPartData right_arm = upperbody.addChild("right_arm", ModelPartBuilder.create().uv(51, 44).cuboid(-5.0F, 0.0F, -3.0F, 5.0F, 16.0F, 6.0F, new Dilation(-0.001F)), ModelTransform.of(-8.0F, 0.0F, -4.0F, -0.3927F, 0.0F, 0.0F));

        ModelPartData head = upper_spine.addChild("head", ModelPartBuilder.create().uv(0, 28).cuboid(-6.0F, -14.0F, -10.0F, 12.0F, 11.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 100).cuboid(-3.0F, -3.0F, -10.0F, 6.0F, 2.0F, 9.0F, new Dilation(0.001F))
                .uv(31, 93).cuboid(1.0F, -10.0F, -10.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(31, 93).cuboid(-5.0F, -10.0F, -10.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -14.0F, -2.0F, -0.0873F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
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
