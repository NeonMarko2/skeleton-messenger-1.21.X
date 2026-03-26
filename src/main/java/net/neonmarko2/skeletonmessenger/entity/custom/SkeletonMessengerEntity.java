package net.neonmarko2.skeletonmessenger.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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

    private final int vanishTime = 20*10; ///MAKE THIS CONFIGURABLE IN GAME AS WELL
    private int timerUntilVanish = vanishTime;
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
    public void tick() {
        if(!getWorld().isClient()) {
            timerUntilVanish -= 1;
            if (timerUntilVanish <= 0) {
                if (!itemToMail.isEmpty()) {
                    ItemEntity droppedItem = new ItemEntity(getWorld(), getX(), getY(), getZ(), itemToMail.copy());
                    getWorld().spawnEntity(droppedItem);
                }
                discard();
            }
        }
        super.tick();
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        var currentPlayerStack = player.getStackInHand(hand);
        if(!getWorld().isClient()) {
            if (itemToMail.isEmpty()) {
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
                teleport(owner.getX(), owner.getY(), owner.getZ(), true);
                timerUntilVanish = vanishTime;
                caller.removeStatusEffect(StatusEffects.BLINDNESS);
                owner.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20*1, 1));
            } else {
                if(player.getUuid().compareTo(owner.getUuid()) != 0){
                    player.damage(getWorld().getDamageSources().magic(), 1);
                    return ActionResult.FAIL;
                }
                player.getInventory().insertStack(itemToMail.copy());
                owner.removeStatusEffect(StatusEffects.BLINDNESS);
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
