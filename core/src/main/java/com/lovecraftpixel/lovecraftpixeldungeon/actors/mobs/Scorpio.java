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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Cripple;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Light;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.food.MysteryMeat;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfHealing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Vampiric;
import com.lovecraftpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ScorpioSprite;
import com.watabou.utils.Random;

public class Scorpio extends Mob {
	
	{
		spriteClass = ScorpioSprite.class;
		
		HP = HT = 95;
		defenseSkill = 24;
		viewDistance = Light.DISTANCE;
		
		EXP = 14;
		maxLvl = 25;
		
		loot = new PotionOfHealing();
		lootChance = 0.2f;

		properties.add(Property.DEMONIC);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 26, 36 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 36;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 16);
	}
	
	@Override
	protected boolean canAttack( Char enemy ) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return !Dungeon.level.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int( 2 ) == 0) {
			Buff.prolong( enemy, Cripple.class, Cripple.DURATION );
		}
		
		return damage;
	}
	
	@Override
	protected boolean getCloser( int target ) {
		if (state == HUNTING) {
			return enemySeen && getFurther( target );
		} else {
			return super.getCloser( target );
		}
	}
	
	@Override
	protected Item createLoot() {
		//5/count+5 total chance of getting healing, failing the 2nd roll drops mystery meat instead.
		if (Random.Int( 5 + Dungeon.LimitedDrops.SCORPIO_HP.count ) < 5) {
			Dungeon.LimitedDrops.SCORPIO_HP.count++;
			return (Item)loot;
		} else {
			return new MysteryMeat();
		}
	}
	
	{
		resistances.add( Vampiric.class );
		resistances.add( Poison.class );
	}
}
