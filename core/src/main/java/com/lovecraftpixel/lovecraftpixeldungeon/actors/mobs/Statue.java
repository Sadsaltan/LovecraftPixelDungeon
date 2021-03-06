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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Generator;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon.Enchantment;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Dividing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Friendship;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Glowing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Grim;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Holy;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Midas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Sting;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.TimeFreezing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Vampiric;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.lovecraftpixel.lovecraftpixeldungeon.journal.Notes;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.StatueSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Statue extends Mob {
	
	{
		spriteClass = StatueSprite.class;

		EXP = 0;
		state = PASSIVE;

		horrorlvl = 3;
	}
	
	protected Weapon weapon;
	
	public Statue() {
		super();
		
		do {
			weapon = (Weapon)Generator.random( Generator.Category.WEAPON );
		} while (!(weapon instanceof MeleeWeapon) || weapon.cursed || (weapon.enchantment instanceof TimeFreezing)
				|| (weapon.enchantment instanceof Dividing)
				|| (weapon.enchantment instanceof Friendship)
				|| (weapon.enchantment instanceof Glowing)
				|| (weapon.enchantment instanceof Holy)
				|| (weapon.enchantment instanceof com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Hunting)
				|| (weapon.enchantment instanceof Midas)
				|| (weapon.enchantment instanceof Sting));
		
		weapon.enchant( Enchantment.random() );
		
		HP = HT = 15 + Dungeon.depth * 5;
		defenseSkill = 4 + Dungeon.depth;
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
	protected boolean act() {
		if (Dungeon.level.heroFOV[pos]) {
			Notes.add( Notes.Landmark.STATUE );
		}
		return super.act();
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
	public void damage( int dmg, Object src ) {

		if (state == PASSIVE) {
			state = HUNTING;
		}
		
		super.damage( dmg, src );
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		return weapon.proc( this, enemy, damage );
	}
	
	@Override
	public void beckon( int cell ) {
		// Do nothing
	}
	
	@Override
	public void die( Object cause ) {
		weapon.identify();
		Dungeon.level.drop( weapon, pos ).sprite.drop();
		super.die( cause );
	}
	
	@Override
	public void destroy() {
		Notes.remove( Notes.Landmark.STATUE );
		super.destroy();
	}
	
	@Override
	public boolean reset() {
		state = PASSIVE;
		return true;
	}

	@Override
	public String description() {
		return Messages.get(this, "desc", weapon.name());
	}
	
	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		resistances.add(Grim.class);
	}
	
	{
		immunities.add( Vampiric.class );
	}
}
