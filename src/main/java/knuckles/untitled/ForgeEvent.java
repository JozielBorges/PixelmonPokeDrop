package knuckles.untitled;

import com.pixelmonmod.pixelmon.api.events.DropEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBase;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonProxy;
import com.pixelmonmod.pixelmon.api.pokemon.boss.BossTier;
import com.pixelmonmod.pixelmon.api.pokemon.boss.BossTiers;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import knuckles.untitled.Config.ConfigClass;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;
import java.util.Random;

import static knuckles.untitled.Config.ConfigClass.*;
import static knuckles.untitled.PixelmonPokeDrop.LOGGER;

public class ForgeEvent {
    //gold 300-500
    //silver 120-299
    //bronze 40-119
    Item coin;
    ItemStack stack;

    @SubscribeEvent
    public void dropCoinOnPokeKill(DropEvent event){
        if(!event.isPokemon()) return;

        Random random = new Random();
        int number = random.nextInt(1, maxCoinDrop.get());

        if(amuletCoinMutiplyer.get()){
            //LOGGER.info("Coin before amulet :" + number);
            number = hasPlayerAmuletCoin(event.player,number);
        }
        //PixelmonEntity['Pikachu'/28, l='ServerLevel[world]', x=182.54, y=66.08, z=230.13]

        PixelmonEntity pokemon = (PixelmonEntity) event.entity;



        number = switch (pokemon.getBossTier().getID()) {
            case BossTiers.COMMON ->   (int) (200+number + (number * bossTierCommonMP.get()));
            case BossTiers.UNCOMMON -> (int) (300+number + (number * bossTierUncommonMP.get()));
            case BossTiers.RARE ->     (int) (400+number + (number * bossTierRareMP.get()));
            case BossTiers.EPIC ->     (int) (500+number + (number * bossTierEpicMP.get()));
            case BossTiers.LEGENDARY ->(int) (600+number + (number * bossTierLegendaryMP.get()));
            case BossTiers.ULTIMATE -> (int) (700+number + (number * bossTierUltimateMP.get()));
            case BossTiers.HAUNTED ->  (int) (800+number + (number * bossTierHauntedMP.get()));
            case BossTiers.DROWNED ->  (int) (900+number + (number * bossTierDrownedMP.get()));
            default -> number;
        };
//        LOGGER.info("Coin after boss : "+pokemon.getBossTier().getID()+ " amulet :" + number);
//
//
//        LOGGER.info("is shiny : " + pokemon.getPokemon().isShiny());
//        LOGGER.info("Pokemon : " + pokemon.getPokemon());




        Item coin = getCoinType(number);
        stack = new ItemStack(coin);
        stack.setHoverName(new StringTextComponent("§6$" + number + " PokeCoin"));
        event.addDrop(stack);
        //byPlayer.addPlayerEntities(event.player);
        //BankAccountProxy.getBankAccount(event.player).get().add(number);

        //String message ="§6 Você ganhou $" +number ;
        //ITextComponent msg = new StringTextComponent(message);

        //event.player.sendMessage(msg,event.player.getUUID());
    }

    private int hasPlayerAmuletCoin(ServerPlayerEntity player,int value){
        for(ItemStack item : player.inventory.items){
            if(item.getItem().getRegistryName().toString().equals("pixelmon:amulet_coin")){
                //LOGGER.info("Value without mutiplication : " +value);
                value = (int) (value+ (value*amuletCoinMutiplyerValue.get()));
                //LOGGER.info("Value with mutiplication : " +value);
//                if(doDamageToAmulet.get()){
//                    LOGGER.info("DMG before: " +item.getDamageValue());
//                    item.setDamageValue(item.getDamageValue() +1);
//                    LOGGER.info("DMG after: " +item.getDamageValue());
//                }
            }
        }
        return value;
    }

    private Item getCoinType(int number){
        if (number < 300){ // silver and bronze
            if(number > 119){// silver
                coin = ForgeRegistries.ITEMS.getValue(new ResourceLocation("pixelmon","relic_silver"));
            }else{ //bronze
                coin = ForgeRegistries.ITEMS.getValue(new ResourceLocation("pixelmon","relic_copper"));
            }
        }else{//gold
            coin = ForgeRegistries.ITEMS.getValue(new ResourceLocation("pixelmon","relic_gold"));
        }
        return coin;
    }
}
