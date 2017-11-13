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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.Fire;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Burning;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Charm;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.LockedFloor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Ooze;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Sleep;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Terror;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Pushing;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.ShadowParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.keys.SkeletonKey;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Grim;
import com.lovecraftpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.BurningFistSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.LarvaSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.RottingFistSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.YogSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.BossHealthBar;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class Yog extends Mob {
	
	{
		spriteClass = YogSprite.class;
		
		HP = HT = 300;
		
		EXP = 50;
		
		state = PASSIVE;

		properties.add(Property.BOSS);
		properties.add(Property.IMMOVABLE);
		properties.add(Property.DEMONIC);
	}
	
	public Yog() {
		super();
	}
	
	public void spawnFists() {
		RottingFist fist1 = new RottingFist();
		BurningFist fist2 = new BurningFist();
		
		do {
			fist1.pos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			fist2.pos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
		} while (!Dungeon.level.passable[fist1.pos] || !Dungeon.level.passable[fist2.pos] || fist1.pos == fist2.pos);
		
		GameScene.add( fist1 );
		GameScene.add( fist2 );

		notice();
	}

	@Override
	protected boolean act() {
		//heals 1 health per turn
		HP = Math.min( HT, HP+1 );

		return super.act();
	}

	@Override
	public void damage( int dmg, Object src ) {

		HashSet<Mob> fists = new HashSet<>();

		for (Mob mob : Dungeon.level.mobs)
			if (mob instanceof RottingFist || mob instanceof BurningFist)
				fists.add( mob );

		for (Mob fist : fists)
			fist.beckon( pos );

		dmg >>= fists.size();
		
		super.damage( dmg, src );


		LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
		if (lock != null) lock.addTime(dmg*0.5f);

	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {

		ArrayList<Integer> spawnPoints = new ArrayList<>();
		
		for (int i=0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
				spawnPoints.add( p );
			}
		}
		
		if (spawnPoints.size() > 0) {
			Larva larva = new Larva();
			larva.pos = Random.element( spawnPoints );
			
			GameScene.add( larva );
			Actor.addDelayed( new Pushing( larva, pos, larva.pos ), -1 );
		}

		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof BurningFist || mob instanceof RottingFist || mob instanceof Larva) {
				mob.aggro( enemy );
			}
		}

		return super.defenseProc(enemy, damage);
	}
	
	@Override
	public void beckon( int cell ) {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void die( Object cause ) {

		for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
			if (mob instanceof BurningFist || mob instanceof RottingFist) {
				mob.die( cause );
			}
		}
		
		GameScene.bossSlain();
		Dungeon.level.drop( new SkeletonKey( Dungeon.depth ), pos ).sprite.drop();
		super.die( cause );
		
		yell( Messages.get(this, "defeated") );
	}
	
	@Override
	public void notice() {
		super.notice();
		BossHealthBar.assignBoss(this);
		yell( Messages.get(this, "notice") );
	}
	
	{
		
		immunities.add( Grim.class );
		immunities.add( Terror.class );
		immunities.add( Amok.class );
		immunities.add( Charm.class );
		immunities.add( Sleep.class );
		immunities.add( Burning.class );
		immunities.add( ToxicGas.class );
		immunities.add( ScrollOfPsionicBlast.class );
		immunities.add( Vertigo.class );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		BossHealthBar.assignBoss(this);
	}

	public static class RottingFist extends Mob {
	
		private static final int REGENERATION	= 4;
		
		{
			spriteClass = RottingFistSprite.class;
			
			HP = HT = 300;
			defenseSkill = 25;
			
			EXP = 0;
			
			state = WANDERING;

			properties.add(Property.BOSS);
			properties.add(Property.DEMONIC);
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 36;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 20, 50 );
		}
		
		@Override
		public int drRoll() {
			return Random.NormalIntRange(0, 15);
		}
		
		@Override
		public int attackProc( Char enemy, int damage ) {
			damage = super.attackProc( enemy, damage );
			
			if (Random.Int( 3 ) == 0) {
				Buff.affect( enemy, Ooze.class );
				enemy.sprite.burst( 0xFF000000, 5 );
			}
			
			return damage;
		}
		
		@Override
		public boolean act() {
			
			if (Dungeon.level.water[pos] && HP < HT) {
				sprite.emitter().burst( ShadowParticle.UP, 2 );
				HP += REGENERATION;
			}
			
			return super.act();
		}

		@Override
		public void damage(int dmg, Object src) {
			super.damage(dmg, src);
			LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
			if (lock != null) lock.addTime(dmg*0.5f);
		}
		
		{
			resistances.add( ToxicGas.class );
			resistances.add( Grim.class );
			resistances.add( ScrollOfPsionicBlast.class );
		}
		
		{
			immunities.add( Amok.class );
			immunities.add( Sleep.class );
			immunities.add( Terror.class );
			immunities.add( Poison.class );
			immunities.add( Vertigo.class );
		}
	}
	
	public static class BurningFist extends Mob {
		
		{
			spriteClass = BurningFistSprite.class;
			
			HP = HT = 200;
			defenseSkill = 25;
			
			EXP = 0;
			
			state = WANDERING;

			properties.add(Property.BOSS);
			properties.add(Property.DEMONIC);
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 36;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 26, 32 );
		}
		
		@Override
		public int drRoll() {
			return Random.NormalIntRange(0, 15);
		}
		
		@Override
		protected boolean canAttack( Char enemy ) {
			return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
		}
		
		@Override
		public boolean attack( Char enemy ) {
			
			if (!Dungeon.level.adjacent( pos, enemy.pos )) {
				spend( attackDelay() );
				
				if (hit( this, enemy, true )) {
					
					int dmg =  damageRoll();
					enemy.damage( dmg, this );
					
					enemy.sprite.bloodBurstA( sprite.center(), dmg );
					enemy.sprite.flash();
					
					if (!enemy.isAlive() && enemy == Dungeon.hero) {
						Dungeon.fail( getClass() );
						GLog.n( Messages.get(Char.class, "kill", name) );
					}
					return true;
					
				} else {
					
					enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
					return false;
				}
			} else {
				return super.attack( enemy );
			}
		}
		
		@Override
		public boolean act() {
			
			for (int i=0; i < PathFinder.NEIGHBOURS9.length; i++) {
				GameScene.add( Blob.seed( pos + PathFinder.NEIGHBOURS9[i], 2, Fire.class ) );
			}
			
			return super.act();
		}

		@Override
		public void damage(int dmg, Object src) {
			super.damage(dmg, src);
			LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
			if (lock != null) lock.addTime(dmg*0.5f);
		}
		
		{
			resistances.add( ToxicGas.class );
			resistances.add( Grim.class );

		}
		
		{
			immunities.add( Amok.class );
			immunities.add( Sleep.class );
			immunities.add( Terror.class );
			immunities.add( Burning.class );
			immunities.add( ScrollOfPsionicBlast.class );
			immunities.add( Vertigo.class );
		}
	}
	
	public static class Larva extends Mob {
		
		{
			spriteClass = LarvaSprite.class;
			
			HP = HT = 25;
			defenseSkill = 20;
			
			EXP = 0;
			
			state = HUNTING;

			properties.add(Property.DEMONIC);
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 30;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 22, 30 );
		}
		
		@Override
		public int drRoll() {
			return Random.NormalIntRange(0, 8);
		}

	}
}