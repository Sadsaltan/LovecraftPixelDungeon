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

package com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles;

import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ThrowingStone extends MissileWeapon {

	{
		image = ItemSpriteSheet.THROWING_STONE;

		bones = false; //Finding them in bones would be semi-frequent and disappointing.
	}

	@Override
	public int min(int lvl) {
		return 1;
	}

	@Override
	public int max(int lvl) {
		return 3;
	}

	@Override
	public int STRReq(int lvl) {
		return 9;
	}

	public ThrowingStone() {
		this( 1 );
	}

	public ThrowingStone(int number ) {
		super();
		quantity = number;
	}
	
	@Override
	public Item random() {
		quantity = Random.Int( 10, 20 );
		return this;
	}
	
	@Override
	public int price() {
		return quantity * 1;
	}
}
