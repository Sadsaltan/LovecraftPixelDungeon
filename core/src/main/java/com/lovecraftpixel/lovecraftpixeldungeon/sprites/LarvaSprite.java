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
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Splash;
import com.watabou.noosa.TextureFilm;

public class LarvaSprite extends MobSprite {
	
	public LarvaSprite() {
		super();
		
		texture( Assets.LARVA );
		
		TextureFilm frames = new TextureFilm( texture, 12, 8 );
		
		idle = new Animation( 5, true );
		idle.frames( frames, 4, 4, 4, 4, 4, 5, 5 );
		
		run = new Animation( 12, true );
		run.frames( frames, 0, 1, 2, 3 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 6, 5, 7 );
		
		die = new Animation( 10, false );
		die.frames( frames, 8 );
		
		play( idle );
	}
	
	@Override
	public int blood() {
		return 0xbbcc66;
	}
	
	@Override
	public void die() {
		Splash.at( center(), blood(), 10 );
		super.die();
	}
}
