package com.tdino48.dtafc;

import com.dtteam.dynamictrees.api.registry.RegistryHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(dtafc.MOD_ID)
public class dtafc {
    public static final String MOD_ID = "dtafc";

    public dtafc(final IEventBus eventBus, final ModContainer modContainer) {
        DtafcConfig.register(modContainer);
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        ModItems.register(eventBus);
        RegistryHandler.get(MOD_ID);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }
}