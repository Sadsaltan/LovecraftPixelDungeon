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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Blindness;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.BlobEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.SporeParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.RandomL;
import com.watabou.utils.Random;

public class Spores extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		Char ch;
		int cell;

		for (int i = area.left; i < area.right; i++){
			for (int j = area.top; j < area.bottom; j++){
				cell = i + j*Dungeon.level.width();
				if (cur[cell] > 0 && (ch = Actor.findChar( cell )) != null) {
					if (!ch.immunities().contains(this.getClass())) {
						Buff.prolong(ch, Blindness.class, 2);
						if(ch instanceof Hero){
							if(RandomL.randomBoolean()){
								((Hero) ch).reduceMentalHealth(1);
							} else {
								((Hero) ch).loseKnowl(1);
							}
						}
						if(!(ch instanceof Hero)){
							ch.resistances().add(this.getClass());
						}
						ch.sprite.burst(0xffffff, Random.Int(10, 50));
					}
				}
			}
		}
	}

	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );

		emitter.pour( SporeParticle.FACTORY, 0.2f );
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}