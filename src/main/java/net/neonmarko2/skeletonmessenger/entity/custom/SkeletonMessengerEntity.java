package net.neonmarko2.skeletonmessenger.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class SkeletonMessengerEntity extends MobEntity{
    public SkeletonMessengerEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        goalSelector.clear(null);
        targetSelector.clear(null);
    }

    private final int vanishTime = 20*100; ///MAKE THIS CONFIGURABLE IN GAME AS WELL
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
            if (timerUntilVanish <= 0 || owner == null) {
                if (!itemToMail.isEmpty()) {
                    ItemEntity droppedItem = new ItemEntity(getWorld(), getX(), getY(), getZ(), itemToMail.copy());
                    getWorld().spawnEntity(droppedItem);
                }
                discard();
                return;
            }
        }
        super.tick();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        if (!itemToMail.isEmpty()){
            nbt.put("MailItem", itemToMail.encode(getRegistryManager()));
        }
        if(owner != null){
            nbt.putUuid("Owner", owner.getUuid());
        }
        if(caller != null){
            nbt.putUuid("Caller", caller.getUuid()); /// CALLER AND OR OWNER NOT WORKING PROPERLY
        }
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if(Objects.requireNonNull(getServer()).getPlayerManager().getPlayer(nbt.getUuid("Owner")) != null){
            this.owner = getServer().getPlayerManager().getPlayer(nbt.getUuid("Owner"));
            System.out.println("Owner is on the server");
        }
        if(getServer().getPlayerManager().getPlayer(nbt.getUuid("Caller")) != null){
            this.caller = getServer().getPlayerManager().getPlayer(nbt.getUuid("Caller"));
            System.out.println("Caller is on the server");
        }
        itemToMail = ItemStack.fromNbt(getRegistryManager(), nbt.get("MailItem")).orElse(ItemStack.EMPTY);
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    public void FaceEntityInstantly(Entity entity){
        if(!getWorld().isClient){
            var directionVec = entity.getPos().subtract(getPos());
            var yaw = (float) MathHelper.atan2(directionVec.z, directionVec.x) * (180/(float)Math.PI) - 90;
            refreshPositionAndAngles(getPos(), yaw, 1);
            setBodyYaw(yaw);
            setHeadYaw(yaw);
            setYaw(yaw);
        }
    }

    public void SpawnInfrontOf(Entity entity){
        var playerFacing = entity.getEyePos().add(entity.getRotationVec(1.0f).multiply(2));
        var skeletonSpawnPosition = new Vec3d(playerFacing.x, entity.getY(), playerFacing.z);
        setPosition(skeletonSpawnPosition);
        FaceEntityInstantly(entity);
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
                player.sendMessage(Text.literal("Gave the messenger " + currentPlayerStack.getName().getString()));
                SpawnInfrontOf(owner);
                FaceEntityInstantly(owner);
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
}
