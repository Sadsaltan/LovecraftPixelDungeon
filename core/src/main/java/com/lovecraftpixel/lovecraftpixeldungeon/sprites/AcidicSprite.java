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

public class AcidicSprite extends ScorpioSprite {
	
	public AcidicSprite() {
		super();
		
		texture( Assets.SCORPIO );
		
		TextureFilm frames = new TextureFilm( texture, 18, 17 );
		
		idle = new Animation( 12, true );
		idle.frames( frames, 14, 14, 14, 14, 14, 14, 14, 14, 15, 16, 15, 16, 15, 16 );
		
		run = new Animation( 4, true );
		run.frames( frames, 19, 20 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 14, 17, 18 );
		
		zap = attack.clone();
		
		die = new Animation( 12, false );
		die.frames( frames, 14, 21, 22, 23, 24 );
		
		play( idle );
	}
	
	@Override
	public int blood() {
		return 0xFF66FF22;
	}
}
