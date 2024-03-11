package knuckles.untitled;

import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;
import com.pixelmonmod.tcg.duel.trainer.Switch;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;

import static knuckles.untitled.PixelmonPokeDrop.LOGGER;
import static knuckles.untitled.PixelmonPokeDrop.byPlayer;

public class CheckInventory {
    private static long currentTickTime = 0;
    private static final long TICKS_PER_1_SECONDS = 1;

    private static HashSet<PlayerEntity> players = new HashSet<>();

    private boolean hasCoin = false;

     //Method to check player's inventory and remove a certain item

    @SubscribeEvent
    public void OnPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        players.add(event.getPlayer());
    }
    @SubscribeEvent
    public void OnPlayerJoin(PlayerEvent.PlayerLoggedOutEvent event){
        players.remove(event.getPlayer());
    }
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            currentTickTime++;
            if(currentTickTime >= TICKS_PER_1_SECONDS){
                currentTickTime = 0;
                checkInventory();
            }
            // Iterate through online players
//            for (ServerPlayerEntity player : byPlayer.getPlayerEntities()) {
//                checkInventory(player);
//                if (hasCoin){
//                    hasCoin = false;
//                    byPlayer.removePlayerEntities(player);
//                }
//            }
        }
    }
    //byPlayer.addPlayerEntities(event.player);
    //BankAccountProxy.getBankAccount(event.player).get().add(number);

    //String message ="§6 Você ganhou $" +number ;
    //ITextComponent msg = new StringTextComponent(message);

    //event.player.sendMessage(msg,event.player.getUUID());

    private void addCoin(PlayerEntity player, int value){
        //LOGGER.info("Value :" +value);
        String message = "§6Você ganhou $" +value ;
        ITextComponent msg = new StringTextComponent(message);
        player.sendMessage(msg,player.getUUID());
        BankAccountProxy.getBankAccount((ServerPlayerEntity) player).get().add(value);
    }

    private int getValueFromName(ItemStack itemStack){
        return Integer.parseInt(itemStack.getDisplayName().getString().replace("[§6$","").replace(" PokeCoin]",""));
    }
    private void checkInventory() {
        int value = 0;
        for(PlayerEntity player : players){
            for (ItemStack itemStack : player.inventory.items) {
                Item item = itemStack.getItem();

                if(itemStack.getDisplayName().getString().contains("PokeCoin") && itemStack.getDisplayName().getString().contains("$")) {
                    if (item == ForgeRegistries.ITEMS.getValue(new ResourceLocation("pixelmon", "relic_gold"))) {
                        addCoin(player, getValueFromName(itemStack));
                        hasCoin = true;
                        player.inventory.removeItem(itemStack);
                        break;
                    } else if (item == ForgeRegistries.ITEMS.getValue(new ResourceLocation("pixelmon", "relic_silver"))) {
                        addCoin(player, getValueFromName(itemStack));
                        hasCoin = true;
                        player.inventory.removeItem(itemStack);
                        break;
                    } else if (item == ForgeRegistries.ITEMS.getValue(new ResourceLocation("pixelmon", "relic_copper"))) {
                        addCoin(player, getValueFromName(itemStack));
                        hasCoin = true;
                        player.inventory.removeItem(itemStack);
                        break;
                    }
                }
            }
            if (hasCoin){
                hasCoin = false;
                break;
            }
        }
    }
}
