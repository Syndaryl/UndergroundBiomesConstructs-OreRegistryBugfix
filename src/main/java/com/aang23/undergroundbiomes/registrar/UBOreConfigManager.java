package com.aang23.undergroundbiomes.registrar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.aang23.undergroundbiomes.api.event.UBRegistrarRegisterOresEvent;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.ForgeRegistries;

// TODO Maybe move to some kind of UBOreConfig class? 
public class UBOreConfigManager {
    // Folders
    public static File mainFolder = new File(UBOreRegistrar.mcdir, "config/undergroundbiomes");
    public static File jsonsFolder = new File(mainFolder, "ores");
    public static File overlayFolder = new File(mainFolder, "overlays");

    // Caches so that the PackGenerator can use those datas
    public static Map<String, String> overlayCache = new HashMap<String, String>();
    public static Map<String, String> stoneVariantCache = new HashMap<String, String>();
    public static Map<String, String> nameCache = new HashMap<String, String>(); // TODO improve
    public static Map<String, Boolean> alphaBlendingCache = new HashMap<String, Boolean>();

    public static void setupConfigs() {
        if (!mainFolder.exists()) {
            mainFolder.mkdirs();
            extractDefaults();
        }
        readJsons();
    }

    private static void registerOre(Block ore, String overlay, String variant, boolean useAlphaBlending) {
        overlayCache.put(ore.getRegistryName().toString(), overlay);
        stoneVariantCache.put(ore.getRegistryName().toString(), variant);
        alphaBlendingCache.put(ore.getRegistryName().toString(), useAlphaBlending);
        UBOreRegistrar.registerOre(ore);
    }

    private static void readJsons() {
        for (File currentJson : jsonsFolder.listFiles()) {
            JSONObject currentConfig = null;
            Object configobj = null;
            try {
                configobj = new JSONParser().parse(new FileReader(currentJson));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            currentConfig = (JSONObject) configobj;

            for (Object currentOreObj : currentConfig.keySet()) {
                String currentOre = (String) currentOreObj;

                // Check that the ore we are registering actually exists
                if (ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(currentOre))) {
                    Block toRegister = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(currentOre));
                    Map oreConfig = new HashMap<>();
                    oreConfig = (Map) currentConfig.get(currentOre);

                    String overlay = (String) oreConfig.get("overlay");
                    String variant = (String) oreConfig.get("variant");
                    String name = (String) oreConfig.get("name"); // TODO improve
                    boolean useAlphaBlending = oreConfig.containsKey("alphaBlend")
                            ? (boolean) oreConfig.get("alphaBlend")
                            : false;

                    nameCache.put(currentOre, name);

                    registerOre(toRegister, overlay, variant, useAlphaBlending);
                }
            }

            // Other mods can register ores in this event
            MinecraftForge.EVENT_BUS.post(new UBRegistrarRegisterOresEvent());
        }
    }

    private static void extractDefaults() {
        try {
            File defaultsZip = new File("defaults.zip");
            InputStream link = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(defaultsZip.getName());
            Files.copy(link, defaultsZip.getAbsoluteFile().toPath());
            ZipFile zipFile = new ZipFile(defaultsZip);
            zipFile.extractAll(mainFolder.getAbsolutePath());
            defaultsZip.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}