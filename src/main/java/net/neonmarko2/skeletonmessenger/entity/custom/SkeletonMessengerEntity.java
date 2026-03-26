package net.neonmarko2.skeletonmessenger.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SkeletonMessengerEntity extends MobEntity{
    public SkeletonMessengerEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    private ItemStack itemToMail = ItemStack.EMPTY;
    public PlayerEntity caller;
    public PlayerEntity owner;

    ///  While making this mod i learnt that praying mantises eat their partners :O

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 99)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        var currentPlayerStack = player.getStackInHand(hand);
        if(!getWorld().isClient()) {
            if (ItemStack.areItemsEqual(itemToMail, ItemStack.EMPTY)) {
                ///if(player.getUuid().compareTo(owner.getUuid()) == 0){
                ///    return ActionResult.PASS;
                ///}
                if(player.getUuid().compareTo(caller.getUuid()) != 0){
                    player.damage(getWorld().getDamageSources().magic(), 1);
                    return ActionResult.FAIL;
                }
                itemToMail = player.getStackInHand(hand).copy();
                player.setStackInHand(hand, ItemStack.EMPTY);
                player.sendMessage(Text.literal("Gave the messenger " + currentPlayerStack.getName().toString()));
                setPosition(owner.getX(), owner.getY(), owner.getZ());
            } else {
                if(player.getUuid().compareTo(owner.getUuid()) != 0){
                    player.damage(getWorld().getDamageSources().magic(), 1);
                    return ActionResult.FAIL;
                }
                player.getInventory().insertStack(itemToMail);
                discard();
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 300f));
    }
}
