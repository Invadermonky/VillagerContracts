package com.invadermonky.villagercontracts.handlers;

import com.invadermonky.villagercontracts.init.RegistryVC;
import com.invadermonky.villagercontracts.util.VillagerHelper;
import com.invadermonky.villagercontracts.util.VillagerInfo;
import gnu.trove.set.hash.THashSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Locale;
import java.util.TreeMap;

public class InteractHandler {
    public static final InteractHandler INSTANCE = new InteractHandler();

    public static TreeMap<String,VillagerInfo> contractMap = new TreeMap<>();
    public static THashSet<String> entityBlacklist = new THashSet<>();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onVillagerInteract(EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack heldItem = event.getItemStack();
        Entity target = event.getTarget();
        World world = event.getWorld();

        if(world.isRemote || target == null || heldItem.isEmpty())
            return;

        if(target instanceof EntityVillager && !((EntityVillager) target).isChild() && !entityBlacklist.contains(target.getEntityString()) &&
                heldItem.getItem() == RegistryVC.villagerContract) {
            String contractName = heldItem.getDisplayName().toLowerCase(Locale.ROOT);

            if(contractMap.containsKey(contractName)) {
                VillagerInfo villagerInfo = contractMap.get(contractName);
                EntityVillager villager = VillagerHelper.createVillager(villagerInfo.profession, villagerInfo.career, world);
                villager.setPositionAndRotation(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
                villager.rotationYawHead = target.getRotationYawHead();
                target.setDead();
                world.spawnEntity(villager);
                villager.setNoAI(((EntityVillager) target).isAIDisabled());
                if(target.hasCustomName()) {
                    villager.setCustomNameTag(target.getCustomNameTag());
                    villager.setAlwaysRenderNameTag(target.getAlwaysRenderNameTag());
                }
                villager.playSound(SoundEvents.ENTITY_VILLAGER_YES, 1.0f, 1.0f);

                player.swingArm(event.getHand());
                if(!player.isCreative()) {
                    heldItem.shrink(1);
                }
            } else {
                target.playSound(SoundEvents.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
            event.setCanceled(true);
        }
    }
}
