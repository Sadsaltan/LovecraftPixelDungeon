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

package com.lovecraftpixel.lovecraftpixeldungeon.items.potions;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class PotionOfToxicGas extends Potion {

	{
		initials = 11;
	}

	@Override
	public void shatter( int cell ) {

		if (Dungeon.level.heroFOV[cell]) {
			setKnown();

			splash( cell );
			Sample.INSTANCE.play( Assets.SND_SHATTER );
		}

		GameScene.add( Blob.seed( cell, 1000, ToxicGas.class ) );
	}
	
	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
