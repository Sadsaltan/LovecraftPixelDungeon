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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.hero;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.Badges;
import com.lovecraftpixel.lovecraftpixeldungeon.Challenges;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.BrokenSeal;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.ClothArmor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.CloakOfShadows;
import com.lovecraftpixel.lovecraftpixeldungeon.items.bags.FoodBag;
import com.lovecraftpixel.lovecraftpixeldungeon.items.food.Food;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfHealing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfMindVision;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.lovecraftpixel.lovecraftpixeldungeon.items.tools.Scissors;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfMagicMissile;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Dagger;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Knuckles;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.MagesStaff;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.WornShortsword;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Boomerang;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Dart;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.BlandfruitItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.BlindweedItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.DewcatcherItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.DreamfoilItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.EarthrootItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.FadeleafItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.FirebloomItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.IcecapItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.RotberryItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SeedpodItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SorrowmossItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.StarflowerItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.StormvineItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SungrassItem;
import com.watabou.utils.Bundle;

public enum HeroClass {

	WARRIOR( "warrior" ),
	MAGE( "mage" ),
	ROGUE( "rogue" ),
	HUNTRESS( "huntress" );

	private String title;

	HeroClass( String title ) {
		this.title = title;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;

		initCommon( hero );

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;
		}
		
	}

	private static void initCommon( Hero hero ) {
		if (!Dungeon.isChallenged(Challenges.NO_ARMOR))
			(hero.belongings.armor = new ClothArmor()).identify();

		if (!Dungeon.isChallenged(Challenges.NO_FOOD))
			new Food().identify().collect();
			new FoodBag().collect();

		new Scissors().collect();

		new BlandfruitItem().collect();
		new BlindweedItem().collect();
		new DewcatcherItem().collect();
		new DreamfoilItem().collect();
		new EarthrootItem().collect();
		new FadeleafItem().collect();
		new FirebloomItem().collect();
		new FirebloomItem().collect();
		new IcecapItem().collect();
		new RotberryItem().collect();
		new SeedpodItem().collect();
		new SorrowmossItem().collect();
		new StarflowerItem().collect();
		new StormvineItem().collect();
		new SungrassItem().collect();
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {

		hero.HP = hero.HT = 25;
		hero.MHP = hero.MHT = 5;

		(hero.belongings.weapon = new WornShortsword()).identify();
		Dart darts = new Dart( 8 );
		darts.identify().collect();

		if ( Badges.isUnlocked(Badges.Badge.TUTORIAL_WARRIOR) ){
			if (!Dungeon.isChallenged(Challenges.NO_ARMOR))
				hero.belongings.armor.affixSeal(new BrokenSeal());
			Dungeon.quickslot.setSlot(0, darts);
		} else {
			if (!Dungeon.isChallenged(Challenges.NO_ARMOR)) {
				BrokenSeal seal = new BrokenSeal();
				seal.collect();
				Dungeon.quickslot.setSlot(0, seal);
			}
			Dungeon.quickslot.setSlot(1, darts);
		}

		new PotionOfHealing().identify();
	}

	private static void initMage( Hero hero ) {

		hero.HP = hero.HT = 15;
		hero.MHP = hero.MHT = 15;

		MagesStaff staff;

		if ( Badges.isUnlocked(Badges.Badge.TUTORIAL_MAGE) ){
			staff = new MagesStaff(new WandOfMagicMissile());
		} else {
			staff = new MagesStaff();
			new WandOfMagicMissile().identify().collect();
		}

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate( hero );

		Dart darts = new Dart( 8 );
		darts.identify().collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, darts);

		new ScrollOfMagicMapping().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Knuckles()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();

		Dungeon.quickslot.setSlot(0, boomerang);

		new PotionOfMindVision().identify();
	}
	
	public String title() {
		return Messages.get(HeroClass.class, title);
	}
	
	public String spritesheet() {
		
		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
		}
		
		return null;
	}
	
	public String[] perks() {
		
		switch (this) {
		case WARRIOR:
			return new String[]{
					Messages.get(HeroClass.class, "warrior_perk1"),
					Messages.get(HeroClass.class, "warrior_perk2"),
					Messages.get(HeroClass.class, "warrior_perk3"),
					Messages.get(HeroClass.class, "warrior_perk4"),
					Messages.get(HeroClass.class, "warrior_perk5"),
			};
		case MAGE:
			return new String[]{
					Messages.get(HeroClass.class, "mage_perk1"),
					Messages.get(HeroClass.class, "mage_perk2"),
					Messages.get(HeroClass.class, "mage_perk3"),
					Messages.get(HeroClass.class, "mage_perk4"),
					Messages.get(HeroClass.class, "mage_perk5"),
			};
		case ROGUE:
			return new String[]{
					Messages.get(HeroClass.class, "rogue_perk1"),
					Messages.get(HeroClass.class, "rogue_perk2"),
					Messages.get(HeroClass.class, "rogue_perk3"),
					Messages.get(HeroClass.class, "rogue_perk4"),
					Messages.get(HeroClass.class, "rogue_perk5"),
			};
		case HUNTRESS:
			return new String[]{
					Messages.get(HeroClass.class, "huntress_perk1"),
					Messages.get(HeroClass.class, "huntress_perk2"),
					Messages.get(HeroClass.class, "huntress_perk3"),
					Messages.get(HeroClass.class, "huntress_perk4"),
					Messages.get(HeroClass.class, "huntress_perk5"),
			};
		}
		
		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : ROGUE;
	}
}
