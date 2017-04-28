package com.puyixiaowo.tnews.enums;

/**
 * @author huangfeihong
 * @date 2017-03-08 10:34
 */
public enum FavoriteType {
	NEWS(1, "新闻");

	FavoriteType(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public int type;
	public String description;

	public static FavoriteType getType(Integer type) {
		for (FavoriteType favoriteType : FavoriteType.values()) {
			if (type == favoriteType.type) {
				return favoriteType;
			}
		}
		return null;
	}

}
