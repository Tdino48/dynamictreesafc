package com.tdino48.dtafc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = dtafc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DtafcPackEvents {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COAST_REDWOOD_PACK = "dtafc-disable-coast-redwood";
    private static final String LEGACY_CONFIG_FILE = "dtafc-commmon.toml";

    private DtafcPackEvents() {
    }

    @SubscribeEvent
    public static void addPackFinders(final AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }
        if (!isCoastRedwoodEnabled()) {
            addBuiltInPack(event, COAST_REDWOOD_PACK, "DTAFC: Disable Coast Redwood");
        }
    }

    private static boolean isCoastRedwoodEnabled() {
        try {
            Path configPath = resolveConfigPath();
            if (configPath != null) {
                try (CommentedFileConfig config = CommentedFileConfig.builder(configPath).build()) {
                    config.load();
                    // Try different possible key formats
                    Object value = config.get("worldgen.coastRedwoodEnabled");
                    if (value instanceof Boolean) {
                        return (Boolean) value;
                    }

                    Object section = config.get("worldgen");
                    if (section instanceof Config) {
                        Object nested = ((Config) section).get("coastRedwoodEnabled");
                        if (nested instanceof Boolean) return (Boolean) nested;
                    }

                    value = config.get("coastRedwoodEnabled");
                    if (value instanceof Boolean) return (Boolean) value;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("DTAFC: Could not read config file manually: {}", e.getMessage());
        }

        try {
            return DtafcConfig.isCoastRedwoodEnabled();
        } catch (Exception e) {
            // If Forge config isn't ready yet, default to true (enabled)
            return true;
        }
    }

    private static Path resolveConfigPath() {
        Path legacyPath = FMLPaths.CONFIGDIR.get().resolve(LEGACY_CONFIG_FILE);
        Path configPath = FMLPaths.CONFIGDIR.get().resolve(DtafcConfig.FILE_NAME);
        boolean hasLegacy = Files.exists(legacyPath);
        boolean hasConfig = Files.exists(configPath);
        if (hasLegacy && hasConfig) {
            try {
                return Files.getLastModifiedTime(configPath).compareTo(Files.getLastModifiedTime(legacyPath)) >= 0
                        ? configPath
                        : legacyPath;
            } catch (IOException ignored) {
                return configPath;
            }
        }
        if (hasLegacy) {
            return legacyPath;
        }
        if (hasConfig) {
            return configPath;
        }
        return null;
    }


    private static void addBuiltInPack(final AddPackFindersEvent event, final String packId, final String title) {
        var modFileInfo = ModList.get().getModFileById(dtafc.MOD_ID);
        if (modFileInfo == null) {
            return;
        }

        Path packPath = modFileInfo.getFile().findResource("resourcepacks/" + packId);
        Pack pack = Pack.readMetaAndCreate(
                packId,
                Component.literal(title),
                true,
                name -> new PathPackResources(name, packPath, true),
                PackType.SERVER_DATA,
                Pack.Position.TOP,
                PackSource.BUILT_IN
        );
        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }
}
