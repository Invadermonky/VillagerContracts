package com.invadermonky.villagercontracts;

import com.invadermonky.villagercontracts.handlers.ConfigHandler;
import com.invadermonky.villagercontracts.proxy.CommonProxy;
import com.invadermonky.villagercontracts.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = VillagerContracts.MOD_ID,
        name = VillagerContracts.MOD_NAME,
        version = VillagerContracts.MOD_VERSION,
        acceptedMinecraftVersions = VillagerContracts.MC_VERSION
)
public class VillagerContracts {
    public static final String MOD_ID = "villagercontracts";
    public static final String MOD_NAME = "Villager Contracts";
    public static final String MOD_VERSION = "1.12.2-1.2.0";
    public static final String MC_VERSION = "[1.12.2]";

    public static final String ProxyClientClass = "com.invadermonky.villagercontracts.proxy.ClientProxy";
    public static final String ProxyServerClass = "com.invadermonky.villagercontracts.proxy.CommonProxy";

    @Mod.Instance(MOD_ID)
    public static VillagerContracts INSTANCE;

    @SidedProxy(clientSide = ProxyClientClass, serverSide = ProxyServerClass)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Starting " + MOD_NAME);
        proxy.preInit(event);
        LogHelper.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        LogHelper.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        ConfigHandler.ConfigChangeListener.syncConfigValues();
        LogHelper.debug("Finished postInit phase.");
    }
}
