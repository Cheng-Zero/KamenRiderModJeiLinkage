package cheng.kamen_rider_mod_jei_linkage;

import cheng.kamen_rider_mod_jei_linkage.config.KamenriderModJeiLinkageConfig;
import cheng.kamen_rider_mod_jei_linkage.recipe.ModRecipeType;
import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

@Mod(KamenRiderModJeiLinkage.MODID)
public class KamenRiderModJeiLinkage {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "kamen_rider_mod_jei_linkage";

    public KamenRiderModJeiLinkage() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModRecipeType.RECIPE_TYPES.register(modEventBus);
        ModRecipeType.RECIPE_SERIALIZERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, KamenriderModJeiLinkageConfig.SPEC, "kamenrider_jei_linkage.toml");
        //游戏内重加载
        KamenriderModJeiLinkageConfig.loadConfig(KamenriderModJeiLinkageConfig.SPEC, FMLPaths.CONFIGDIR.get().resolve("kamenrider_jei_linkage.toml").toString());

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class d {
        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getPlayer() == null)
                return;
            if (KamenriderModJeiLinkageConfig.PLAYER_LOGIN_MESSAGE.get())
                if (!event.getPlayer().level.isClientSide())
                    event.getPlayer().displayClientMessage(new TextComponent(
                                    """
                                            歌查德JEI联动:目前只给骑士冒险整合包用，如果用了并且发了整合包或视频请在BiliBili@我\
                                            主页：https://space.bilibili.com/1553817658?spm_id_from=333.1369.0.0\
                                            B站UID 1553817658\
                                            若想关闭请到config文件夹的kamenrider_jei_linkage.toml文件中修改
                                            """)
                            , false);
        }
    }
}
