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

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Mimic;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Statue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Thief;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Dividing extends Weapon.Enchantment {

	private static ItemSprite.Glowing XANADU = new ItemSprite.Glowing( 0x738678 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
			// lvl 0 - 20%
			// lvl 1 - 33%
			// lvl 2 - 43%
			int level = Math.max( 0, weapon.level() );

			if (Random.Int( level + 5 ) >= 4) {

				ArrayList<Integer> spawnPoints = new ArrayList<>();

				for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
					int p = defender.pos + PathFinder.NEIGHBOURS8[i];
					if (Actor.findChar( p ) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
						spawnPoints.add( p );
					}
				}

				if (spawnPoints.size() > 0) {

					Mob m = null;
					if (defender instanceof Hero){
						return damage;
					} else {
						//FIXME should probably have a mob property for this
						if (defender.properties().contains(Char.Property.BOSS) || defender.properties().contains(Char.Property.MINIBOSS)
								|| defender instanceof Mimic || defender instanceof Statue){
							m = Dungeon.level.createMob();
						} else {
							try {
								Actor.fixTime();

								m = (Mob)defender.getClass().newInstance();
								for(Buff buff : defender.buffs()){
									m.add(buff);
								}
								Bundle store = new Bundle();
								defender.storeInBundle(store);
								m.restoreFromBundle(store);
								if(defender.HP < 2){
									return damage;
								}
								m.HP = defender.HP/2;

								//If a thief has stolen an item, that item is not duplicated.
								if (m instanceof Thief){
									((Thief) m).item = null;
								}

							} catch (Exception e) {
								LovecraftPixelDungeon.reportException(e);
								m = null;
							}
						}

					}

					if (m != null) {
						GameScene.add(m);
						ScrollOfTeleportation.appear(m, Random.element(spawnPoints));
						defender.HP = defender.HP/2;
						return damage/2;
					}

				}
			}

		return damage;
	}
	
	@Override
	public ItemSprite.Glowing glowing() {
		return XANADU;
	}

}
