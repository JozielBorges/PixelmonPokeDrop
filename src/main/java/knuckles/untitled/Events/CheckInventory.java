package knuckles.untitled.Events;

import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
public class CheckInventory {
    private static long currentTickTime = 0;
    private static final long TICKS_PER_1_SECONDS = 1;

    private static HashSet<PlayerEntity> players = new HashSet<>();

    private boolean hasCoin = false;

    // To be honest, im new to these stuffs, I coded one plugin using spigot so this should work like the plugins do?
    // IDK if forge can get the player list like spigot
    // OBS: me uga uga no search only gpt 🧔🏽‍♀️🦴🔥🛖🦕
    @SubscribeEvent
    public void OnPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        players.add(event.getPlayer());
    }
    @SubscribeEvent
    public void OnPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
        players.remove(event.getPlayer());
    }

    // My man(GPT) gave me the idea to use this event that uses the minecraft Tick thing,
    // i trie
    // to get some event when the player clicks in the item
    // or opens the inventory but because of the server side-only problem,
    // I can't, maybe YOU can do it better :)
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            currentTickTime++;
            if(currentTickTime >= TICKS_PER_1_SECONDS){
                currentTickTime = 0;
                checkInventory();
            }
        }
    }
    private void addCoin(PlayerEntity player, int value){
        //Maybe you want to change this string below if you are not a portuguese speaker.
        String message = "§6Você ganhou $" +value ;
        ITextComponent msg = new StringTextComponent(message);
        player.sendMessage(msg,player.getUUID());
        BankAccountProxy.getBankAccount((ServerPlayerEntity) player).get().add(value);
    }

    // IF YOU CHANGE THE STRING AT LINE 53 AND 68, YOU SHOULD LOOK AFTER THIS!
    private int getValueFromName(ItemStack itemStack){
        return Integer.parseInt(itemStack.getDisplayName().getString().replace("[§6$","").replace(" PokeCoin]",""));
    }
    private void checkInventory() {
        for(PlayerEntity player : players){
            // Maybe player.inventory.items.contains() should be a better approach?
            for (ItemStack itemStack : player.inventory.items) {
                // The only way I found to get the "Custom" coin from the pokemon screen after the player click to get it/get from the ground.
                if(itemStack.getDisplayName().getString().contains("PokeCoin") && itemStack.getDisplayName().getString().contains("$")) {
                    String registry = itemStack.getItem().getRegistryName().toString();
                    if(registry.equals("pixelmon:relic_gold") || registry.equals("pixelmon:relic_silver") || registry.equals("pixelmon:relic_copper")){
                        addCoin(player, getValueFromName(itemStack));
                        hasCoin = true;
                        player.inventory.removeItem(itemStack);
                        break;
                    }
                }
            }
            //Imagine looping the entire inventory, and the last item is the coin, and then... you jump of it by this super cool system?
            if (hasCoin){
                hasCoin = false;
                break;
            }
        }
    }
}
