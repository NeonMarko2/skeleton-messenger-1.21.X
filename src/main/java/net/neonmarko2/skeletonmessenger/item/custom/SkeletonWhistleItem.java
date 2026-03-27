package net.neonmarko2.skeletonmessenger.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.neonmarko2.skeletonmessenger.component.ModDataComponentTypes;
import net.neonmarko2.skeletonmessenger.entity.ModEntities;
import net.neonmarko2.skeletonmessenger.entity.custom.SkeletonMessengerEntity;

import java.util.List;

public class SkeletonWhistleItem extends Item {
    public SkeletonWhistleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(!world.isClient()){
            var player_uuid = itemStack.get(ModDataComponentTypes.OWNER_UUID);
            if(player_uuid == null){
                world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_CREEPER_HURT, SoundCategory.PLAYERS);
                itemStack.set(ModDataComponentTypes.OWNER_UUID, user.getUuid());
                itemStack.set(ModDataComponentTypes.OWNER_NAME, user.getName().getString());
                user.sendMessage(Text.translatable("skeletonmessenger.popup.firstuse"), true);
            }else{
                if(world.getServer().getPlayerManager().getPlayer(player_uuid) == null){
                    user.sendMessage(Text.translatable("skeletonmessenger.popup.offline"), true);
                    return TypedActionResult.fail(itemStack);
                }
                var whistle_owner = world.getPlayerByUuid(player_uuid);
                SkeletonMessengerEntity skeletonMessenger = new SkeletonMessengerEntity(ModEntities.SKELETON_MESSENGER, world);
                var playerFacing = user.getEyePos().add(user.getRotationVec(1.0f).multiply(2));
                var skeletonSpawnPosition = new Vec3d(playerFacing.x, user.getY(), playerFacing.z);
                skeletonMessenger.setPosition(skeletonSpawnPosition);
                skeletonMessenger.FaceEntityInstantly(user);
                skeletonMessenger.caller = user;
                skeletonMessenger.owner = whistle_owner;
                world.spawnEntity(skeletonMessenger);
                world.playSound(null, whistle_owner.getBlockPos(), SoundEvents.AMBIENT_CAVE.value(), SoundCategory.PLAYERS, 1, 1);
                whistle_owner.sendMessage(Text.translatable("skeletonmessenger.popup.summoned"), true);
                user.getItemCooldownManager().set(this, 20*5); /// MAKE THIS CONFIGURABLE IN GAME
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20*5, 1));
            }
        }
        return TypedActionResult.success(itemStack, false);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.get(ModDataComponentTypes.OWNER_UUID) != null){
            if (stack.get(ModDataComponentTypes.OWNER_NAME) == null){
                tooltip.add(Text.translatable("tooltip.skeletonmessenger.skeleton_whistle.name_fetch_fail"));
            }else
                tooltip.add(Text.literal(stack.get(ModDataComponentTypes.OWNER_NAME)));
        }else{
            tooltip.add(Text.translatable("tooltip.skeletonmessenger.skeleton_whistle.unbound"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
