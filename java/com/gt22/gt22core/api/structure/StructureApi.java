package com.gt22.gt22core.api.structure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gt22.gt22core.core.Core;
import com.gt22.gt22core.utils.FileUtils;
import com.gt22.gt22core.utils.Gt22MathHelper;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

public class StructureApi
{		
	private static final File structurelist = new File(Loader.instance().getConfigDir() + "/GT22CoreStructure","strucutres.cfg");
	//Main json names
	public static final String STRUCTURE = "structure";
	public static final String PROPERTIES = "properties";
	//Structure names
	public static final String X_OFFSET = "xOffset";
	public static final String Y_OFFSET = "yOffset";
	public static final String Z_OFFSET = "zOffset";
	public static final String BLOCK_META = "meta";
	public static final String BLOCK_NAME = "blockname";
	//Properties names
	public static final String WEIGHT = "weight";
	public static final String NAME = "name";
	public static final String TARGET = "target";
	public static final String USE_TARGET = "usetarget";
	public static final String MIN_Y = "miny";
	public static final String MAX_Y = "maxy";
	public static final String WORLD = "world";
	/**
	 * Create and write to file strcuture, this structure will not generate in normal ways. Designet to use form commands, but can be used in other cases
	 * @param name - name of the structure
	 * @param firstpoint - first corner of structure in format {x, y, z}
	 * @param secondpoint - second corner of structure in format {x, y, z}
	 * @param templateWorld - world where structure template was build in
	 */
	public static void createStructure(String name, int[] firstpoint, int[] secondpoint, World world)
	{
		createStructure(name, firstpoint, secondpoint, world, -1, Blocks.air, false, 0, 0, 0);
	}

	/**
	 * Create and write to file strcuture. Designet to use form commands, but can be used in other cases
	 * @param name - name of the structure
	 * @param firstpoint - first corner of structure in format {x, y, z}
	 * @param secondpoint - second corner of structure in format {x, y, z}
	 * @param templateWorld - world where structure template was build in, does not affect generating
	 * @param weight - generation weight, than it bigget than rarer will be the structure (formula is crazy, even i can't understand how it working), negative weight will prevent structure from generation
	 * @param target - Target block whitch structure should replace, structure will not generate if even one block doen't eqals target
	 * @param usetarget - if false target will be ignored, usually used for custom generating structure
	 * @param miny - minimum y coordinate, structure will not generate lower
	 * @param miny - maximum y coordinate, structure will not generate higher
	 * @param targetWorld - id of world where structure should generate
	 */
	public static boolean createStructure(String name, int[] firstpoint, int[] secondpoint, World templateWorld, int weight, Block target, boolean usetarget, int miny, int maxy, int targetWorld)
	{
		try
		{
			new StructureJson(name, firstpoint, secondpoint, templateWorld, weight, target, usetarget, miny, maxy, targetWorld);
			return true;
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Return json of structure
	 * @param name
	 * of structure
	 * @return
	 */
	public static JsonObject getByName(String name)
	{
		return load(new File(Loader.instance().getConfigDir() + "/GT22CoreStructure", name + ".json"));
	}

	/**
	 * Return properties of the structure.
	 * Properties contain:
	 * weight : weight of structure
	 * name : name of structure
	 * target : name of target block
	 * usetarget : does structure uses target or not
	 * miny : minimal y coordinate
	 * maxy : maximal y coordinate
	 * world : id of world where strucutre will genetate
	 * @param name
	 * @return
	 */
	public static JsonObject getStructureProperties(String name)
	{
		return getByName(name).getAsJsonObject(PROPERTIES);
	}

	/**
	 * Delete structure
	 * @param name
	 * of structure
	 * @return true if structure was succsfully deleted
	 */
	public static boolean deleteStructure(String name)
	{
		File file = new File(Loader.instance().getConfigDir() + "/GT22CoreStructure", name + ".json");
		if (!file.exists())
		{
			return false;
		}
		file.delete();
		if (!structurelist.exists())
		{
			return true;
		}
		try
		{
			FileUtils.deleteLine(structurelist, name);
		}
		catch (IOException e)
		{
			throw new ReportedException(CrashReport.makeCrashReport(e, "Unable to read structure config"));
		}
		return true;
	}

	private static JsonObject load(File file)
	{

		if (!file.canWrite())
		{
			FMLLog.warning("cannot load json for structure " + file.getName());
			return null;
		}
		try
		{
			return new JsonParser().parse(new FileReader(file)).getAsJsonObject();
		}
		catch (Exception e)
		{
			FMLLog.bigWarning("Can't load json for structure");
			e.printStackTrace();
		}
		return null;
	}

	private static ArrayList<JsonObject> getStructures()
	{
		ArrayList<JsonObject> ret = new ArrayList<JsonObject>();
		try
		{
			FileUtils.initFile(structurelist);
			BufferedReader list = FileUtils.createReader(structurelist);
			String temp = "";
			int i = 0;
			while (true)
			{
				temp = list.readLine();
				i++;
				if (temp == null)
				{
					break;
				}
				JsonObject jsontemp = StructureApi.getByName(temp);
				if (jsontemp == null)
				{
					FileUtils.deleteLine(structurelist, i);
					continue;
				}
				ret.add(jsontemp);
			}
		}
		catch (IOException e)
		{
			throw new ReportedException(CrashReport.makeCrashReport(e, "Unable to read structure config"));
		}
		return ret;
	}

	/**
	 * Return list with names of all registered strcutures
	 * @return
	 */
	public static ArrayList<String> getStructuresNames()
	{
		ArrayList<JsonObject> structures = getStructures();
		ArrayList<String> ret = new ArrayList<String>();
		for (JsonObject j : structures)
		{
			ret.add(j.getAsJsonObject(PROPERTIES).get(NAME).getAsString());
		}
		return ret;
	}

	/**
	 * Generate random structure based on weight, y corrdinate calculated
	 * internaly
	 * @param world
	 * @param x
	 * @param z
	 */
	public static void getnerateRandomStrucutre(World world, int x, int z)
	{
		ArrayList<JsonObject> structures = getStructures();
		if (structures.isEmpty())
		{
			return;
		}
		ArrayList<Integer> weights = new ArrayList<Integer>();
		for (int i = 0; i < structures.size(); i++)
		{
			JsonObject j = structures.get(i);
			int weight = j.getAsJsonObject(PROPERTIES).get(StructureApi.WEIGHT).getAsInt();
			if (weight < 0)
			{
				structures.remove(j);
				continue;
			}
			weights.add(weight);
		}
		if (weights.isEmpty())
		{
			return;
		}
		weights.sort(null);
		int randweight = world.rand.nextInt(weights.get(weights.size() - 1) + 1);
		int midweight = weights.get((weights.size() / 2));
		if (randweight >= midweight)
		{
			JsonObject structure = weights.size() == 1 ? structures.get(0) : structures.get(Gt22MathHelper.getPositionOfClosest(weights, world.rand.nextInt(weights.get(weights.size() - 1) - world.rand.nextInt(weights.get(weights.size() - 2)))));
			JsonObject properties = structure.getAsJsonObject(StructureApi.PROPERTIES);
			int miny = properties.get(StructureApi.MIN_Y).getAsInt(), maxy = properties.get(StructureApi.MAX_Y).getAsInt(), ydiff = Gt22MathHelper.diff(miny, maxy);
			generateStructure(structure, world, x, miny + world.rand.nextInt(ydiff), z);
		}
	}

	/**
	 * Generate specified structure
	 * @param json
	 * of the structure
	 * @param world
	 * to generate structure (Srtucture will no generate if different
	 * from structure world)
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void generateStructure(JsonObject json, World world, int x, int y, int z)
	{
		generateStructure(json, world, x, y, z, false, false);
	}

	/**
	 * Generate specified structure
	 * @param json
	 * of the structure
	 * @param world
	 * to generate structure
	 * @param x
	 * @param y
	 * @param z
	 * @param ignoreJsonTargetSetting
	 * - if true usetarget setting in json will be ignored and
	 * counted as false
	 * @param ignoreJsonWorldSetting
	 * - if true world setting in json will be ignored
	 */
	public static void generateStructure(JsonObject json, World world, int x, int y, int z, boolean ignoreJsonTargetSetting, boolean ignoreJsonWorldSetting)
	{
		JsonObject properties = json.getAsJsonObject(StructureApi.PROPERTIES);
		if (!ignoreJsonWorldSetting && world.provider.dimensionId != properties.get(StructureApi.WORLD).getAsInt())
		{
			return;
		}
		Block target = GameData.getBlockRegistry().getObject(properties.get(StructureApi.TARGET).getAsString());
		boolean forcetarget = !ignoreJsonTargetSetting && properties.get(StructureApi.USE_TARGET).getAsBoolean();
		JsonArray str = json.getAsJsonArray(STRUCTURE);
		if (forcetarget && !ignoreJsonTargetSetting)
		{
			for (JsonElement e : str)
			{
				JsonObject o = e.getAsJsonObject();
				if (world.getBlock(x + o.get(X_OFFSET).getAsInt(), y + o.get(Y_OFFSET).getAsInt(), z + o.get(Z_OFFSET).getAsInt()) != target)
				{
					System.out.println("target");
					return;
				}
			}
		}
		for (JsonElement e : str)
		{
			JsonObject o = e.getAsJsonObject();
			world.setBlock(x + o.get(X_OFFSET).getAsInt(), y + o.get(Y_OFFSET).getAsInt(), z + o.get(Z_OFFSET).getAsInt(), GameData.getBlockRegistry().getObject(o.get(BLOCK_NAME).getAsString()), o.get(BLOCK_META).getAsInt(), 3);
		}
	}
	
	private static class StructureJson
	{
		private String name;
		private JsonObject json = new JsonObject();
		private File file;
		private ItemStack[][][] structure;
		private int[] f, s;
		private int xSize, ySize, zSize, weight, miny, maxy, worldid;
		private String target;
		private boolean usetarget;
		
		public StructureJson(String name, int[] firstpoint, int[] secondpoint, World world, int weight, Block target, boolean usetarget, int miny, int maxy, int worldid) throws IllegalArgumentException
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
				throw new IllegalArgumentException("Incorrect height arguments");
			}
			addToStructureList(name);
			this.weight = weight;
			this.name = name;
			this.file = new File(Loader.instance().getConfigDir() + "/GT22CoreStructure", name + ".json");
			this.f = firstpoint;
			this.s = secondpoint;
			this.target = GameData.getBlockRegistry().getNameForObject(target);
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
						ItemStack s = structure[x][y][z];
						block.addProperty(X_OFFSET, x);
						block.addProperty(Y_OFFSET, y);
						block.addProperty(Z_OFFSET, z);
						block.addProperty(BLOCK_META, structure == null || s == null || s.getItem() == null ? 0 : s.getItemDamage());
						block.addProperty(BLOCK_NAME, structure == null || s == null || s.getItem() == null ? GameData.getBlockRegistry().getNameForObject(Blocks.air) : GameData.getBlockRegistry().getNameForObject(Block.getBlockFromItem(s.getItem())));
						structureJson.add(block);
					}
				}
			}
			JsonObject structureProperties = new JsonObject();
			structureProperties.addProperty(StructureApi.WEIGHT, weight);
			structureProperties.addProperty(StructureApi.NAME, name);
			structureProperties.addProperty(StructureApi.TARGET, target);
			structureProperties.addProperty(StructureApi.USE_TARGET, usetarget);
			structureProperties.addProperty(StructureApi.MIN_Y, miny);
			structureProperties.addProperty(StructureApi.MAX_Y, maxy);
			structureProperties.addProperty(StructureApi.WORLD, worldid);
			json.add(StructureApi.STRUCTURE, structureJson);
			json.add(PROPERTIES, structureProperties);
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
			try
			{
				FileUtils.initFile(file);
				FileOutputStream jsonfile = new FileOutputStream(file);
				jsonfile.write(json.toString().getBytes());
				jsonfile.close();
			}
			catch (Exception e)
			{
				FMLLog.log(Core.modid, Level.ERROR, e, "Unable to write json for structure %s", name);
				e.printStackTrace();
			}
		}
	}
}
