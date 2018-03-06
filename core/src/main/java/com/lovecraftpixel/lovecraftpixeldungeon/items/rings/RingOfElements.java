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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Burning;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Venom;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.bosses.Yog;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.eyes.Eye;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.shamans.Shaman;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.warlocks.Warlock;
import com.watabou.utils.Random;

import java.util.HashSet;

public class RingOfElements extends Ring {
	
	@Override
	protected RingBuff buff( ) {
		return new Resistance();
	}

	private static final HashSet<Class> EMPTY = new HashSet<>();
	public static final HashSet<Class> FULL = new HashSet<>();
	static {
		FULL.add( Burning.class );
		FULL.add( ToxicGas.class );
		FULL.add( Poison.class );
		FULL.add( Venom.class );
		FULL.add( Shaman.class );
		FULL.add( Warlock.class );
		FULL.add( Eye.class );
		FULL.add( Yog.BurningFist.class );
	}
	
	public static HashSet<Class> resistances( Char target ){
		if (Random.Int( getBonus(target, Resistance.class) + 2 ) >= 2) {
			return FULL;
		} else {
			return EMPTY;
		}
	}
	
	public static float durationFactor( Char target ){
		int level = getBonus( target, Resistance.class);
		return level <= 0 ? 1 : (1 + 0.5f * level) / (1 + level);
	}
	
	public class Resistance extends RingBuff {
		
		public float durationFactor() {
			return level() < 0 ? 1 : (1 + 0.5f * level()) / (1 + level());
		}
	}
}
