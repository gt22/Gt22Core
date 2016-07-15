package com.gt22.gt22core.world;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import com.gt22.gt22core.api.structure.StructureApi;
import cpw.mods.fml.common.IWorldGenerator;

public class Gt22CoreStructureGenerator implements IWorldGenerator
{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		StructureApi.getnerateRandomStrucutre(world, chunkX * 16 + random.nextInt(16), chunkZ * 16 + random.nextInt(16));
	}

}
