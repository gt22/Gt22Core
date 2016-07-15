package com.gt22.gt22core.api.structure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gt22.gt22core.utils.FileUtils;
import com.gt22.gt22core.utils.Gt22MathHelper;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

/**
 * Internal, use {@link StructureApi} to work with structures
 */
public class StructureJson
{
	private String name;
	private JsonObject json = new JsonObject();
	private File file;
	private static File structurelist = new File(Loader.instance().getConfigDir() + "/GT22CoreStructure","strucutres.cfg");
	private ItemStack[][][] structure;
	private int[] f, s;
	private int xSize, ySize, zSize, weight, miny, maxy, worldid;
	private String target;
	private boolean usetarget;
	
	StructureJson(String name, int[] firstpoint, int[] secondpoint, World world, int weight, Block target, boolean usetarget, int miny, int maxy, int worldid) throws IllegalArgumentException
	{
		if(firstpoint.length != 3)
		{
			throw new IllegalArgumentException("First point must contain three coordinates {X, Y, Z}");
		}
		if(secondpoint.length != 3)
		{
			throw new IllegalArgumentException("Second point must contain three coordinates {X, Y, Z}");
		}
		if(miny > maxy || miny < 0 || maxy > world.getActualHeight())
		{
			throw new IllegalArgumentException("Incorrect height argumentas");
		}
		addToStructureList(name);
		this.weight = weight;
		this.name = name;
		this.file = new File(Loader.instance().getConfigDir() + "/GT22CoreStructure", name + ".json");
		this.f = firstpoint;
		this.s = secondpoint;
		this.target = target.getUnlocalizedName().substring(5);
		this.usetarget = usetarget;
		this.miny = miny;
		this.maxy = maxy;
		this.worldid = worldid;
		sortPoints();
		generateStructure(world);
		generateJson();
		write();
	}
	
	private static void addToStructureList(String name)
	{
		try
		{
			FileUtils.initFile(structurelist);
			FileUtils.addLine(structurelist, name);
		}
		catch (IOException e)
		{
			throw new ReportedException(CrashReport.makeCrashReport(e, "Unable to write structure to structure config"));
		}
	}
	
	private void sortPoints()
	{
		for(int i = 0; i < f.length; i++)
		{
			if(f[i] > s[i])
			{
				s[i] = Gt22MathHelper.swap(f[i], f[i] = s[i]);
			}
		}
	}
	
	private void generateJson()
	{
		JsonArray structureJson = new JsonArray();
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				for(int z = 0; z < zSize; z++)
				{
					JsonObject block = new JsonObject();
					block.addProperty("xOffset", x);
					block.addProperty("yOffset", y);
					block.addProperty("zOffset", z);
					block.addProperty("meta", structure == null ? 0 : structure[x][y][z] == null ? 0 : structure[x][y][z].getItem() == null ? 0 : structure[x][y][z].getItemDamage());
					block.addProperty("blockname", structure == null || structure[x][y][z] == null || structure[x][y][z].getItem() == null ? Blocks.air.getUnlocalizedName() : structure[x][y][z].getItem().getUnlocalizedName());
					structureJson.add(block);
				}
			}
		}
		JsonObject structureProperties = new JsonObject();
		structureProperties.addProperty("weight", weight);
		structureProperties.addProperty("name", name);
		structureProperties.addProperty("target", target);
		structureProperties.addProperty("usetarget", usetarget);
		structureProperties.addProperty("miny", miny);
		structureProperties.addProperty("maxy", maxy);
		structureProperties.addProperty("world", worldid);
		json.add("structure", structureJson);
		json.add("properties", structureProperties);
	}
	
	private void generateStructure(World world)
	{
		xSize = s[0] - f[0] + 1;
		ySize = s[1] - f[1] + 1;
		zSize = s[2] - f[2] + 1;
		structure = new ItemStack[xSize][ySize][zSize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				for(int z = 0; z < zSize; z++)
				{
					structure[x][y][z] = new ItemStack(world.getBlock(x + f[0], y + f[1], z + f[2]), 1, world.getBlockMetadata(x + f[0], y + f[1], z + f[2]));
				}
			}
		}
	}
	
	private void write()
	{
		FileUtils.initFile(file);	
		try
		{
			FileOutputStream jsonfile = new FileOutputStream(file);
			jsonfile.write(json.toString().getBytes());
			jsonfile.close();
		}
		catch (Exception e)
		{
			FMLLog.bigWarning("Cannot write json file for structure " + name);
			e.printStackTrace();
		}
	}

	static File getStructurelist()
	{
		return structurelist;
	}
}