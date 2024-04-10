package com.invadermonky.villagercontracts.util;

import com.invadermonky.villagercontracts.handlers.ConfigHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;

public class VillagerHelper {

    public static boolean doesVillagerExist(String professionName, String careerName) {
        return doesProfessionExist(professionName) && getCareer(getProfession(professionName), careerName) != null;
    }

    public static boolean doesProfessionExist(String professionName) {
        return getProfession(professionName) != null;
    }

    @Nullable
    public static VillagerProfession getProfession(String professionName) {
        for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS) {
            if(getProfessionName(profession).equalsIgnoreCase(professionName)) {
                return profession;
            }
        }
        return null;
    }

    public static String getProfessionName(VillagerProfession profession) {
        try {
            Field nameField = profession.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            return ((ResourceLocation) nameField.get(profession)).toString();
        } catch (Exception e) {
            LogHelper.error("Failed to retrieve profession name for profession: " + profession.getRegistryName().toString());
            return "";
        }
    }

    @Nullable
    public static List<VillagerCareer> getProfessionCareers(VillagerProfession profession) {
        try {
            Field careersField = profession.getClass().getDeclaredField("careers");
            careersField.setAccessible(true);
            return (List<VillagerCareer>) careersField.get(profession);
        } catch (Exception e) {
            LogHelper.error("Error retrieving careers for villager profession: " + getProfessionName(profession));
            return null;
        }
    }

    @Nullable
    public static VillagerCareer getCareer(VillagerProfession profession, String careerName) {
        List<VillagerCareer> careers = getProfessionCareers(profession);
        if(careers != null) {
            for (VillagerCareer career : careers) {
                if (getCareerName(career).equalsIgnoreCase(careerName)) {
                    return career;
                }
            }
        }
        return null;
    }

    public static String getCareerName(VillagerCareer career) {
        return career.getName();
    }

    public static int getCareerId(VillagerCareer career) {
        try {
            Field careerIdField = career.getClass().getDeclaredField("id");
            careerIdField.setAccessible(true);
            return (int) careerIdField.get(career);
        } catch (Exception e) {
            LogHelper.error("Error retrieving career id for career: " + getCareerName(career));
            return 0;
        }
    }

    public static EntityVillager createVillager(@Nullable VillagerProfession profession, @Nullable VillagerCareer career, World world) {
        if(profession != null && career != null) {
            EntityVillager villager;
            int loopControl = 0;
            int careerId = getCareerId(career) + 1;

            while(loopControl < ConfigHandler.generateVillagerAttempts) {
                villager = new EntityVillager(world);
                villager.setProfession(profession);
                villager.populateBuyingList();

                if(villager.careerId == careerId) {
                    return villager;
                } else {
                    loopControl++;
                    villager.setDead();
                }
            }
        }
        return new EntityVillager(world);
    }
}
