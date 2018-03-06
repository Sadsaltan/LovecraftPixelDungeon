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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Terrain;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Weight extends Armor.Glyph {

	private static ItemSprite.Glowing BROWN = new ItemSprite.Glowing( 0x775A42 );

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		if(Random.Int( armor.level() + 5 ) >= 4 && !Dungeon.bossLevel()){
			if(Dungeon.level.map[attacker.pos] == Terrain.EMPTY ||
					Dungeon.level.map[attacker.pos] == Terrain.GRASS ||
					Dungeon.level.map[attacker.pos] == Terrain.EMPTY_SP){
				Dungeon.level.set(attacker.pos, Terrain.AVOID);
			}
		}
		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return BROWN;
	}

}

