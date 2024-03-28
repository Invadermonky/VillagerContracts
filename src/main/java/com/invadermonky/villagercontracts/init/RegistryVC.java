package com.invadermonky.villagercontracts.init;

import com.invadermonky.villagercontracts.VillagerContracts;
import com.invadermonky.villagercontracts.items.ItemVillagerContract;
import com.invadermonky.villagercontracts.util.LogHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = VillagerContracts.MOD_ID)
public class RegistryVC {
    public static Item villagerContract;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        villagerContract = new ItemVillagerContract("contract");
        registry.register(villagerContract);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemRenders(ModelRegistryEvent event) {
        ModelResourceLocation loc = new ModelResourceLocation(villagerContract.delegate.name(), "inventory");
        ModelLoader.setCustomModelResourceLocation(villagerContract, 0, loc);
        LogHelper.debug("Registered item renders.");
    }
}
