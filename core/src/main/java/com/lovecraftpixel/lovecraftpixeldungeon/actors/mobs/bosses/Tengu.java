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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.bosses;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.Badges;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.ToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.LockedFloor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.HeroSubClass;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Speck;
import com.lovecraftpixel.lovecraftpixeldungeon.items.TomeOfMastery;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.LloydsBeacon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments.Grim;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Level;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.PrisonBossLevel;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Terrain;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.GrippingTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.TenguSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.BossHealthBar;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Tengu extends Mob {
	
	{
		spriteClass = TenguSprite.class;
		
		HP = HT = 120;
		EXP = 20;
		defenseSkill = 20;

		HUNTING = new Hunting();

		flying = true; //doesn't literally fly, but he is fleet-of-foot enough to avoid hazards

		properties.add(Property.BOSS);
		properties.add(Property.HUMANOID);

		horrorlvl = 2;
	}
	
	@Override
	protected void onAdd() {
		//when he's removed and re-added to the fight, his time is always set to now.
		spend(-cooldown());
		super.onAdd();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 6, 20 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 20;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}

	@Override
	public void damage(int dmg, Object src) {

		int beforeHitHP = HP;
		super.damage(dmg, src);
		dmg = beforeHitHP - HP;

		LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
		if (lock != null) {
			int multiple = beforeHitHP > HT/2 ? 1 : 4;
			lock.addTime(dmg*multiple);
		}

		//phase 2 of the fight is over
		if (HP == 0 && beforeHitHP <= HT/2) {
			((PrisonBossLevel)Dungeon.level).progress();
			return;
		}

		int hpBracket = beforeHitHP > HT/2 ? 12 : 20;

		//phase 1 of the fight is over
		if (beforeHitHP > HT/2 && HP <= HT/2){
			HP = (HT/2)-1;
			yell(Messages.get(this, "interesting"));
			((PrisonBossLevel)Dungeon.level).progress();
			BossHealthBar.bleed(true);

		//if tengu has lost a certain amount of hp, jump
		} else if (beforeHitHP / hpBracket != HP / hpBracket) {
			jump();
		}
	}

	@Override
	public boolean isAlive() {
		return Dungeon.level.mobs.contains(this); //Tengu has special death rules, see prisonbosslevel.progress()
	}

	@Override
	public void die( Object cause ) {
		
		if (Dungeon.hero.subClass == HeroSubClass.NONE) {
			Dungeon.level.drop( new TomeOfMastery(), pos ).sprite.drop();
		}
		
		GameScene.bossSlain();
		super.die( cause );
		
		Badges.validateBossSlain();

		LloydsBeacon beacon = Dungeon.hero.belongings.getItem(LloydsBeacon.class);
		if (beacon != null) {
			beacon.upgrade();
		}
		
		yell( Messages.get(this, "defeated") );
	}

	@Override
	protected boolean canAttack( Char enemy ) {
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}

	//tengu's attack is always visible
	@Override
	protected boolean doAttack(Char enemy) {
		if (enemy == Dungeon.hero)
			Dungeon.hero.resting = false;
		sprite.attack( enemy.pos );
		spend( attackDelay() );
		return true;
}

	private void jump() {
		
		Level level = Dungeon.level;
		if (enemy == null) enemy = chooseEnemy();
		if (enemy == null) return;

		int newPos;
		//if we're in phase 1, want to warp around within the room
		if (HP > HT/2) {
			
			//place new traps
			for (int i=0; i < 4; i++) {
				int trapPos;
				do {
					trapPos = Random.Int( level.length() );
				} while (level.map[trapPos] != Terrain.INACTIVE_TRAP
						&& level.map[trapPos] != Terrain.TRAP);
				
				if (level.map[trapPos] == Terrain.INACTIVE_TRAP) {
					level.setTrap( new GrippingTrap().reveal(), trapPos );
					Level.set( trapPos, Terrain.TRAP );
					ScrollOfMagicMapping.discover( trapPos );
				}
			}
			
			int tries = 50;
			do {
				newPos = Random.IntRange(3, 7) + 32*Random.IntRange(26, 30);
			} while ( (level.adjacent(newPos, enemy.pos) || Actor.findChar(newPos) != null)
					&& --tries > 0);
			if (tries <= 0) return;

		//otherwise go wherever, as long as it's a little bit away
		} else {
			do {
				newPos = Random.Int(level.length());
			} while (
					level.solid[newPos] ||
					level.distance(newPos, enemy.pos) < 8 ||
					Actor.findChar(newPos) != null);
		}
		
		if (level.heroFOV[pos]) CellEmitter.get( pos ).burst( Speck.factory( Speck.WOOL ), 6 );

		sprite.move( pos, newPos );
		move( newPos );
		
		if (level.heroFOV[newPos]) CellEmitter.get( newPos ).burst( Speck.factory( Speck.WOOL ), 6 );
		Sample.INSTANCE.play( Assets.SND_PUFF );
		
		spend( 1 / speed() );
	}
	
	@Override
	public void notice() {
		super.notice();
		BossHealthBar.assignBoss(this);
		if (HP <= HT/2) BossHealthBar.bleed(true);
		if (HP == HT) {
			yell(Messages.get(this, "notice_mine", Dungeon.hero.givenName()));
		} else {
			yell(Messages.get(this, "notice_face", Dungeon.hero.givenName()));
		}
	}
	
	{
		resistances.add( ToxicGas.class );
		resistances.add( Poison.class );
		resistances.add( Grim.class );
		resistances.add( ScrollOfPsionicBlast.class );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		BossHealthBar.assignBoss(this);
		if (HP <= HT/2) BossHealthBar.bleed(true);
	}

	//tengu is always hunting
	private class Hunting extends Mob.Hunting{

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			enemySeen = enemyInFOV;
			if (enemyInFOV && !isCharmedBy( enemy ) && canAttack( enemy )) {

				return doAttack( enemy );

			} else {

				if (enemyInFOV) {
					target = enemy.pos;
				} else {
					chooseEnemy();
					if (enemy != null) {
						target = enemy.pos;
					}
				}

				spend( TICK );
				return true;

			}
		}
	}
}
