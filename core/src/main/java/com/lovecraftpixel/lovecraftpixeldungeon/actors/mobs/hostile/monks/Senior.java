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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.monks;

import com.lovecraftpixel.lovecraftpixeldungeon.Badges;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Paralysis;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.SeniorSprite;
import com.watabou.utils.Random;

public class Senior extends Monk {

	{
		spriteClass = SeniorSprite.class;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 16, 24 );
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int( 10 ) == 0) {
			Buff.prolong( enemy, Paralysis.class, 1.1f );
		}
		return super.attackProc( enemy, damage );
	}
	
	@Override
	public void die( Object cause ) {
		super.die( cause );
		Badges.validateRare( this );
	}
}
