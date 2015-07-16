package com.lothrazar.samsinvcrafting;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ButtonPacket implements IMessage 
{
	public ButtonPacket() {}
	NBTTagCompound tags = new NBTTagCompound();
	public static final int ID = 0;
	
	public ButtonPacket(NBTTagCompound ptags)
	{
		tags = ptags;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		tags = ByteBufUtils.readTag(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, this.tags);
	}

	public class HandleButtonPacket implements IMessageHandler<ButtonPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ButtonPacket message, MessageContext ctx)
		{
			System.out.println("packet handler got it");
			return message;
			
			//todo: server handle the button press from inventory
			
			
			//return null
		}
	}
}
