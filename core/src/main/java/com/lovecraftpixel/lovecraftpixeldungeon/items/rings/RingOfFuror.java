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

package com.lovecraftpixel.lovecraftpixeldungeon.items.rings;

import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;

public class RingOfFuror extends Ring {

	@Override
	protected RingBuff buff( ) {
		return new Furor();
	}
	
	public static float modifyAttackDelay( float delay, Char target){
		//furor bonus only affects delay after 0.2
		return (float)(0.2 + (delay - 0.2)*Math.pow(0.85, getBonus(target, Furor.class)));
	}

	public class Furor extends RingBuff {
	}
}
