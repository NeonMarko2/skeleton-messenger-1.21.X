package net.neonmarko2.skeletonmessenger.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.neonmarko2.skeletonmessenger.SkeletonMessenger;

public class ModItems {
    public static final Item SKELETON_WHISTLE = registerItem("skeleton_whistle", new Item(new Item.Settings()));
    public static final Item SKELETON_WHISTLE_OMNI = registerItem("skeleton_whistle_omni", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(SkeletonMessenger.MOD_ID, name), item);
    }

    public static void registerModItems(){
        SkeletonMessenger.LOGGER.info("Registering mod items for " + SkeletonMessenger.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(SKELETON_WHISTLE);
            fabricItemGroupEntries.add(SKELETON_WHISTLE_OMNI);
        });
    }
}
