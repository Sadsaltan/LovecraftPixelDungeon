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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.Storm;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Corruption;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Terror;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.ShadowParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Grim;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.KindSpiritSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class KindSpirit extends Mob {

	private static final float SPAWN_DELAY	= 2f;
	
	private int level;
	
	{
		spriteClass = KindSpiritSprite.class;
		
		HP = HT = 1;
		EXP = 0;
		
		flying = true;

		properties.add(Property.UNDEAD);

		horrorlvl = 0;
	}
	
	private static final String LEVEL = "level";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEVEL, level );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		level = bundle.getInt( LEVEL );
		adjustStats( level );
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1 + level/2, 2 + level );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 10 + level;
	}
	
	public void adjustStats( int level ) {
		this.level = level;
		defenseSkill = attackSkill( null ) * 5;
		enemySeen = true;
	}

	@Override
	public boolean reset() {
		state = WANDERING;
		return true;
	}
	
	public static KindSpirit spawnAt(int pos ) {
		if (Dungeon.level.passable[pos] && Actor.findChar( pos ) == null) {
			
			KindSpirit w = new KindSpirit();
			w.adjustStats( Dungeon.depth );
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add( w, SPAWN_DELAY );
			
			w.sprite.alpha( 0 );
			w.sprite.parent.add( new AlphaTweener( w.sprite, 1, 0.5f ) );
			
			w.sprite.emitter().burst( ShadowParticle.UP, 2 );

			w.alignment = Alignment.ALLY;
			
			return w;
		} else {
			return null;
		}
	}
	
	{
		immunities.add( Grim.class );
		immunities.add( Terror.class );
		immunities.add( Storm.class );
		immunities.add( Corruption.class );
	}
}
