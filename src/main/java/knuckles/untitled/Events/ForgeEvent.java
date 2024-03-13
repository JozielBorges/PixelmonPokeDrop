package knuckles.untitled.Events;

import com.pixelmonmod.pixelmon.api.events.DropEvent;
import com.pixelmonmod.pixelmon.api.pokemon.boss.BossTiers;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import knuckles.untitled.Item.AmuletCoin;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

import static knuckles.untitled.Config.ConfigClass.*;
import static knuckles.untitled.PixelmonPokeDrop.*;
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
            number = hasPlayerAmuletCoin(event.player,number);
        }
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

        Item coin = getCoinType(number);
        stack = new ItemStack(coin);
        stack.setHoverName(new StringTextComponent("§6$" + number + " PokeCoin"));
        event.addDrop(stack);
    }

    // BAD STUFF, DO NOT LOOK.
    // WHAT IS THIS? I thought that amulet coins should be in the player's hand/inventory to be used, but a friend told me that it is a held item, you put it in the pokemon "bag" slot...
    // It works btw, I hope.
    private int hasPlayerAmuletCoin(ServerPlayerEntity player,int value){
        //AmuletCoin newAmulet = null;
        for(ItemStack item : player.inventory.items){
            if(item.getItem().getRegistryName().toString().equals("pixelmon:amulet_coin")){

                value = (int) (value+ (value*amuletCoinMutiplyerValue.get()));
                // NOT WORKING AS PLANNED DO NOT USE doDamageToAmulet AS TRUE IN THE CONFIGS!
//                if(doDamageToAmulet.get()){
//                    newAmulet = verifyAmuletCoin(item);
//                    ItemStack newItem = newAmulet.getBaseItem();
//                    //int stack = item.getCount();
//                    player.inventory.removeItem(item);
//
//                    LOGGER.info("newAmulet.Health Before: " + newAmulet.Health);
//                    if(newAmulet.doDamageToItemModded(amountOfDamageToAmulet.get())){
//                        newItem.setCount(newItem.getCount() -1);
//                        newItem = newAmulet.addHelthToDisplay(newItem);
//                    }
//                    //item.setDamageValue(item.getDamageValue() + amountOfDamageToAmulet.get());
//                    LOGGER.info("newAmulet.Health After: " + newAmulet.Health);
//
//                    player.inventory.add(newItem);
//                    break;
//                }
            }
        }
        return value;
    }


    private AmuletCoin verifyAmuletCoin(ItemStack amulet){
        //LOGGER.info("Before amulet.setHoverName :" + amulet.getHoverName());
        AmuletCoin newamulet = null;
        //IFormattableTextComponent copy = amulet.getHoverName().copy();

        // BAD DESIGNER , I WILL CHANGE IT LATER (i think...)
        LOGGER.info(amulet.getHoverName().getString());
        if(!amulet.getHoverName().getString().contains("(HP: ")){
            LOGGER.info("ITEM HAS NOT HP");
            newamulet = new AmuletCoin(amulet,true);
        }else{
            LOGGER.info("ITEM HAS HP");
            newamulet = new AmuletCoin(amulet,false);
        }

        //copy.append(new StringTextComponent().withStyle(TextFormatting.GOLD));
        //amulet.setHoverName(copy);
        return newamulet;
    }
    // I guess you want to change the value, because of the boss type multiples LINE 39
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
