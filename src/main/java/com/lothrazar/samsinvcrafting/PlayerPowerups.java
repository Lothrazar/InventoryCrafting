package com.lothrazar.samsinvcrafting;
 
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerPowerups implements IExtendedEntityProperties
{
	private final static String EXT_PROP_NAME = ModInvCrafting.MODID;
	private EntityPlayer player;//we get one of these powerup classes for each player
	private EntityPlayer prevPlayer;

	public PlayerPowerups(EntityPlayer player)
	{
		this.player = player; 
		this.prevPlayer = null;
	}
	@Override
	public void init(Entity entity, World world) 
	{ 
		 if ((entity instanceof EntityPlayer))
		 {
			 if ((this.player != null) && (this.player != entity))
		     {
				 this.prevPlayer = this.player;
		     }
		    
		    this.player = ((EntityPlayer)entity);
		 }
	}	
	
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(PlayerPowerups.EXT_PROP_NAME, new PlayerPowerups(player));
	}

	public static final PlayerPowerups get(EntityPlayer player)
	{
		return (PlayerPowerups) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
	
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{ 
		 if (this.player.inventory instanceof InventoryPlayerCrafting == false)
		 {
		   this.player.inventory = new InventoryPlayerCrafting(this.player);
		   this.player.inventoryContainer = new ContainerPlayerCrafting((InventoryPlayerCrafting)this.player.inventory, !this.player.worldObj.isRemote, this.player);
		   this.player.openContainer = this.player.inventoryContainer;
		   ((InventoryPlayerCrafting)this.player.inventory).readFromNBT(compound.getTagList("Inventory", 10));
		 }
 	}
 
	public void onJoinWorld() 
	{
		if (this.player.inventory instanceof InventoryPlayerCrafting == false)
		{
			this.player.inventory = new InventoryPlayerCrafting(this.player);
			this.player.inventoryContainer = new ContainerPlayerCrafting((InventoryPlayerCrafting)this.player.inventory, !this.player.worldObj.isRemote, this.player);
			this.player.openContainer = this.player.inventoryContainer;
	    }
	    
	    if (this.prevPlayer != null)
	    {
		    this.player.inventory.readFromNBT(this.prevPlayer.inventory.writeToNBT(new NBTTagList()));
		    this.prevPlayer = null;
	    }
	}
}