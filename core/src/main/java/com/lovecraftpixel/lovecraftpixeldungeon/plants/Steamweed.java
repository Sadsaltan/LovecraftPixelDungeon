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

package com.lovecraftpixel.lovecraftpixeldungeon.plants;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Levitation;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.livingplants.LivingPlantSteamweed;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Speck;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfSteam;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.RandomL;

public class Steamweed extends Plant {
	
	{
		image = 14;
	}
	
	@Override
	public void activate() {
		if (RandomL.randomBoolean()){
			spawnLivingPlant(new LivingPlantSteamweed());
		} else {
			effectChar();
		}
	}

	private void effectChar(){
		Char ch = Actor.findChar(pos);

		if (ch == Dungeon.hero) {
			Buff.prolong( ch, Levitation.class, 2f );
			Buff.prolong(ch, Vertigo.class, 2f);
		}

		if (Dungeon.level.heroFOV[pos]) {
			CellEmitter.get( pos ).start( Speck.factory(Speck.STEAM), 0.2f, 6 );
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_STEAMWEED;

			plantClass = Steamweed.class;
			alchemyClass = PotionOfSteam.class;

			bones = true;
		}
	}
}
