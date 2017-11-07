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
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class LivingStormvinePlantSprite extends MobSprite {

	public LivingStormvinePlantSprite() {
		super();

		texture(Assets.LIVINGPLANTS);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new MovieClip.Animation(5, true);
		idle.frames(frames, 0+(7*9), 1+(7*9));
		run = new MovieClip.Animation( 15, true);
		run.frames( frames, 0+(7*9), 1+(7*9), 2+(7*9), 3+(7*9), 4+(7*9), 5+(7*9));

		attack = new MovieClip.Animation( 12, false );
		attack.frames( frames, 0+(7*9), 2+(7*9), 3+(7*9), 4+(7*9), 0+(7*9));

		die = new MovieClip.Animation( 5, false);
		die.frames( frames, 6+(7*9), 7+(7*9), 8+(7*9));

		play(idle);
	}
}
