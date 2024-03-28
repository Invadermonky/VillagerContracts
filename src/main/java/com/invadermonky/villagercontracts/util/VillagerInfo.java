package com.invadermonky.villagercontracts.util;

import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class VillagerInfo {
    public final String identifier;
    public final VillagerProfession profession;
    public final VillagerCareer career;

    public VillagerInfo(String identifier, VillagerProfession profession, VillagerCareer career) {
        this.identifier = identifier;
        this.profession = profession;
        this.career = career;
    }
}
