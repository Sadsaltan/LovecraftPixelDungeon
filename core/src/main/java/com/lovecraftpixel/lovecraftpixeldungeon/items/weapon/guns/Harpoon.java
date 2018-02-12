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

package com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Chains;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Pushing;
import com.lovecraftpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;

public class Harpoon extends GunWeapon {

	{
		image = ItemSpriteSheet.HARPOON;
		LOADING_TIME = 4;
	}

	@Override
	public int min(int lvl) {
		return lvl+3;
	}

	@Override
	public int max(int lvl) {
		return lvl+10;
	}

	@Override
	public int STRReq(int lvl) {
		return 14;
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		chain(defender);
		return super.proc( attacker, defender, damage );
	}

	private boolean chain(final Char defender){
		int target = defender.pos;

		Ballistica chain = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);

		if (chain.collisionPos != defender.pos
				|| chain.path.size() < 2
				|| Dungeon.level.pit[chain.path.get(1)])
			return false;
		else {
			int newPos = -1;
			for (int i : chain.subPath(1, chain.dist)){
				if (!Dungeon.level.solid[i] && Actor.findChar(i) == null){
					newPos = i;
					break;
				}
			}

			if (newPos == -1){
				return false;
			} else {
				final int newPosFinal = newPos;
				curUser.sprite.parent.add(new Chains(curUser.sprite.center(), defender.sprite.center(), new Callback() {
					public void call() {
						Actor.addDelayed(new Pushing(defender, defender.pos, newPosFinal, new Callback(){
							public void call() {
								defender.pos = newPosFinal;
								Dungeon.level.press(newPosFinal, defender);
								Cripple.prolong(defender, Cripple.class, 4f);
							}
						}), -1);
					}
				}));
			}
		}
		return true;
	}
	
	@Override
	public int price() {
		return 100 * quantity;
	}
}
