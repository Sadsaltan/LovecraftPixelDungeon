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

package com.lovecraftpixel.lovecraftpixeldungeon.levels.traps;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Bleeding;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Wound;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Level;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Terrain;
import com.watabou.noosa.audio.Sample;

public class GrippingTrap extends Trap {

	{
		color = GREY;
		shape = DOTS;
	}
	
	@Override
	public void trigger() {
		if (Dungeon.level.heroFOV[pos]){
			Sample.INSTANCE.play(Assets.SND_TRAP);
		}
		//this trap is not disarmed by being triggered
		reveal();
		Level.set(pos, Terrain.TRAP);
		activate();
	}

	@Override
	public void activate() {

		Char c = Actor.findChar( pos );

		if (c != null) {
			int damage = Math.max( 0,  (2 + Dungeon.depth) - c.drRoll() );
			Buff.affect( c, Bleeding.class ).set( damage );
			Buff.prolong( c, Cripple.class, Cripple.DURATION);
			Wound.hit( c );
		} else {
			Wound.hit( pos );
		}

	}
}
