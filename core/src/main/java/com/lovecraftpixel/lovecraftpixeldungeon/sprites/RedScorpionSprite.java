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
import com.watabou.noosa.TextureFilm;

public class RedScorpionSprite extends ScorpioSprite {

	public RedScorpionSprite() {
		super();
		
		texture( Assets.SCORPIO );
		
		TextureFilm frames = new TextureFilm( texture, 18, 17 );
		
		idle = new Animation( 12, true );
		idle.frames( frames, 14+14, 14+14, 14+14, 14+14, 14+14, 14+14, 14+14, 14+14, 15+14, 16+14, 15+14, 16+14, 15+14, 16+14 );
		
		run = new Animation( 4, true );
		run.frames( frames, 19+14, 20+14 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 14+14, 17+14, 18+14 );
		
		zap = attack.clone();
		
		die = new Animation( 12, false );
		die.frames( frames, 14+14, 21+14, 22+14, 23+14, 24+14 );
		
		play( idle );
	}
	
	@Override
	public int blood() {
		return 0xFF66FF22;
	}
}
