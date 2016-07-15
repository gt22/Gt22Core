package com.gt22.gt22core.command;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import com.google.gson.JsonObject;
import com.gt22.gt22core.api.structure.StructureApi;
import com.gt22.gt22core.utils.ChatComponentError;
import com.gt22.gt22core.utils.ChatComponentSucces;

public class CommandHandler extends CommandBase
{

	@Override
	public String getCommandName()
	{
		return "Gt22Core";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/Gt22Core help";
	}

	@Override
	public List getCommandAliases()
	{
		ArrayList<String> ret = new ArrayList<String>();
		ret.add("gt22core");
		ret.add("gt22c");
		ret.add("gtc");
		return ret;
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 4;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		switch(args.length)
		{
			case(1):
			{
				return getListOfStringsMatchingLastWord(args, new String[] {"structure", "help"});
			}
			case(2):
			{
				switch(args[0].toLowerCase())
				{
					case("structure"):
					{
						return getListOfStringsMatchingLastWord(args, new String[] {"set", "save", "remove", "points", "clear", "spawn", "help"});
					}
				}
				return getListOfStringsMatchingLastWord(args, new String[] {});
			}
			case(3):
			{
				switch(args[1].toLowerCase())
				{
					case("set"):
					{
						return getListOfStringsMatchingLastWord(args, new String[] {"1", "2"});
					}
					case("save"):
					{
						return getListOfStringsMatchingLastWord(args, new String[] {"name"}); 
					}
					case("remove"): case("spawn"):
					{
						ArrayList<String> names = StructureApi.getStructuresNames();
						String[] ret = new String[names.size()];
						for(int i = 0; i < ret.length; i++)
						{
							ret[i] = names.get(i);
						}
						return getListOfStringsMatchingLastWord(args, ret);
					}
				}
				return getListOfStringsMatchingLastWord(args, new String[] {});
			}
			case(4):
			{
				switch(args[1].toLowerCase())
				{
					case("save"):
					{
						return getListOfStringsMatchingLastWord(args, new String[] {"weight(number)"});
					}
				}
				return getListOfStringsMatchingLastWord(args, new String[] {});
			}
			case(5):
			{
				switch(args[1].toLowerCase())
				{
					case("save"):
					{
						return getListOfStringsMatchingLastWord(args, new String[] {"useTarget(true/false)"});
					}
				}
				return getListOfStringsMatchingLastWord(args, new String[] {});
			}
			case(6):
			{
				switch(args[1].toLowerCase())
				{
					case("save"):
					{
						return getListOfStringsMatchingLastWord(args, new String[] {"miny(number)"});
					}
				}
				return getListOfStringsMatchingLastWord(args, new String[] {});
			}
			case(7):
			{
				switch(args[1].toLowerCase())
				{
					case("save"):
					{
						return getListOfStringsMatchingLastWord(args, new String[] {"maxy(number)"});
					}
				}
				return getListOfStringsMatchingLastWord(args, new String[] {});
			}
			default:
			{
				return getListOfStringsMatchingLastWord(args, new String[] {});
			}
		}
	}
	
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		String prefix = "/Gt22 core ";
		if (args.length == 0)
		{
			sender.addChatMessage(new ChatComponentError("Unknown command, use /Gt22Core help"));
			return;
		}
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		World world = player.worldObj;
		MovingObjectPosition block = getTargetBlock(player.worldObj, player, false);
		int x = 0, y = 0, z = 0;
		if (block != null)
		{
			x = block.blockX;
			y = block.blockY;
			z = block.blockZ;
		}
		switch (args[0].toLowerCase())
		{
			case ("structure"):
			{
				switch (args[1].toLowerCase())
				{
					case ("set"):
					{

						if (args.length < 3)
						{
							sender.addChatMessage(new ChatComponentError("Enter point number"));
							return;
						}
						switch (args[2].toLowerCase())
						{
							case ("1"):
							{
								if (block == null)
								{
									sender.addChatMessage(new ChatComponentError("Cannot set point"));
									return;
								}
								player.getEntityData().setIntArray("POINT1", new int[]
								{ x, y, z });
								sender.addChatMessage(new ChatComponentSucces("First point setted on x: " + x + " y: " + y + " z:" + z));
								return;
							}
							case ("2"):
							{
								if (player.worldObj.isAirBlock(x, y, z))
								{
									sender.addChatMessage(new ChatComponentError("Cannot set point"));
									return;
								}
								player.getEntityData().setIntArray("POINT2", new int[]
								{ x, y, z });
								sender.addChatMessage(new ChatComponentSucces("Second point setted on x: " + x + " y: " + y + " z:" + z));
								return;
							}
						}
					}
					case ("save"):
					{
						if (args.length < 3)
						{
							sender.addChatMessage(new ChatComponentError("Enter structure name"));
							return;
						}
						boolean points = true;
						if (!player.getEntityData().hasKey("POINT1"))
						{
							sender.addChatMessage(new ChatComponentError("First point not setted"));
							points = false;
						}
						if (!player.getEntityData().hasKey("POINT2"))
						{
							sender.addChatMessage(new ChatComponentError("Second point not setted"));
							points = false;
						}
						if (!points)
						{
							return;
						}
						if (args.length < 7)
						{
							sender.addChatMessage(new ChatComponentSucces("Structure created, structure weight seted to -1 so structure will not generate in normal ways"));
							StructureApi.createStructure(args[2], player.getEntityData().getIntArray("POINT1"), player.getEntityData().getIntArray("POINT2"), player.worldObj);
						}
						else if(args.length < 8)
						{
							sender.addChatMessage(new ChatComponentSucces("Structure created, current world will be used to generate structure"));
							StructureApi.createStructure(args[2], player.getEntityData().getIntArray("POINT1"), player.getEntityData().getIntArray("POINT2"), player.worldObj, parseInt(sender, args[3]), block == null ? Blocks.air : world.getBlock(x, y, z), parseBoolean(sender, args[4]), parseInt(player, args[5]), parseInt(sender, args[6]), world.provider.dimensionId);
						}
						else
						{
							sender.addChatMessage(new ChatComponentSucces("Structure created"));
							StructureApi.createStructure(args[2], player.getEntityData().getIntArray("POINT1"), player.getEntityData().getIntArray("POINT2"), player.worldObj, parseInt(sender, args[3]), block == null ? Blocks.air : world.getBlock(x, y, z), parseBoolean(sender, args[4]), parseInt(player, args[5]), parseInt(sender, args[6]), parseInt(sender, args[7]));
						}
						player.getEntityData().removeTag("POINT1");
						player.getEntityData().removeTag("POINT2");
						return;
					}
					case ("clear"):
					{
						player.getEntityData().removeTag("POINT1");
						player.getEntityData().removeTag("POINT2");
						sender.addChatMessage(new ChatComponentSucces("Points cleared"));
						return;
					}
					case ("points"):
					{
						boolean points = false;
						if (player.getEntityData().hasKey("POINT1"))
						{
							int[] point = player.getEntityData().getIntArray("POINT1");
							sender.addChatMessage(new ChatComponentText("First point - x: " + point[0] + " y: " + point[1] + " z: " + point[2]));
							points = true;
						}
						if (player.getEntityData().hasKey("POINT2"))
						{
							int[] point = player.getEntityData().getIntArray("POINT2");
							sender.addChatMessage(new ChatComponentText("Second point - x: " + point[0] + " y: " + point[1] + " z: " + point[2]));
							points = true;
						}
						if (!points)
						{
							sender.addChatMessage(new ChatComponentError("No points seted"));
						}
						return;
					}
					case ("remove"):
					{
						if (args.length < 3)
						{
							sender.addChatMessage(new ChatComponentError("Enter structure name"));
							return;
						}
						if (!StructureApi.deleteStructure(args[2]))
						{
							sender.addChatMessage(new ChatComponentError("Unable to find structure " + args[2]));
						}
						else
						{
							sender.addChatMessage(new ChatComponentSucces("Succesfully removed structure " + args[2]));
						}
						return;
					}
					case ("spawn"):
					{
						if (args.length < 3)
						{
							sender.addChatMessage(new ChatComponentError("Enter strucutre name"));
							return;
						}
						String name = args[2];
						JsonObject json = StructureApi.getByName(name);
						if (json == null)
						{
							sender.addChatMessage(new ChatComponentError("Trying to generate missing strucuture " + name));
							return;
						}
						if (block == null)
						{
							sender.addChatMessage(new ChatComponentError("To create strucuture target block"));
						}
						StructureApi.generateStructure(json, world, x, y, z, true, true);
						return;
					}
					case ("help"):
					{
						sender.addChatMessage(new ChatComponentText(prefix + "set 1/2 - setting first/second point"));
						sender.addChatMessage(new ChatComponentText(prefix + "clear - clear setted points"));
						sender.addChatMessage(new ChatComponentText(prefix + "points - display points coords"));
						sender.addChatMessage(new ChatComponentText(prefix + "save <name> [<weight> <useTarget> <miny> <maxy>] - save structure, than more weight parameter, then more rare will be the structure, setting lesser then 0 will prevent structure from normal generating. Defaul: -1, useTarget can be true or false and will affect structure generation: if true structure will check are all blocks in structure equals target blocks that specified  by block you looking while saving, defauld: false due to structure not generate. forcespawn ignored while creating structure with spawn command"));
						sender.addChatMessage(new ChatComponentText(prefix + "remove <name> - removes a structure"));
						sender.addChatMessage(new ChatComponentText(prefix + "spawn <name> - create strucutre using block that you are looking as point with minimum coordinates"));
						return;
					}
					default:
					{
						sender.addChatMessage(new ChatComponentError("Unknown structure command command, use /Gt22Core structure help"));
						return;
					}
				}
			}
			case ("help"):
			{
				sender.addChatMessage(new ChatComponentText("Instead of Gt22Core you can also use gt22core, gt22c, or gtc"));
				sender.addChatMessage(new ChatComponentText(prefix + "structure - allow saving and spawning structure by command, contains help command"));
				return;
			}
			default:
			{
				sender.addChatMessage(new ChatComponentError("Unknown command command, use /Gt22Core help"));
				return;
			}
		}
	}

	/**
	 * @author Azanor
	 */
	public static MovingObjectPosition getTargetBlock(World world, Entity entity, boolean par3)
	{
		float var4 = 1.0F;
		float var5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * var4;

		float var6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * var4;

		double var7 = entity.prevPosX + (entity.posX - entity.prevPosX) * var4;

		double var9 = entity.prevPosY + (entity.posY - entity.prevPosY) * var4 + 1.62D - entity.yOffset;

		double var11 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * var4;

		Vec3 var13 = Vec3.createVectorHelper(var7, var9, var11);
		float var14 = MathHelper.cos(-var6 * 0.017453292F - 3.1415927F);
		float var15 = MathHelper.sin(-var6 * 0.017453292F - 3.1415927F);
		float var16 = -MathHelper.cos(-var5 * 0.017453292F);
		float var17 = MathHelper.sin(-var5 * 0.017453292F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		double var21 = 10.0D;
		Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);

		return world.func_147447_a(var13, var23, par3, !par3, false);
	}

}
