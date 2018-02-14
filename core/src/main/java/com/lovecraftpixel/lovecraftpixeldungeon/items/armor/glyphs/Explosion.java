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

package com.lovecraftpixel.lovecraftpixeldungeon.items.armor.glyphs;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.BlastParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.SmokeParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Heap;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Explosion extends Armor.Glyph {

	private static ItemSprite.Glowing ORANGE_RED = new ItemSprite.Glowing( 0xC93102 );
	
	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {

		int level = Math.max( 0, armor.level() );

		if (Random.Int( level + 5 ) >= 4){
			if (Random.Int( 2 ) == 0) {
				explode(attacker.pos, attacker);
			}
		}
		
		return damage;
	}

	public void explode(int cell, Char character){
		Sample.INSTANCE.play( Assets.SND_BLAST );

		if (Dungeon.level.heroFOV[cell]) {
			CellEmitter.center( cell ).burst( BlastParticle.FACTORY, 30 );
		}

		boolean terrainAffected = false;
		for (int n : PathFinder.NEIGHBOURS9) {
			int c = cell + n;
			if (c >= 0 && c < Dungeon.level.length()) {
				if (Dungeon.level.heroFOV[c]) {
					CellEmitter.get( c ).burst( SmokeParticle.FACTORY, 4 );
				}

				if (Dungeon.level.flamable[c]) {
					Dungeon.level.destroy( c );
					GameScene.updateMap( c );
					terrainAffected = true;
				}

				//destroys items / triggers bombs caught in the blast.
				Heap heap = Dungeon.level.heaps.get( c );
				if(heap != null)
					heap.explode();

				Char ch = Actor.findChar( c );
				if (ch != null && ch != character) {
					//those not at the center of the blast take damage less consistently.
					int minDamage = c == cell ? Dungeon.depth+5 : 1;
					int maxDamage = 10 + Dungeon.depth * 2;

					int dmg = Random.NormalIntRange( minDamage, maxDamage ) - ch.drRoll();
					if (dmg > 0) {
						ch.damage( dmg, this );
					}
				}
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return ORANGE_RED;
	}
}
