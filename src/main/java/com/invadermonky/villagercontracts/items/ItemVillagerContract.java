package com.invadermonky.villagercontracts.items;

import com.invadermonky.villagercontracts.VillagerContracts;
import com.invadermonky.villagercontracts.handlers.ConfigHandler;
import com.invadermonky.villagercontracts.handlers.EventHandler;
import com.invadermonky.villagercontracts.util.StringHelper;
import com.invadermonky.villagercontracts.util.VillagerInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemVillagerContract extends Item {
    public ItemVillagerContract(String unlocName) {
        setRegistryName(unlocName);
        setCreativeTab(CreativeTabs.MISC);
        setTranslationKey(new ResourceLocation(VillagerContracts.MOD_ID, unlocName).toString());
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(!ConfigHandler.disableAnvilRenaming) {
            if (GuiScreen.isShiftKeyDown()) {
                tooltip.add(I18n.format(StringHelper.getLanguageKey("naming", "tooltip")));
                for (VillagerInfo info : EventHandler.contractMap.values()) {
                    tooltip.add("  - " + info.identifier);
                }
            } else {
                tooltip.add(I18n.format(StringHelper.getLanguageKey("desc", "tooltip")));
                tooltip.add(I18n.format(StringHelper.getLanguageKey("info", "tooltip")));
            }
        }
    }
}
