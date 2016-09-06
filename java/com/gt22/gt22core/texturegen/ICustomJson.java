package com.gt22.gt22core.texturegen;

/**
 * Can be used for special blocks/items that require special jsons
 */
public interface ICustomJson {

	public static enum JsonType
	{
		ITEM,
		BLOCK,
		BLOCKSTATE
	}
	
	/**
	 * Return the full json of following type or null
	 * if null returned default json will be used
	 * @param type
	 * @return
	 */
	public String getJson(JsonType type);
}
