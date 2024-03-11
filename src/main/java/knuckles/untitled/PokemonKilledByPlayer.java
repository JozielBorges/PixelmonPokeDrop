package knuckles.untitled;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

public class PokemonKilledByPlayer {
    private final HashSet<ServerPlayerEntity> playerEntities = new HashSet<>();

    public HashSet<ServerPlayerEntity> getPlayerEntities() {
        return playerEntities;
    }


    public void addPlayerEntities(ServerPlayerEntity playerEntities) {
        if(!this.playerEntities.contains(playerEntities)){
            this.playerEntities.add(playerEntities);
        }
    }

    public void removePlayerEntities(ServerPlayerEntity playerEntity){
        if(playerEntities.contains(playerEntity)){
            playerEntities.remove(playerEntity);
        }
    }
}
