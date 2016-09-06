package com.gt22.gt22core.texturegen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.gt22.gt22core.core.Core;
import com.gt22.gt22core.texturegen.ICustomJson.JsonType;
import com.gt22.gt22core.utils.FileUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraftforge.fml.common.FMLLog;

public class TextureGenRegistry {
	private static class TextureStorage {
		public Object reg;
		public String modid;

		public TextureStorage(Object clazz, String modid) {
			this.reg = clazz;
			this.modid = modid;
		}
	}

	private static List<TextureStorage> registries = Core.isDev() ? new ArrayList<TextureStorage>() : null;

	/**
	 * Use this method to add class containing Items/Blocks field (Like
	 * {@link net.minecraft.init.Items} or {@link net.minecraft.init.Items} for
	 * vanilla) After registering and jsons will appear in
	 * rundir/GeneratedTextures, Just copy assets forlder int your resources
	 * source forlder to add all generate jsons. Texture generating works ONLY
	 * in dev enviroment, if trying to use this in normal minecraft it will be
	 * just ignored. Also see: {@link DoNotApplyTexture} {@link ICustomJson}
	 * {@link IMultitextureBlock}
	 * 
	 * @param registry
	 * @param modid
	 */
	public static void reigster(Object registry, String modid) {
		if (Core.isDev()) {
			registries.add(new TextureStorage(registry, modid));
		}
	}

	/**
	 * Called interanly
	 */
	public static void generateTextures() {
		List<String> addedGenericFor = new ArrayList<String>();
		for (TextureStorage reg : registries) {
			if (!addedGenericFor.contains(reg.modid)) {
				createGeneric(reg.modid);
				addedGenericFor.add(reg.modid);
			}
			Field[] fields = reg.reg.getClass().getFields();
			for (Field f : fields) {
				if (!f.isAnnotationPresent(DoNotApplyTexture.class)) {
					try {
						if (f.getType().isAssignableFrom(Item.class)) {
							generateItemTexture(reg.modid, (Item) f.get(reg.reg));

						} else if (f.getType().isAssignableFrom(Block.class)) {
							generateBlockTexture(reg.modid, (Block) f.get(reg.reg));
						}
					} catch (IllegalAccessException e) {
						throw new ReportedException(CrashReport.makeCrashReport(e, "Unable to get value from registry. Are you using PRIVATE FIELDS IN REGISTRY!!??"));
					}
					catch(NullPointerException e)
					{
						FMLLog.log(Core.modid, Level.WARN, "Item/Block field %s was not be initialized, skipping", f.getName());
					}
				}
			}
		}
		registries = null;
	}

	private static void createGeneric(String modid) {
		File generic = new File(getDirForItem(modid), "ItemGeneric.json");
		try (BufferedReader in = new BufferedReader(new InputStreamReader(TextureGenRegistry.class.getResourceAsStream("/ItemGeneric.json"), "UTF-8")); BufferedWriter out = FileUtils.createWriter(generic, false)) {
			for (String buf = in.readLine(); buf != null; buf = in.readLine()) {
				out.write(buf);
			}
		} catch (IOException e) {
			throw new ReportedException(CrashReport.makeCrashReport(e, "Unable to create ItemGeneric.json for mod " + modid));
		}
	}

	private static File getCoreModDir(String modid) {
		return new File("GeneratedTextures/assets/" + modid);
	}

	private static File getDirForMod(String modid) {
		return new File(getCoreModDir(modid), "models/");
	}

	private static File getDirForItem(String modid) {
		return new File(getDirForMod(modid), "item");
	}

	private static File getDirForBlock(String modid) {
		return new File(getDirForMod(modid), "block");
	}

	public static File getDirForBlockstate(String modid) {
		return new File(getCoreModDir(modid), "blockstates");
	}

	private static void generateItemTexture(String modid, Item item) {
		File itemfile = new File(getDirForItem(modid), item.getRegistryName().getResourcePath() + ".json");
		try (BufferedWriter out = FileUtils.createWriter(itemfile, false)) {
			out.write(formatItemJson(modid, item));
		} catch (IOException e) {
			throw new ReportedException(CrashReport.makeCrashReport(e, "Unable to generate texture for item " + item.getUnlocalizedName()));
		}
	}

	private static void generateBlockTexture(String modid, Block block) {
		File blockfile = new File(getDirForBlock(modid), block.getRegistryName().getResourcePath() + ".json");
		File statefile = new File(getDirForBlockstate(modid), block.getRegistryName().getResourcePath() + ".json");
		File itemfile = new File(getDirForItem(modid), block.getRegistryName().getResourcePath() + ".json");
		try (BufferedWriter outblock = FileUtils.createWriter(blockfile, false); BufferedWriter outstate = FileUtils.createWriter(statefile, false); BufferedWriter outitem = FileUtils.createWriter(itemfile, false)) {
			outblock.write(formatBlockJson(modid, block));
			outstate.write(formatBlockStateJson(modid, block));
			outitem.write(formatItemBlockJson(modid, block));
		} catch (IOException e) {
			throw new ReportedException(CrashReport.makeCrashReport(e, "Unable to generate texture for block " + block.getUnlocalizedName()));
		}
	}

	//@formatter:off
	private static String formatItemJson(String modid, Item item) {
		if(item instanceof ICustomJson && ((ICustomJson) item).getJson(JsonType.ITEM) != null)
		{
			return ((ICustomJson) item).getJson(JsonType.ITEM);
		}
		if(item.isFull3D())
		{
			return String.format("{\n"
									+"\"parent\": \"item/handheld\",\n"
									+"\"textures\": {\n"
									+ "\"layer0\": \"%s:items/%s\"\n"
									+"}\n"
								+"}", modid, item.getRegistryName().getResourcePath());
		}
		else
		{
			return String.format(
					  "{\n"
					+ "\"parent\": \"%s:item/ItemGeneric\",\n"
					+ "\"textures\": {\n"
					+ "\"layer0\": \"%s:items/%s\"\n"
	    			+ "}\n"
					+ "}",
					modid, modid, item.getRegistryName().getResourcePath());
			}
		}
	
	private static String formatItemBlockJson(String modid, Block block) {
		if(block instanceof ICustomJson && ((ICustomJson) block).getJson(JsonType.ITEM) != null)
		{
			return ((ICustomJson) block).getJson(JsonType.ITEM);
		}
		return String.format(
				"{\n"
				    +"\"parent\":\"%s:block/%s\",\n"
				    +"\"display\": {\n"
				        +"\"thirdperson\": {\n"
				            +"\"rotation\": [ 10, -45, 170 ],\n"
				            +"\"translation\": [ 0, 1.5, -2.75 ],\n"
				            +"\"scale\": [ 0.375, 0.375, 0.375 ]\n"
				       +"}\n"
				    +"}\n"
				+"}",
				modid, block.getRegistryName().getResourcePath());
	}
	
	private static String formatBlockJson(String modid, Block block) {
		if(block instanceof ICustomJson && ((ICustomJson) block).getJson(JsonType.BLOCK) != null)
		{
			return ((ICustomJson) block).getJson(JsonType.BLOCK);
		}
		if(block instanceof IMultitextureBlock)
		{
			IMultitextureBlock bl = (IMultitextureBlock) block;
			return String.format("{\n"
					+ "\"parent\": \"block/cube\",\n"
					+ "\"textures\": {\n"
						+ "\"up\": \"%s\",\n"
						+ "\"down\": \"%s\",\n"
						+ "\"north\": \"%s\",\n"
						+ "\"east\": \"%s\",\n"
						+ "\"south\": \"%s\",\n"
						+ "\"west\": \"%s\"\n"
					+ "}\n"
				+ "}",
				bl.getTexture(EnumFacing.UP), bl.getTexture(EnumFacing.DOWN), bl.getTexture(EnumFacing.NORTH), bl.getTexture(EnumFacing.EAST), bl.getTexture(EnumFacing.SOUTH), bl.getTexture(EnumFacing.WEST));
		}
			return String.format("{\n"
									+ "\"parent\": \"block/cube_all\",\n"
									+ "\"textures\": {\n"
										+ "\"all\": \"%s:blocks/%s\"\n"
									+ "}\n"
								+ "}",
								modid, block.getRegistryName().getResourcePath());
	}
	
	private static String formatBlockStateJson(String modid, Block block) {
		if(block instanceof ICustomJson && ((ICustomJson) block).getJson(JsonType.BLOCKSTATE) != null)
		{
			return ((ICustomJson) block).getJson(JsonType.BLOCKSTATE);
		}
		return String.format("{\n"
							+ "\"variants\": {\n"
							+ "\"normal\": { \"model\":\"%s:%s\" }\n"
							+ "}\n"
							+ "}", modid, block.getRegistryName().getResourcePath());
	}
	
	//@formatter:on
}
