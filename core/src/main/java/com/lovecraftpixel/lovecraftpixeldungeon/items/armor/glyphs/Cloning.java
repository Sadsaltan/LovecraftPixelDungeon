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

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Cloning extends Armor.Glyph {

	private static ItemSprite.Glowing LIGHT_BLUE = new ItemSprite.Glowing( 0x19CEDE );
	
	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {

		if (Random.Int(20) == 0) {
			ArrayList<Integer> spawnPoints = new ArrayList<>();

			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = defender.pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (spawnPoints.size() > 0) {

				Mob m;
				if (Random.Int(2) == 0) {
					m = new MirrorImage();
					((MirrorImage) m).duplicate((Hero) defender);

					if (m != null) {
						GameScene.add(m);
						ScrollOfTeleportation.appear(m, Random.element(spawnPoints));
					}

				}
			}
		}

		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return LIGHT_BLUE;
	}
}
