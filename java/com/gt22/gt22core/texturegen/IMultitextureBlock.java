package com.gt22.gt22core.texturegen;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

/**
 * Use this if your block has different textures on different sides
 */
public interface IMultitextureBlock {
	public ResourceLocation getTexture(EnumFacing side);
}
