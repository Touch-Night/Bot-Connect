package cn.evolvefield.mods.botapi;

import cn.evolvefield.mods.botapi.common.config.BotConfig;
import cn.evolvefield.mods.botapi.common.config.ConfigManger;
import cn.evolvefield.mods.botapi.core.service.ClientThreadService;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

@Mod("botapi")
public class BotApi {

    public static final String MODID = "botapi";
    public static final Logger LOGGER = LogManager.getLogger();
    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();
    public static Path CONFIG_FOLDER ;
    public static BotConfig config ;

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
        CONFIG_FOLDER = FMLPaths.CONFIGDIR.get();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {

    }

    private void onServerStarted(FMLServerStartedEvent event){
        //加载配置
        config = ConfigManger.initBotConfig();
        //启动自启
        if (BotApi.config.getCommon().isEnable()) {
            ClientThreadService.runWebSocketClient();
        }

    }

    private void onServerStopping(FMLServerStoppingEvent event) {
        ConfigManger.saveBotConfig(config);
        ClientThreadService.stopWebSocketClient();
    }


    @SubscribeEvent
    public void init(FMLServerAboutToStartEvent event){
        SERVER = event.getServer();
    }


}
