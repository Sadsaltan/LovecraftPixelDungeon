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

package com.lovecraftpixel.lovecraftpixeldungeon.sprites;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.LeafParticle;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;

public class GardnerSprite extends MobSprite {

	public GardnerSprite() {
		super();
		
		texture( Assets.GARDNER );
		
		TextureFilm frames = new TextureFilm( texture, 12, 14 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 3, 2, 1 );
		
		run = new Animation( 20, true );
		run.frames( frames, 0 );
		
		die = new Animation( 20, false );
		die.frames( frames, 0 );
		
		play( idle );
	}
	
	@Override
	public void die() {
		super.die();

		emitter().start( LeafParticle.GENERAL, 0.03f, 60 );

		if (visible) {
			Sample.INSTANCE.play( Assets.SND_BURNING );
		}
	}

}
