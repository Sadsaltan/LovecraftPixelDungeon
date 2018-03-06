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

public class GreyRatSprite extends MobSprite {

	public GreyRatSprite() {
		super();

		texture( Assets.RAT );

		TextureFilm frames = new TextureFilm( texture, 16, 15 );

		idle = new Animation( 2, true );
		idle.frames( frames, 32+16, 32+16, 32+16, 33+16 );

		run = new Animation( 10, true );
		run.frames( frames, 38+16, 39+16, 40+16, 41+16, 42+16 );

		attack = new Animation( 15, false );
		attack.frames( frames, 34+16, 35+16, 36+16, 37+16, 32+16 );

		die = new Animation( 10, false );
		die.frames( frames, 43+16, 44+16, 45+16, 46+16 );

		play( idle );
	}
}
