package knuckles.untitled;

import com.pixelmonmod.pixelmon.Pixelmon;
import knuckles.untitled.Config.ConfigClass;
import knuckles.untitled.Events.CheckInventory;
import knuckles.untitled.Events.ForgeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pixelmonpokedrop")
public class PixelmonPokeDrop {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public PixelmonPokeDrop() {
        // Register ourselves for server and other game events we are interested in
        Pixelmon.EVENT_BUS.register(new ForgeEvent());
        MinecraftForge.EVENT_BUS.register(new CheckInventory());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,ConfigClass.SPEC,"./PokeDrop/Configs.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,ConfigClass.SPEC_COIN,"./PokeDrop/BossCoins.toml");
    }
}
