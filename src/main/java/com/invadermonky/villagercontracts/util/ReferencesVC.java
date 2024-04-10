package com.invadermonky.villagercontracts.util;

public class ReferencesVC {
    public static final String[] defaultContracts = new String[] {
            "Farmer=minecraft:farmer;farmer",
            "Fisherman=minecraft:farmer;fisherman",
            "Shepherd=minecraft:farmer;shepherd",
            "Fletcher=minecraft:farmer;fletcher",
            "Librarian=minecraft:librarian;librarian",
            "Cartographer=minecraft:librarian;cartographer",
            "Cleric=minecraft:priest;cleric",
            "Armorer=minecraft:smith;armor",
            "Weapon Smith=minecraft:smith;weapon",
            "Tool Smith=minecraft:smith;tool",
            "Butcher=minecraft:butcher;butcher",
            "Leatherworker=minecraft:butcher;leather",
            "Homer=minecraft:nitwit;nitwit"
    };
    public static final String[] defaultBlacklist = new String[] {
            "iceandfire:snowvillager",
            "primitivemobs:sheepman",
            "rats:plague_doctor",
            "toroquest:toroquest_toro_villager"
    };

    public static final String dumpVillagerInfoComment = "Prints the profession and career names of all registered villagers" +
            " to the log. This dump will occur on game restart and after the configuration has been changed in-game.";
    public static final String generateVillagerAttemptsComment = "Due to some Minecraft jank, villagers always generate " +
            "with random careers. This value controls the number of attempts the mod will make when attempting to spawn the correct career.";
    public static final String validContractsComment =
            "List of valid contract names and their associated villager careers.\n" +
            "Format:  name=profession;career\n" +
            "  name - The name used when renaming the villager contract to specify the desired career. These names are case-insensitive but must be unique, though multiple names can be assigned for the same villager career.\n" +
            "  profession - The villager profession resource location. Can be found using the \"dumpVillagerInfo\" config option.\n" +
            "  career - The villager career name. Career must be associated with the profession and can be found using the \"dumpVillagerInfo\" config option.\n\n" +
            "NOTE:\n" +
            "  Modded villagers that are not registered with Forge's VillagerProfession registry will not work with these contracts.\n" +
            "  Villagers with custom models may generate their correct trades, but will default to the farmer texture.";
    public static final String entityBlacklistComment = "Blacklist of any villager entity ids where contract interactions should be disabled.";
}
