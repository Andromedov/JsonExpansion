package me.andromedov.jsonExpantion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class JsonExpansion extends PlaceholderExpansion {
    private final Map<String, JsonObject> jsonCache = new HashMap<>();
    private final Map<String, Long> fileLastModified = new HashMap<>();

    private File jsonFolder;

    @Override
    public boolean canRegister() {
        File papiFolder = Bukkit.getPluginManager().getPlugin("PlaceholderAPI").getDataFolder();
        jsonFolder = new File(papiFolder, "json");

        if (!jsonFolder.exists()) {
            return jsonFolder.mkdirs();
        }
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "json";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ParadiseCraft";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        File targetFile = null;
        String jsonKeyPath = "";

        File[] files = jsonFolder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return "Error: No json folder";

        for (File file : files) {
            String fileName = file.getName();

            if (params.startsWith(fileName + "_")) {
                targetFile = file;
                jsonKeyPath = params.substring(fileName.length() + 1);
                break;
            }
        }

        if (targetFile == null) {
            return "File not found";
        }

        JsonObject jsonObject = getFileContent(targetFile);
        if (jsonObject == null) {
            return "Invalid JSON";
        }

        return getJsonValue(jsonObject, jsonKeyPath);
    }

    private JsonObject getFileContent(File file) {
        String fileName = file.getName();
        long currentModified = file.lastModified();

        if (jsonCache.containsKey(fileName) && fileLastModified.get(fileName) == currentModified) {
            return jsonCache.get(fileName);
        }

        try (FileReader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

            jsonCache.put(fileName, json);
            fileLastModified.put(fileName, currentModified);

            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getJsonValue(JsonObject json, String path) {
        try {
            String[] parts = path.split("\\.");
            JsonElement currentElement = json;

            for (String part : parts) {
                if (currentElement.isJsonObject()) {
                    currentElement = currentElement.getAsJsonObject().get(part);
                } else {
                    return "...";
                }

                if (currentElement == null) return "...";
            }

            if (currentElement.isJsonPrimitive()) {
                return currentElement.getAsString();
            } else {
                return currentElement.toString();
            }
        } catch (Exception e) {
            return "Error";
        }
    }
}