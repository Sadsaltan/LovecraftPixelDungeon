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

import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Bleeding;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ThrowingKnife extends MissileWeapon {

	{
		image = ItemSpriteSheet.THROWING_KNIVE;

	}

	@Override
	public int min(int lvl) {
		return 2;
	}

	@Override
	public int max(int lvl) {
		return 10;
	}

	@Override
	public int STRReq(int lvl) {
		return 11;
	}

	public ThrowingKnife() {
		this( 1 );
	}

	public ThrowingKnife(int number ) {
		super();
		quantity = number;
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		Buff.affect( defender, Bleeding.class ).set( damage );
		return super.proc( attacker, defender, damage );
	}
	
	@Override
	public Item random() {
		quantity = Random.Int( 5, 12 );
		return this;
	}
	
	@Override
	public int price() {
		return 15 * quantity;
	}
}
