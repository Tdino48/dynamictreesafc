package com.tdino48.dtafc;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final String TFC_DEBARK_MODID = "tfc_debark";
    private static boolean barkItemsRegistered = false;

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(dtafc.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, dtafc.MOD_ID);

    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DTAFC_TAB = CREATIVE_TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.dtafc"))
                    .icon(() -> {
                        Item seed = BuiltInRegistries.ITEM.get(
                                ResourceLocation.fromNamespaceAndPath(dtafc.MOD_ID, "baobab_seed"));
                        return new ItemStack(seed != null && seed != Items.AIR ? seed : Items.OAK_SAPLING);
                    })
                    .displayItems((params, output) ->
                            BuiltInRegistries.ITEM.entrySet().stream()
                                    .filter(e -> e.getKey().location().getNamespace().equals(dtafc.MOD_ID))
                                    .filter(e -> !e.getKey().location().getPath().endsWith("_branch"))
                                    .forEach(e -> output.accept(e.getValue())))
                    .build());

    private ModItems() {
    }

    public static void register(final IEventBus bus) {
        if (ModList.get().isLoaded(TFC_DEBARK_MODID)) {
            registerBarkItems();
        }
        ITEMS.register(bus);
        CREATIVE_TABS.register(bus);
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