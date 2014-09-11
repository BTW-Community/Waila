package mcp.mobius.waila.addons.enderio;

import java.util.List;

import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.item.ItemStack;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import static mcp.mobius.waila.api.SpecialChars.*;

public class HUDHandlerCapacitor implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		
		if (accessor.getPlayer().isSneaking()){
			try{
				
				String maxIOStr  = LangUtil.translateG("hud.msg.maxio");
				String inputStr  = LangUtil.translateG("hud.msg.input");
				String outputStr = LangUtil.translateG("hud.msg.output");
				
				Integer maxEnergyStored = (Integer)EnderIOModule.TCB_getMaxEnergyStored.invoke(accessor.getTileEntity());
				Double  energyStored    = (Double)EnderIOModule.TCB_getEnergyStored.invoke(accessor.getTileEntity());
				Integer maxIO           = (Integer)EnderIOModule.TCB_getMaxIO.invoke(accessor.getTileEntity());
				Integer maxInput        = (Integer)EnderIOModule.TCB_getMaxInput.invoke(accessor.getTileEntity());
				Integer maxOutput       = (Integer)EnderIOModule.TCB_getMaxOutput.invoke(accessor.getTileEntity());
				
				if (config.getConfig("enderio.storage"))
					currenttip.add(String.format("%s%.1f%s / %s%d%s RF", WHITE, energyStored * 10, RESET, WHITE, maxEnergyStored * 10, RESET));
				
				if (config.getConfig("enderio.inout")){
					currenttip.add(String.format("%s : %s%d%sRF/t ", maxIOStr, TAB + ALIGNRIGHT + WHITE, maxIO     * 10, TAB + ALIGNRIGHT));
					currenttip.add(String.format("%s : %s%d%sRF/t ", inputStr, TAB + ALIGNRIGHT + WHITE, maxInput  * 10, TAB + ALIGNRIGHT));
					currenttip.add(String.format("%s : %s%d%sRF/t ", outputStr,TAB + ALIGNRIGHT + WHITE, maxOutput * 10, TAB + ALIGNRIGHT));
				}
				
				
			} catch (Exception e) {
				currenttip = WailaExceptionHandler.handleErr(e, accessor.getTileEntity().getClass().getName(), currenttip);			
			}
		}
		
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return currenttip;
	}

}
