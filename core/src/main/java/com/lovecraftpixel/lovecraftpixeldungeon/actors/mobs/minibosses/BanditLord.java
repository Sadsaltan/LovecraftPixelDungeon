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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.minibosses;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Blindness;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.criminals.Thief;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Generator;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Dividing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Friendship;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Glowing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Holy;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Midas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Sting;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.TimeFreezing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.BanditLordSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class BanditLord extends Thief {
	
	public Item item;
	
	{
		spriteClass = BanditLordSprite.class;

		properties.add(Property.HUMANOID);
		properties.add(Property.MINIBOSS);

		//1 in 30 chance to be a crazy bandit, equates to overall 1/90 chance.
		lootChance = 0.333f;
	}

	protected Weapon weapon;

	public BanditLord() {
		super();

		do {
			weapon = (Weapon) Generator.random( Generator.Category.WEAPON );
		} while (!(weapon instanceof MeleeWeapon) || weapon.cursed || (weapon.enchantment instanceof TimeFreezing)
				|| (weapon.enchantment instanceof Dividing)
				|| (weapon.enchantment instanceof Friendship)
				|| (weapon.enchantment instanceof Glowing)
				|| (weapon.enchantment instanceof Holy)
				|| (weapon.enchantment instanceof com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Hunting)
				|| (weapon.enchantment instanceof Midas)
				|| (weapon.enchantment instanceof Sting));

		weapon.enchant( Weapon.Enchantment.random() );
	}

	private static final String WEAPON	= "weapon";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( WEAPON, weapon );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		weapon = (Weapon)bundle.get( WEAPON );
	}

	@Override
	public int damageRoll() {
		return weapon.damageRoll(this);
	}

	@Override
	public int attackSkill( Char target ) {
		return (int)((9 + Dungeon.depth) * weapon.accuracyFactor(this));
	}

	@Override
	protected float attackDelay() {
		return weapon.speedFactor( this );
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Dungeon.level.distance( pos, enemy.pos ) <= weapon.reachFactor(this);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, Dungeon.depth + weapon.defenseFactor(this));
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );

		if (item == null && enemy instanceof Hero && steal( (Hero)enemy )) {
			state = FLEEING;
		}

		return weapon.proc( this, enemy, damage );
	}
	
	@Override
	protected boolean steal( Hero hero ) {
		if (super.steal( hero )) {
			
			Buff.prolong( hero, Blindness.class, Random.Int( 2, 5 ) );
			Buff.affect( hero, Poison.class ).set(Random.Int(5, 7) * Poison.durationFactor(enemy));
			Buff.prolong( hero, Cripple.class, Random.Int( 3, 8 ) );
			Dungeon.observe();
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void die( Object cause ) {
		super.die( cause );
	}
}
