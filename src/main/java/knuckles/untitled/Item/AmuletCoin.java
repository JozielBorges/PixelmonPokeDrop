package knuckles.untitled.Item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static knuckles.untitled.PixelmonPokeDrop.LOGGER;

public class AmuletCoin {
    private static IFormattableTextComponent originalName =null;
    public static ItemStack ITEM_STACK = null;
    public int Health= 0;

    public AmuletCoin(ItemStack item, boolean vanilla) {
        Item base = ForgeRegistries.ITEMS.getValue(new ResourceLocation("pixelmon", "amulet_coin"));
        ITEM_STACK = new ItemStack(base,item.getCount());
        originalName = ITEM_STACK.getHoverName().copy();
        if(vanilla){
            LOGGER.info("VAnilla ");
            Health = 100;
            addHelthToDisplay(ITEM_STACK);
        }else{
            LOGGER.info("Not vanilla");
            getHealthFromDisplay(ITEM_STACK);
        }
    }

    public ItemStack addHelthToDisplay(ItemStack item){
        IFormattableTextComponent copy = item.getHoverName().copy();
        copy.append(new StringTextComponent(" (HP: "+String.valueOf(Health)+")").withStyle(TextFormatting.GOLD));
        item.setHoverName(copy);
        return item;
    }

    private void getHealthFromDisplay(ItemStack item){
        Pattern pattern = Pattern.compile("\\(HP: (\\d+)\\)");
        IFormattableTextComponent copy = item.getHoverName().copy();

        LOGGER.info(copy.toString());

        Matcher matcher = pattern.matcher(item.getHoverName().getString());

        if(matcher.find()){
            Health = Integer.parseInt(matcher.group(1));
        }else{//I HOPE THIS ONLY WORKS WHEN THE ITEM HAS 0 HP
            //OBS from 2 days later: IT DOESN'T...

            //addHelthToDisplay(item);
        }
    }

    private void updateDisplayHealth(){
        originalName.append(new StringTextComponent(" (HP: "+String.valueOf(Health)+")").withStyle(TextFormatting.GOLD));
        ITEM_STACK.setHoverName(originalName);
    }

    public Boolean doDamageToItemVanilla(ItemStack item, int damage){
        Pattern pattern = Pattern.compile("\\(HP: (\\d+)\\)");
        Matcher matcher = pattern.matcher(item.getDisplayName().getString());

        if(matcher.find()){
            Health = Integer.parseInt(matcher.group(1)) - damage;
            if(Health <= 0){
                Health = 100;
                return true;
            }
        }else{
            LOGGER.info("ERROR WHILE GETTING HEALTH FROM DISPLAY NAME");
        }
        updateDisplayHealth();
        return false;
    }

    public Boolean doDamageToItemModded(int damage){
        this.Health -= damage;
        if(Health <= 0){
            Health = 100;
            return true;
        }
        updateDisplayHealth();
        return false;
    }

    public ItemStack getBaseItem() {
        return ITEM_STACK;
    }
}
