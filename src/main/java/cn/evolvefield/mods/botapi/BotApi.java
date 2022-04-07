package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.api.data.BindData;
import cn.evolvefield.mods.botapi.common.config.BotConfig;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.bot.BotHandler;
import cn.evolvefield.mods.botapi.core.service.MySqlService;
import cn.evolvefield.mods.botapi.core.service.WebSocketService;
import cn.evolvefield.mods.botapi.util.FileUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.sql.Connection;

@Mod(BotApi.MODID)
public class BotApi {

    public static final String MODID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger();
    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();
    public static Path CONFIG_FOLDER ;
    public static BotConfig config;
    public static Connection connection;

    public BotApi() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);

    }


    private void setup(final FMLCommonSetupEvent event) {
        CONFIG_FOLDER = FMLPaths.CONFIGDIR.get().resolve("botapi");
        FileUtil.checkFolder(CONFIG_FOLDER);
    }


    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event) {
        //加载配置
        config = ConfigManger.initBotConfig();
        //绑定数据加载
        BindData.init();
        //连接框架与数据库
        if (BotApi.config.getCommon().isEnable()) {
            BotHandler.init();
            if (BotApi.config.getCommon().isSQL_ENABLED()) {
                LOGGER.info("▌ §a开始连接数据库 §6┈━═☆");
                connection = MySqlService.Join();
            }

        }

    }

    @SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) {
        ConfigManger.saveBotConfig(config);
        BindData.save();
        if (WebSocketService.client != null) {
            WebSocketService.client.close();
        }
        LOGGER.info("▌ §c正在关闭群服互联 §a┈━═☆");
    }


    @SubscribeEvent
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        SERVER = event.getServer();
    }


}
