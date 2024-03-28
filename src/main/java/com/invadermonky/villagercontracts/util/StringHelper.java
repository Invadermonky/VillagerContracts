package com.invadermonky.villagercontracts.util;

import com.invadermonky.villagercontracts.VillagerContracts;

public class StringHelper {
    public static String getLanguageKey(String unlocalizedStr, String type) {
        return String.format("%s.%s:%s", type, VillagerContracts.MOD_ID, unlocalizedStr);
    }
}
