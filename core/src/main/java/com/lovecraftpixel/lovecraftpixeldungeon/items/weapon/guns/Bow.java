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

import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Slow;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;

public class Bow extends GunWeapon {

	{
		image = ItemSpriteSheet.BOW;
		LOADING_TIME = 1;
	}

	@Override
	public int min(int lvl) {
		return lvl+1;
	}

	@Override
	public int max(int lvl) {
		return  4 + lvl;
	}

	@Override
	public int STRReq(int lvl) {
		return 8;
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		Buff.prolong( defender, Slow.class, Slow.duration(defender) );
		return super.proc( attacker, defender, damage );
	}
	
	@Override
	public int price() {
		return 100 * quantity;
	}
}
