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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.npcs;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.VenomGas;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Burning;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.MirrorSprite;
import com.watabou.utils.Bundle;

public class MirrorImage extends NPC {
	
	{
		spriteClass = MirrorSprite.class;
		
		alignment = Alignment.ALLY;
		state = HUNTING;
	}
	
	public int tier;
	
	private int attack;
	private int damage;
	
	private static final String TIER	= "tier";
	private static final String ATTACK	= "attack";
	private static final String DAMAGE	= "damage";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( TIER, tier );
		bundle.put( ATTACK, attack );
		bundle.put( DAMAGE, damage );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		tier = bundle.getInt( TIER );
		attack = bundle.getInt( ATTACK );
		damage = bundle.getInt( DAMAGE );
	}
	
	public void duplicate( Hero hero ) {
		hero.reduceMentalHealth(1);
		tier = hero.tier();
		attack = hero.attackSkill( hero );
		damage = hero.damageRoll();
	}
	
	@Override
	public int attackSkill( Char target ) {
		return attack;
	}
	
	@Override
	public int damageRoll() {
		return damage;
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );

		destroy();
		sprite.die();
		
		return damage;
	}
	
	@Override
	public CharSprite sprite() {
		CharSprite s = super.sprite();
		((MirrorSprite)s).updateArmor( tier );
		return s;
	}

	@Override
	public boolean interact() {
		
		int curPos = pos;
		
		moveSprite( pos, Dungeon.hero.pos );
		move( Dungeon.hero.pos );
		
		Dungeon.hero.sprite.move( Dungeon.hero.pos, curPos );
		Dungeon.hero.move( curPos );
		
		Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
		Dungeon.hero.busy();

		return true;
	}
	
	{
		immunities.add( ToxicGas.class );
		immunities.add( VenomGas.class );
		immunities.add( Burning.class );
	}
}