package com.tdino48.dtafc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public final class DtafcConfig {
    public static final String FILE_NAME = "dtafc-common.toml";
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue COAST_REDWOOD_ENABLED;
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.comment("Worldgen options").push("worldgen");
        COAST_REDWOOD_ENABLED = BUILDER
                .comment("Whether coast redwood trees can spawn during world generation.")
                .define("coastRedwoodEnabled", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    private DtafcConfig() {
    }

    @SuppressWarnings("removal")
    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, FILE_NAME);
    }

    public static boolean isCoastRedwoodEnabled() {
        return COAST_REDWOOD_ENABLED.get();
    }
}
