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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Chill;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Frost;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.BlobEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.SnowParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Heap;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.watabou.utils.Random;

public class Freezing extends Blob {
	
	@Override
	protected void evolve() {
		
		boolean[] water = Dungeon.level.water;
		int cell;
		
		Fire fire = (Fire)Dungeon.level.blobs.get( Fire.class );
		
		for (int i = area.left-1; i <= area.right; i++) {
			for (int j = area.top-1; j <= area.bottom; j++) {
				cell = i + j*Dungeon.level.width();
				if (cur[cell] > 0) {
					
					if (fire != null && fire.volume > 0 && fire.cur[cell] > 0){
						fire.clear(cell);
						off[cell] = cur[cell] = 0;
						continue;
					}
					
					Char ch = Actor.findChar( cell );
					if (ch != null && !ch.immunities().contains(this.getClass())) {
						if (ch.buff(Frost.class) != null){
							Buff.affect(ch, Frost.class, 2f);
						} else {
							Buff.affect(ch, Chill.class, water[cell] ? 5f : 3f);
							Chill chill = ch.buff(Chill.class);
							if (chill != null && chill.cooldown() >= 10f){
								Buff.affect(ch, Frost.class, 5f);
							}
						}
					}
					
					Heap heap = Dungeon.level.heaps.get( cell );
					if (heap != null) heap.freeze();
					
					off[cell] = cur[cell] - 1;
					volume += off[cell];
				} else {
					off[cell] = 0;
				}
			}
		}
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start( SnowParticle.FACTORY, 0.05f, 0 );
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
	
	//legacy functionality from before this was a proper blob. Returns true if this cell is visible
	public static boolean affect( int cell, Fire fire ) {
		
		Char ch = Actor.findChar( cell );
		if (ch != null) {
			if (Dungeon.level.water[ch.pos]){
				Buff.prolong(ch, Frost.class, Frost.duration(ch) * Random.Float(5f, 7.5f));
			} else {
				Buff.prolong(ch, Frost.class, Frost.duration(ch) * Random.Float(1.0f, 1.5f));
			}
		}
		
		if (fire != null) {
			fire.clear( cell );
		}
		
		Heap heap = Dungeon.level.heaps.get( cell );
		if (heap != null) {
			heap.freeze();
		}
		
		if (Dungeon.level.heroFOV[cell]) {
			CellEmitter.get( cell ).start( SnowParticle.FACTORY, 0.2f, 6 );
			return true;
		} else {
			return false;
		}
	}
}
