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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Paralysis;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Pushing;
import com.lovecraftpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class HeavyCrossbow extends GunWeapon {

	{
		image = ItemSpriteSheet.HEAVY_CROSSBOW;
		LOADING_TIME = 7;
	}

	@Override
	public int min(int lvl) {
		return 10;
	}

	@Override
	public int max(int lvl) {
		return 30;
	}

	@Override
	public int STRReq(int lvl) {
		return 17;
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		int oppositeHero = defender.pos + (defender.pos - attacker.pos);
		Ballistica trajectory = new Ballistica(defender.pos, oppositeHero, Ballistica.MAGIC_BOLT);
		throwChar(defender, trajectory, 2);
		return super.proc( attacker, defender, damage );
	}

	public static void throwChar(final Char ch, final Ballistica trajectory, int power){
		int dist = Math.min(trajectory.dist, power);

		if (ch.properties().contains(Char.Property.BOSS))
			dist /= 2;

		if (dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE)) return;

		if (Actor.findChar(trajectory.path.get(dist)) != null){
			dist--;
		}

		final int newPos = trajectory.path.get(dist);

		if (newPos == ch.pos) return;

		final int finalDist = dist;
		final int initialpos = ch.pos;

		Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
			public void call() {
				if (initialpos != ch.pos) {
					//something cased movement before pushing resolved, cancel to be safe.
					ch.sprite.place(ch.pos);
					return;
				}
				ch.pos = newPos;
				if (ch.pos == trajectory.collisionPos) {
					ch.damage(Random.NormalIntRange((finalDist + 1) / 2, finalDist), this);
					Paralysis.prolong(ch, Paralysis.class, Random.NormalIntRange((finalDist + 1) / 2, finalDist));
				}
				Dungeon.level.press(ch.pos, ch);
				if (ch == Dungeon.hero){
					Dungeon.observe();
				}
			}
		}), -1);
	}
	
	@Override
	public int price() {
		return 100 * quantity;
	}
}
