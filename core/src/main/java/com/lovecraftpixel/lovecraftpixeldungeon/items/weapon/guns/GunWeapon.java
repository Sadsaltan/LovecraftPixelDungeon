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

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.HeroClass;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Beam;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.lovecraftpixel.lovecraftpixeldungeon.mechanics.Ballistica;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.CellSelector;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.ItemsFlavourText;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

abstract public class GunWeapon extends Weapon {

	public String flavourtext;
	public static GunWeapon thisweapon;

	public static final String AC_SHOOT = "SHOOT";

	public static int LOADING_TIME = 1;

	{
		stackable = false;
		RCH = 10;

		defaultAction = AC_SHOOT;
		usesTargeting = true;
		flavourtext = new ItemsFlavourText().getText(this);
		thisweapon = this;
	}

	private static final String FLAVOUR			= "flavour";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put(FLAVOUR, flavourtext);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		flavourtext = bundle.getString(FLAVOUR);
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if(this.isEquipped(hero)){
			actions.add( AC_SHOOT );
		}
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_SHOOT )) {
			GameScene.selectCell(informer);
		}
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		final Ballistica shot = new Ballistica(
				attacker.pos,
				defender.pos,
				Ballistica.MAGIC_BOLT);
		int shotcell = shot.collisionPos;
		attacker.sprite.zap(shotcell);

		fx(shot, defender, attacker);
		onShoot(shot, (Hero) attacker);
		return super.proc(attacker, defender, damage);
	}

	private static CellSelector.Listener informer = new CellSelector.Listener() {
		@Override
		public void onSelect(Integer cell) {
			if (cell != null) {
				Char enemy = Actor.findChar( cell );
				if (enemy == null || enemy == curUser) {
					GLog.i(Messages.get(GunWeapon.class, "nothing_here"));
				} else {
					if (!curUser.shoot( enemy, thisweapon)) {
						GLog.i(Messages.get(GunWeapon.class, "missed"));
						final Ballistica shot = new Ballistica( curUser.pos, cell, Ballistica.PROJECTILE);
						int shotcell = shot.collisionPos;
						curUser.sprite.zap(shotcell);

						fx(shot);
						onShoot(shot, curUser);
					} else {
						final Ballistica shot = new Ballistica( curUser.pos, cell, Ballistica.PROJECTILE);
						int shotcell = shot.collisionPos;
						curUser.sprite.zap(shotcell);

						fx(shot);
						onShoot(shot, curUser);
					}
				}
			}
		}

		@Override
		public String prompt() {
			return Messages.get(GunWeapon.class, "shoot_where");
		}
	};

	public static void fx( Ballistica bolt, Char defender, Char attacker) {
		Char target;
		if(Actor.findChar(bolt.collisionPos) != null){
			target = Actor.findChar(bolt.collisionPos);
		} else {
			target = defender;
		}
		attacker.sprite.parent.add(new Beam.BulletTray(attacker.sprite.center(), target.sprite.center()));
		Sample.INSTANCE.play( Assets.SND_ZAP );
	}

	public static void fx( Ballistica bolt) {
		curUser.sprite.parent.add(new Beam.BulletTray(curUser.sprite.center(), Actor.findChar(bolt.collisionPos).sprite.center()));
		Sample.INSTANCE.play( Assets.SND_ZAP );
	}

	public static void onShoot(Ballistica shot, Hero hero){
		hero.spend(LOADING_TIME);
		hero.busy();
		(hero.sprite).operate(shot.collisionPos);
		hero.sprite.showStatus(CharSprite.DEFAULT, Messages.get(GunWeapon.class, "loading"));
	}

	@Override
	public Item random() {
		return this;
	}
	
	@Override
	public boolean isUpgradable() {
		return true;
	}
	
	@Override
	public boolean isIdentified() {
		return false;
	}
	
	@Override
	public String info() {

		String info = desc();
		
		info += "\n\n" + Messages.get( GunWeapon.class, "stats", imbue.damageFactor(min()), imbue.damageFactor(max()), STRReq());

		if (STRReq() > Dungeon.hero.STR()) {
			info += " " + Messages.get(Weapon.class, "too_heavy");
		} else if (Dungeon.hero.heroClass == HeroClass.HUNTRESS && Dungeon.hero.STR() > STRReq()){
			info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
		}

		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + Messages.get(enchantment, "desc");
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		}

		info += "\n\n" + flavourtext;

		info += "\n\n" + Messages.get(MissileWeapon.class, "distance");
		
		return info;
	}
}
