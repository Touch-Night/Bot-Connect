package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.common.config.BotConfig;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import cn.evolvefield.mods.botapi.core.service.MySqlService;
import cn.evolvefield.mods.botapi.util.FileUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("botapi")
public class BotApi {

    public static final String MODID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger();
    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();
    public static Path CONFIG_FOLDER ;
    public static BotConfig config ;
    public static Connection connection;

    public BotApi() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarted);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStopping);

    }

    private void setup(final FMLCommonSetupEvent event) {
        CONFIG_FOLDER = FMLPaths.CONFIGDIR.get().resolve("botapi");
        FileUtil.checkFolder(CONFIG_FOLDER);
        System.out.println("▌ §a开始连接数据库 §6┈━═☆");
        connection = MySqlService.Join();
        try {
            if (connection != null && !connection.isClosed()) {
                //System.out.println("§7[§a§l*§7] §a数据库检测");
                connection.createStatement().execute("SELECT 1");
            }
        } catch (SQLException e) {
            System.out.println("§7[§a§l*§7] §c未连接上数据库现为你重连");
            connection = MySqlService.Join();
        }
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {

    }

    private void onServerStarted(ServerStartedEvent event){
        //加载配置
        config = ConfigManger.initBotConfig();
        if (BotApi.config.getCommon().isEnable()) {
            ClientThreadService.runWebSocketClient();
        }

    }

    private void onServerStopping(ServerStoppingEvent event) {
        ConfigManger.saveBotConfig(config);
        ClientThreadService.stopWebSocketClient();
    }

    @SubscribeEvent
    public void init(ServerAboutToStartEvent event){
        SERVER = event.getServer();
    }
}
