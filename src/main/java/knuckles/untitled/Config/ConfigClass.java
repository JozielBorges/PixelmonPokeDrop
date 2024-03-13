package knuckles.untitled.Config;

import net.minecraftforge.common.ForgeConfigSpec;
public final class ConfigClass {

    public static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.Builder builderCoin = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec SPEC_COIN;
    public static final  ForgeConfigSpec.ConfigValue<Integer> maxCoinDrop;
    public static final ForgeConfigSpec.ConfigValue<Boolean> amuletCoinMutiplyer;
    public static final ForgeConfigSpec.ConfigValue<Double> amuletCoinMutiplyerValue;
    public static final ForgeConfigSpec.ConfigValue<Boolean> doDamageToAmulet;

    public static final ForgeConfigSpec.ConfigValue<Integer> amountOfDamageToAmulet;



    public static final ForgeConfigSpec.ConfigValue<Double> bossTierCommonMP;
    public static final ForgeConfigSpec.ConfigValue<Double> bossTierUncommonMP;
    public static final ForgeConfigSpec.ConfigValue<Double> bossTierRareMP;
    public static final ForgeConfigSpec.ConfigValue<Double> bossTierEpicMP;
    public static final ForgeConfigSpec.ConfigValue<Double> bossTierLegendaryMP;
    public static final ForgeConfigSpec.ConfigValue<Double> bossTierUltimateMP;
    public static final ForgeConfigSpec.ConfigValue<Double> bossTierHauntedMP;
    public static final ForgeConfigSpec.ConfigValue<Double> bossTierDrownedMP;




    static {
        builder.comment("All configs made by the dump knuckles").push("money config");
        builderCoin.comment("All configs made by you know who").push("PokeTiers");

        maxCoinDrop = builder
                .comment("how much money will the pokens drop after being killed")
                .defineInRange("pokeCoinDrop", 500, 0, Integer.MAX_VALUE);
        amuletCoinMutiplyer = builder
                .comment("if the player has the amulet coin, it will incrise his droped?")
                .define("mutipleWithAmulet", Boolean.valueOf("true"));
        amuletCoinMutiplyerValue = builder
                .comment("multiply the droped coin with amulet coin by:")
                .define("amuletCoinMP", 2.0);
        doDamageToAmulet = builder
                .comment("Will the amulet get damage each time its use ? (NOT IMPLEMENTED)")
                .define("amuletCoinDamage", Boolean.valueOf("true"));
        amountOfDamageToAmulet = builder
                .comment("The amount of damage the amulet will get after being used")
                .define("amuletCoinDAMAGE",1);

        bossTierCommonMP = builderCoin.comment("multiply the coin from boss common by:").define("commonCoin",1.1);
        bossTierUncommonMP = builderCoin.comment("multiply the coin from boss:").define("uncommonCoin",1.3);
        bossTierRareMP = builderCoin.comment("multiply the coin from boss:").define("rareCoin",1.4);
        bossTierEpicMP = builderCoin.comment("multiply the coin from boss:").define("epicCoin",1.5);
        bossTierLegendaryMP = builderCoin.comment("multiply the coin from boss:").define("legendaryCoin",1.6);
        bossTierUltimateMP = builderCoin.comment("multiply the coin from boss:").define("ultimateCoin",1.7);
        bossTierHauntedMP = builderCoin.comment("multiply the coin from boss:").define("hauntedCoin",1.8);
        bossTierDrownedMP = builderCoin.comment("multiply the coin from boss:").define("drownedCoin",1.9);



        builder.pop();
        SPEC = builder.build();
        builderCoin.pop();
        SPEC_COIN = builderCoin.build();
    }
}
