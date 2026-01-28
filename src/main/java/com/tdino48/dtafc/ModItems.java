package com.tdino48.dtafc;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModItems {
    private static final String TFC_DEBARK_MODID = "tfc_debark";
    private static boolean barkItemsRegistered = false;

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, dtafc.MOD_ID);

    private ModItems() {
    }

    public static void register(final IEventBus bus) {
        if (ModList.get().isLoaded(TFC_DEBARK_MODID)) {
            registerBarkItems();
        }
        ITEMS.register(bus);
    }

    private static void registerBarkItems() {
        if (barkItemsRegistered) {
            return;
        }
        barkItemsRegistered = true;

        registerBarkPair("baobab");
        registerBarkPair("ipe");
        registerBarkPair("eucalyptus");
        registerBarkPair("mahogany");
        registerBarkPair("hevea");
        registerBarkPair("tualang");
        registerBarkPair("teak");
        registerBarkPair("cypress");
        registerBarkPair("fig");
        registerBarkPair("ironwood");
        registerBarkPair("black_oak");
        registerBarkPair("gum_arabic");
        registerBarkPair("poplar");
        registerBarkPair("rainbow_eucalyptus");
        registerBarkPair("redcedar");
    }

    private static void registerBarkPair(final String woodName) {
        ITEMS.register(woodName + "_bark", () -> new Item(new Item.Properties()));
        ITEMS.register(woodName + "_bark_powder", () -> new Item(new Item.Properties()));
    }
}
