/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * Lovecraft Pixel Dungeon
 * Copyright (C) 2016-2017 Leon Horn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This Program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without eben the implied warranty of
 * GNU General Public License for more details.
 *
 * You should have have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses>
 */

package com.lovecraftpixel.lovecraftpixeldungeon.effects;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.watabou.noosa.Image;

public class BannerSprites {

	public enum  Type {
		PIXEL_DUNGEON,
		BOSS_SLAIN,
		GAME_OVER,
		SELECT_YOUR_HERO,
		PIXEL_DUNGEON_SIGNS
	};

	public static Image get( Type type ) {
		Image icon = new Image( Assets.BANNERS );
		switch (type) {
			case PIXEL_DUNGEON:
				icon.frame( icon.texture.uvRect( 0, 0, 132, 90 ) );
				break;
			case BOSS_SLAIN:
				icon.frame( icon.texture.uvRect( 0, 90, 128, 125 ) );
				break;
			case GAME_OVER:
				icon.frame( icon.texture.uvRect( 0, 125, 128, 160 ) );
				break;
			case SELECT_YOUR_HERO:
				icon.frame( icon.texture.uvRect( 0, 160, 128, 181 ) );
				break;
			case PIXEL_DUNGEON_SIGNS:
				icon.frame( icon.texture.uvRect( 133, 0, 255, 90 ) );
				break;
		}
		return icon;
	}
}
