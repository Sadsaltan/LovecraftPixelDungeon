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

package com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns;

import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;

public class Luger extends GunWeapon {

	{
		image = ItemSpriteSheet.LUGER;
		LOADING_TIME = 5;
	}

	@Override
	public int min(int lvl) {
		return lvl+3;
	}

	@Override
	public int max(int lvl) {
		return lvl+4;
	}

	@Override
	public int STRReq(int lvl) {
		return 10;
	}
	
	@Override
	public int price() {
		return 1488 * quantity;
	}
}
