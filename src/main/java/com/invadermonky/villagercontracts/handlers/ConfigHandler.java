package com.invadermonky.villagercontracts.handlers;

import com.invadermonky.villagercontracts.VillagerContracts;
import com.invadermonky.villagercontracts.util.LogHelper;
import com.invadermonky.villagercontracts.util.ReferencesVC;
import com.invadermonky.villagercontracts.util.VillagerHelper;
import com.invadermonky.villagercontracts.util.VillagerInfo;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Config(modid = VillagerContracts.MOD_ID)
public class ConfigHandler {
    @Comment(ReferencesVC.dumpVillagerInfoComment)
    public static boolean dumpVillagerInfo = false;

    @Comment(ReferencesVC.generateVillagerAttemptsComment)
    @RangeInt(min = 1, max = 100)
    public static int generateVillagerAttempts = 20;

    @LangKey("config." + VillagerContracts.MOD_ID + ":validcontracts")
    @Comment(ReferencesVC.validContractsComment)
    public static String[] validContracts = ReferencesVC.defaultContracts;

    @Comment(ReferencesVC.entityBlacklistComment)
    public static String[] entityBlacklist = ReferencesVC.defaultBlacklist;

    @Mod.EventBusSubscriber(modid = VillagerContracts.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(VillagerContracts.MOD_ID)) {
                ConfigManager.sync(VillagerContracts.MOD_ID, Type.INSTANCE);
                syncConfigValues();
            }
        }

        public static void syncConfigValues() {
            if(dumpVillagerInfo) {
                dumpVillagerInfo();
            }

            InteractHandler.entityBlacklist.clear();
            InteractHandler.entityBlacklist.addAll(Arrays.asList(entityBlacklist));

            InteractHandler.contractMap.clear();
            for(String configStr : validContracts) {
                parseConfiguredVillager(configStr);
            }
        }

        public static void dumpVillagerInfo() {
            LogHelper.info("Villager Info Dump: STARTING");

            for(VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS) {
                String professionName = VillagerHelper.getProfessionName(profession);
                List<VillagerCareer> careers = VillagerHelper.getProfessionCareers(profession);

                if(careers == null)
                    continue;

                for(VillagerCareer career : careers) {
                    String careerName = VillagerHelper.getCareerName(career);
                    LogHelper.info("\tProfession: " + professionName + ", Career: " + careerName);
                    LogHelper.info("\t\t=" + professionName + ";" + careerName);
                }
            }

            LogHelper.info("Villager Info Dump: FINISHED");
        }

        public static void parseConfiguredVillager(String configStr) {
            Pattern pattern = Pattern.compile("^(.+?)=(.+?);(.+)$");
            Matcher matcher = pattern.matcher(configStr);
            if(!matcher.matches()) {
                LogHelper.error("Invalid villager contract string: " + configStr);
            } else {
                String identifier = matcher.group(1);
                String professionName = matcher.group(2);
                String careerName = matcher.group(3);

                if(!VillagerHelper.doesProfessionExist(professionName)) {
                    LogHelper.error("Invalid villager profession: " + configStr);
                } else if(!VillagerHelper.doesVillagerExist(professionName, careerName)) {
                    LogHelper.error("Invalid villager career: " + configStr);
                } else {
                    VillagerProfession profession = VillagerHelper.getProfession(professionName);
                    VillagerCareer career = VillagerHelper.getCareer(profession, careerName);
                    VillagerInfo info = new VillagerInfo(identifier, profession, career);
                    InteractHandler.contractMap.put(identifier.toLowerCase(Locale.ROOT), info);
                }
            }

        }
    }
}
