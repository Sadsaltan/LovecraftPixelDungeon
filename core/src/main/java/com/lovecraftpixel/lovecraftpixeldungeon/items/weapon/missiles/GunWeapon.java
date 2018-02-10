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

package com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.HeroClass;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.CellSelector;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.ItemsFlavourText;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

abstract public class GunWeapon extends Weapon {

	public String flavourtext;
	public static GunWeapon thisweapon;

	public static final String AC_SHOOT = "SHOOT";

	{
		stackable = false;
		levelKnown = true;

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
		actions.remove( AC_EQUIP );
		actions.add( AC_SHOOT );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_SHOOT )) {
			GameScene.selectCell(informer);
		}
	}

	private static CellSelector.Listener informer = new CellSelector.Listener() {
		@Override
		public void onSelect(Integer cell) {
			if (cell != null) {
				Char enemy = Actor.findChar( cell );
				if (enemy == null || enemy == curUser) {
					Messages.get(GunWeapon.class, "nothing_here");
				} else {
					if (!curUser.shoot( enemy, thisweapon)) {
						Messages.get(GunWeapon.class, "missed");
					}
				}
			}
		}

		@Override
		public String prompt() {
			return Messages.get(GunWeapon.class, "shoot_where");
		}
	};
	
	@Override
	public Item random() {
		return this;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
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

		info += "\n\n" + Messages.get(GunWeapon.class, "distance");
		
		return info;
	}
}
