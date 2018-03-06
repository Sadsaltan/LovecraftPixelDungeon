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

package com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments;

import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Friendship extends Weapon.Enchantment {

	private static ItemSprite.Glowing PONY = new ItemSprite.Glowing( 0xFE0002 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		if(defender.properties().contains(Char.Property.ANIMAL)) {
			// lvl 0 - 20%
			// lvl 1 - 33%
			// lvl 2 - 43%
			int level = Math.max( 0, weapon.level() );

			if (Random.Int( level + 5 ) >= 4) {
				for(int c : PathFinder.NEIGHBOURS9){
					if(Actor.findChar(attacker.pos + c).alignment == Char.Alignment.ALLY){
						damage = damage * 2;
					}
				}
			}
		}

		return damage;
	}
	
	@Override
	public ItemSprite.Glowing glowing() {
		return PONY;
	}

}
