package cn.evolvefield.mods.botapi.config;

import cn.evolvefield.mods.botapi.util.json.JSONFormat;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static cn.evolvefield.mods.botapi.BotApi.CONFIG_FOLDER;


public class ConfigManger {
    private static final Gson GSON = new Gson();

    public static void initBotConfig() {
        BotConfig config = new BotConfig();

        if (!CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path configPath = CONFIG_FOLDER.resolve(config.getConfigName() + ".json");
        if (configPath.toFile().isFile()) {
            try {
                config = GSON.fromJson(FileUtils.readFileToString(configPath.toFile(), StandardCharsets.UTF_8),
                        BotConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileUtils.write(configPath.toFile(), GSON.toJson(config), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void saveBotConfig(BotConfig config) {
        if (!CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path configPath = CONFIG_FOLDER.resolve(config.getConfigName() + ".json");
        try {
            FileUtils.write(configPath.toFile(), JSONFormat.formatJson(GSON.toJson(config)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
