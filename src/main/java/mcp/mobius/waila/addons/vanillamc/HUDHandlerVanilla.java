package mcp.mobius.waila.addons.vanillamc;

import btw.block.BTWBlocks;
import btw.block.blocks.CropsBlock;
import mcp.mobius.waila.addons.ExternalModulesHandler;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.src.*;

import java.util.List;

public class HUDHandlerVanilla implements IWailaDataProvider {
    static int mobSpawnerID = Block.mobSpawner.blockID;
    static int wheatCropID = BTWBlocks.wheatCrop.blockID;
    static int melonStemID = Block.melonStem.blockID;
    static int pumpkinStemID = Block.pumpkinStem.blockID;
    static int carrotCropID = BTWBlocks.carrotCrop.blockID;
    static int potatoID = Block.potato.blockID;
    static int hempCropID = BTWBlocks.hempCrop.blockID;
    static int oakSaplingID = BTWBlocks.oakSapling.blockID;
    static int spruceSaplingID = BTWBlocks.spruceSapling.blockID;
    static int birchSaplingID = BTWBlocks.birchSapling.blockID;
    static int jungleSaplingID = BTWBlocks.jungleSapling.blockID;
    static int netherStalkID = Block.netherStalk.blockID;
    static int leverID = Block.lever.blockID;
    static int repeaterIdle = Block.redstoneRepeaterIdle.blockID;
    static int repeaterActv = Block.redstoneRepeaterActive.blockID;
    static int comparatorIdl = Block.redstoneComparatorIdle.blockID;
    static int comparatorAct = Block.redstoneComparatorActive.blockID;
    static int redstone = Block.redstoneWire.blockID;


    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }


    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        int blockID = accessor.getBlockID();
        return currenttip;
    }


    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        int blockID = accessor.getBlockID();

        if (config.getConfig("vanilla.growthvalue") &&
                (Block.blocksList[blockID] instanceof CropsBlock ||
                        Block.blocksList[blockID] instanceof BlockCrops ||
                        Block.blocksList[blockID] instanceof BlockStem ||
                        blockID == netherStalkID)) {

            int metadata = accessor.getMetadata();
            float growthValue = blockID == netherStalkID? metadata / 3.0F * 100F: (metadata & 7) / 7.0F * 100.0F;

            if (growthValue != 100.0D) {
                currenttip.add(String.format("Growth : %.0f %%", growthValue));
            } else {
                if(blockID == wheatCropID || blockID == hempCropID) currenttip.add("Growth : Upward");
                else currenttip.add("Growth : Mature");
            }
            return currenttip;
        }

        if (config.getConfig("vanilla.leverstate") &&
                blockID == leverID) {
            String redstoneOn = ((accessor.getMetadata() & 0x8) == 0) ? "Off" : "On";
            currenttip.add("State : " + redstoneOn);
            return currenttip;
        }

        if (config.getConfig("vanilla.repeater") && (
                blockID == repeaterIdle || blockID == repeaterActv)) {
            int tick = (accessor.getMetadata() >> 2) + 1;
            if (tick == 1) {
                currenttip.add(String.format("Delay : %s tick", tick));
            } else {
                currenttip.add(String.format("Delay : %s ticks", tick));
            }
            return currenttip;
        }

        if (config.getConfig("vanilla.comparator") && (
                blockID == comparatorIdl || blockID == comparatorAct)) {
            String mode = ((accessor.getMetadata() >> 2 & 0x1) == 0) ? "Comparator" : "Subtractor";

            currenttip.add("Mode : " + mode);

            return currenttip;
        }

        if (config.getConfig("vanilla.redstone") &&
                blockID == redstone) {
            currenttip.add(String.format("Power : %s", accessor.getMetadata()));
            return currenttip;
        }

        if (config.getConfig("vanilla.spawntype") && blockID == mobSpawnerID && accessor.getTileEntity() instanceof TileEntityMobSpawner) {
            currenttip.add(String.format("Type : %s", ((TileEntityMobSpawner) accessor.getTileEntity()).func_98049_a().getEntityNameToSpawn()));
        }

        return currenttip;
    }

    public static void register() {
        ExternalModulesHandler.instance().addConfig("VanillaMC", "vanilla.spawntype", "Spawner type");
        ExternalModulesHandler.instance().addConfig("VanillaMC", "vanilla.growthvalue", "Growth value");
        ExternalModulesHandler.instance().addConfig("VanillaMC", "vanilla.leverstate", "Lever state");
        ExternalModulesHandler.instance().addConfig("VanillaMC", "vanilla.repeater", "Repeater delay");
        ExternalModulesHandler.instance().addConfig("VanillaMC", "vanilla.comparator", "Comparator mode");
        ExternalModulesHandler.instance().addConfig("VanillaMC", "vanilla.redstone", "Redstone power");

        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), mobSpawnerID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), wheatCropID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), melonStemID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), pumpkinStemID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), carrotCropID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), potatoID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), hempCropID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), netherStalkID);

        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), oakSaplingID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), spruceSaplingID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), birchSaplingID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), jungleSaplingID);

        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), leverID);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), repeaterIdle);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), repeaterActv);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), comparatorIdl);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), comparatorAct);
        // ExternalModulesHandler.instance().registerHeadProvider(new HUDHandlerVanilla(), redstone);
        ExternalModulesHandler.instance().registerBodyProvider(new HUDHandlerVanilla(), redstone);

        ExternalModulesHandler.instance().registerDocTextFile("D:/BTW/Test/src/java/mcp/mobius/waila/addons/vanillamc/WikiData.csv");
    }
}


