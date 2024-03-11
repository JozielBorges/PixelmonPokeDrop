package knuckles.untitled;

import com.pixelmonmod.pixelmon.Pixelmon;
import knuckles.untitled.Config.ConfigClass;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pixelmonpokedrop")

public class PixelmonPokeDrop {

    // Directly reference a log4j logger.
    static final Logger LOGGER = LogManager.getLogger();
    static final PokemonKilledByPlayer byPlayer = new PokemonKilledByPlayer();

    public PixelmonPokeDrop() {
        // Register ourselves for server and other game events we are interested in
        Pixelmon.EVENT_BUS.register(new ForgeEvent());
        MinecraftForge.EVENT_BUS.register(new CheckInventory());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,ConfigClass.SPEC,"./PokeDrop/Configs.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,ConfigClass.SPEC_COIN,"./PokeDrop/BossCoins.toml");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
}
