package com.tdino48.dtafc;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class DtafcConfig {
    public static final String FILE_NAME = "dtafc-common.toml";
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue COAST_REDWOOD_ENABLED;
    public static final ModConfigSpec SPEC;

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

    public static void register(final ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, SPEC, FILE_NAME);
    }

    public static boolean isCoastRedwoodEnabled() {
        return COAST_REDWOOD_ENABLED.get();
    }
}