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

package com.lovecraftpixel.lovecraftpixeldungeon.items;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.ClothArmor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.LeatherArmor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.MailArmor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.PlateArmor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.ScaleArmor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.Artifact;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.CapeOfThorns;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.CloakOfShadows;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.DriedRose;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.EtherealChains;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.HornOfPlenty;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.LloydsBeacon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.MasterThievesArmband;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.SandalsOfNature;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.TalismanOfForesight;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.UnstableSpellbook;
import com.lovecraftpixel.lovecraftpixeldungeon.items.bags.Bag;
import com.lovecraftpixel.lovecraftpixeldungeon.items.food.Food;
import com.lovecraftpixel.lovecraftpixeldungeon.items.food.MysteryMeat;
import com.lovecraftpixel.lovecraftpixeldungeon.items.food.Pasty;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.Potion;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfExperience;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfFrost;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfHealing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfInvisibility;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfLevitation;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfMight;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfMindVision;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfParalyticGas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfPurity;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfStrength;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.Ring;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfAccuracy;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfElements;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfEnergy;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfEvasion;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfForce;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfFuror;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfHaste;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfMight;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfSharpshooting;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfTenacity;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.RingOfWealth;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.Scroll;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfMagicalInfusion;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfRage;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfTerror;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.Wand;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfBlastWave;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfCorruption;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfDisintegration;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfFireblast;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfFrost;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfLightning;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfMagicMissile;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfPrismaticLight;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfRegrowth;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfTransfusion;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfVenom;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.Bow;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.Crossbow;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.FastFirePistol;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.Harpoon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.HeavyCrossbow;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.Luger;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.Pistol;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.Rifle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.Shotgun;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Axe;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.BattleAxe;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Dagger;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Dirk;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Flail;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Glaive;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Greataxe;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Greatshield;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Greatsword;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Halberd;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.HandAxe;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.HeroicShortsword;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Katana;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.KnifeGlove;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Knuckles;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Kusarigama;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Longsword;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Mace;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.MagesStaff;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Quarterstaff;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.RoundShield;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.RunicBlade;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Sai;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Scimitar;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Scythe;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Shortsword;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Spear;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.SpikedGreatsword;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Sword;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.WarHammer;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.Whip;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.WornShortsword;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Bolas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Boomerang;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Chakram;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.CurareDart;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Dart;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.FishingSpear;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.GiantShuriken;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.IncendiaryDart;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Javelin;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.PrimitiveSpear;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Shuriken;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Tamahawk;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.ThrowingHammer;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.Trident;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.BlandfruitBush;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Blindweed;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Dreamfoil;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Earthroot;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Fadeleaf;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Firebloom;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Icecap;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Plant;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Rotberry;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Sorrowmoss;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Starflower;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Steamweed;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Stormvine;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Sungrass;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.BlandfruitHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.BlindweedHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.DewcatcherHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.DreamfoilHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.EarthrootHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.FadeleafHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.FirebloomHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.Herb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.IcecapHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.RotberryHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.SeedpodHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.SorrowmossHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.StarflowerHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.SteamweedHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.StormvineHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.SungrassHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.BlandfruitItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.BlindweedItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.DewcatcherItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.DreamfoilItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.EarthrootItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.FadeleafItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.FirebloomItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.IcecapItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.PlantItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.RotberryItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SeedpodItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SorrowmossItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.StarflowerItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SteamWeedItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.StormvineItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SungrassItem;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {
		WEAPON	( 6,    Weapon.class ),
		WEP_T1	( 0,    Weapon.class),
		WEP_T2	( 0,    Weapon.class),
		WEP_T3	( 0,    Weapon.class),
		WEP_T4	( 0,    Weapon.class),
		WEP_T5	( 0,    Weapon.class),
		ARMOR	( 4,    Armor.class ),
		POTION	( 20,   Potion.class ),
		SCROLL	( 20,   Scroll.class ),
		WAND	( 3,    Wand.class ),
		RING	( 1,    Ring.class ),
		ARTIFACT( 1,    Artifact.class),
		SEED	( 0,    Plant.Seed.class ),
		FOOD	( 0,    Food.class ),
		PLANT	( 0,    PlantItem.class ),
		HERBS	( 0,    Herb.class ),
		GOLD	( 25,   Gold.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
		
		private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{ 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1};
		
		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };
			
			SCROLL.classes = new Class<?>[]{
					ScrollOfIdentify.class,
					ScrollOfTeleportation.class,
					ScrollOfRemoveCurse.class,
					ScrollOfUpgrade.class,
					ScrollOfRecharging.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfTerror.class,
					ScrollOfLullaby.class,
					ScrollOfMagicalInfusion.class,
					ScrollOfPsionicBlast.class,
					ScrollOfMirrorImage.class };
			SCROLL.probs = new float[]{ 30, 10, 20, 0, 15, 15, 12, 8, 8, 0, 4, 10 };
			
			POTION.classes = new Class<?>[]{
					PotionOfHealing.class,
					PotionOfExperience.class,
					PotionOfToxicGas.class,
					PotionOfParalyticGas.class,
					PotionOfLiquidFlame.class,
					PotionOfLevitation.class,
					PotionOfStrength.class,
					PotionOfMindVision.class,
					PotionOfPurity.class,
					PotionOfInvisibility.class,
					PotionOfMight.class,
					PotionOfFrost.class };
			POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };
			
			//TODO: add last ones when implemented
			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfVenom.class,
					WandOfBlastWave.class,
					//WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					//WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class };
			WAND.probs = new float[]{ 5, 4, 4, 4, 4, 3, /*3,*/ 3, 3, /*3,*/ 3, 3, 3 };
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					Knuckles.class,
					Dagger.class,
					MagesStaff.class,
					Boomerang.class,
					Dart.class,
					HeroicShortsword.class,
					Axe.class,
					ThrowingKnife.class,
					FishingSpear.class,
					ThrowingStone.class,
			};
			WEP_T1.probs = new float[]{ 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1 };
			
			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					Dirk.class,
					IncendiaryDart.class,
					PrimitiveSpear.class,
					Bow.class,
					KnifeGlove.class
			};
			WEP_T2.probs = new float[]{ 6, 5, 5, 4, 4, 6, 3, 2, 1 };
			
			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class,
					Shuriken.class,
					CurareDart.class,
					Katana.class,
					Pistol.class,
					Kusarigama.class,
					Chakram.class,
					GiantShuriken.class,
					Scythe.class,
			};
			WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4, 6, 6, 4, 3, 3, 2, 1, 1 };
			
			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					RunicBlade.class,
					AssassinsBlade.class,
					Javelin.class,
					Rifle.class,
					Harpoon.class,
					Crossbow.class,
					Bolas.class,
					Luger.class,
			};
			WEP_T4.probs = new float[]{ 6, 5, 5, 4, 4, 6, 3, 2, 2, 1, 2 };
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Tamahawk.class,
					SpikedGreatsword.class,
					Halberd.class,
					FastFirePistol.class,
					HeavyCrossbow.class,
					ThrowingHammer.class,
					Trident.class,
					Shotgun.class,
			};
			WEP_T5.probs = new float[]{ 6, 5, 5, 4, 4, 6, 3, 5, 2, 2, 1, 1, 2 };
			
			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class };
			ARMOR.probs = new float[]{ 0, 0, 0, 0, 0 };
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class };
			FOOD.probs = new float[]{ 4, 1, 0 };

			PLANT.classes = new Class<?>[]{
					BlandfruitItem.class,
					BlindweedItem.class,
					DewcatcherItem.class,
					DreamfoilItem.class,
					EarthrootItem.class,
					FadeleafItem.class,
					FirebloomItem.class,
					IcecapItem.class,
					RotberryItem.class,
					SeedpodItem.class,
					SorrowmossItem.class,
					StarflowerItem.class,
					StormvineItem.class,
					SungrassItem.class,
					SteamWeedItem.class};
			PLANT.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1  };

			HERBS.classes = new Class<?>[]{
					BlandfruitHerb.class,
					BlindweedHerb.class,
					DewcatcherHerb.class,
					DreamfoilHerb.class,
					EarthrootHerb.class,
					FadeleafHerb.class,
					FirebloomHerb.class,
					IcecapHerb.class,
					RotberryHerb.class,
					SeedpodHerb.class,
					SorrowmossHerb.class,
					StarflowerHerb.class,
					StormvineHerb.class,
					SungrassHerb.class,
					SteamweedHerb.class};
			HERBS.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1  };
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfEvasion.class,
					RingOfElements.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfEnergy.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
			ARTIFACT.classes = new Class<?>[]{
					CapeOfThorns.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class,
					AlchemistsToolkit.class, //currently removed from drop tables, pending rework.
					DriedRose.class,
					LloydsBeacon.class,
					EtherealChains.class
			};
			ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();
			
			SEED.classes = new Class<?>[]{
					Firebloom.Seed.class,
					Icecap.Seed.class,
					Sorrowmoss.Seed.class,
					Blindweed.Seed.class,
					Sungrass.Seed.class,
					Earthroot.Seed.class,
					Fadeleaf.Seed.class,
					Rotberry.Seed.class,
					BlandfruitBush.Seed.class,
					Dreamfoil.Seed.class,
					Stormvine.Seed.class,
					Starflower.Seed.class,
					Steamweed.Seed.class,};
			SEED.probs = new float[]{ 12, 12, 12, 12, 12, 12, 12, 0, 3, 12, 12, 1, 6 };
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{0, 70, 20,  8,  2},
			{0, 25, 50, 20,  5},
			{0, 10, 40, 40, 10},
			{0,  5, 20, 50, 25},
			{0,  2,  8, 20, 70}
	};
	
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			reset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);
		return random( cat );
	}
	
	public static Item random( Category cat ) {
		try {
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			LovecraftPixelDungeon.reportException(e);
			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			LovecraftPixelDungeon.reportException(e);
			return null;
			
		}
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Armor a = (Armor)Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])].newInstance();
			a.random();
			return a;
		} catch (Exception e) {
			LovecraftPixelDungeon.reportException(e);
			return null;
		}
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5
	};

	public static Weapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}
	
	public static Weapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Category c = wepTiers[Random.chances(floorSetTierProbs[floorSet])];
			Weapon w = (Weapon)c.classes[Random.chances(c.probs)].newInstance();
			w.random();
			return w;
		} catch (Exception e) {
			LovecraftPixelDungeon.reportException(e);
			return null;
		}
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;
			int i = Random.chances( cat.probs );

			//if no artifacts are left, return null
			if (i == -1){
				return null;
			}
			
			Class<?extends Artifact> art = (Class<? extends Artifact>) cat.classes[i];

			if (removeArtifact(art)) {
				Artifact artifact = art.newInstance();
				
				artifact.random();
				
				return artifact;
			} else {
				return null;
			}

		} catch (Exception e) {
			LovecraftPixelDungeon.reportException(e);
			return null;
		}
	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		if (spawnedArtifacts.contains(artifact))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact)) {
				if (cat.probs[i] == 1){
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact);
					return true;
				} else
					return false;
			}

		return false;
	}

	//resets artifact probabilities, for new dungeons
	public static void initArtifacts() {
		Category.ARTIFACT.probs = Category.INITIAL_ARTIFACT_PROBS.clone();
		spawnedArtifacts = new ArrayList<>();
	}

	private static ArrayList<Class<?extends Artifact>> spawnedArtifacts = new ArrayList<>();
	
	private static final String GENERAL_PROBS = "general_probs";
	private static final String SPAWNED_ARTIFACTS = "spawned_artifacts";
	
	public static void storeInBundle(Bundle bundle) {
		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);
		
		bundle.put( SPAWNED_ARTIFACTS, spawnedArtifacts.toArray(new Class[0]));
	}

	public static void restoreFromBundle(Bundle bundle) {
		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		} else {
			reset();
		}
		
		initArtifacts();
		if (bundle.contains(SPAWNED_ARTIFACTS)){
			for ( Class<?extends Artifact> artifact : bundle.getClassArray(SPAWNED_ARTIFACTS) ){
				removeArtifact(artifact);
			}
		//pre-0.6.1 saves
		} else if (bundle.contains("artifacts")) {
			String[] names = bundle.getStringArray("artifacts");
			Category cat = Category.ARTIFACT;

			for (String artifact : names)
				for (int i = 0; i < cat.classes.length; i++)
					if (cat.classes[i].getSimpleName().equals(artifact))
						cat.probs[i] = 0;
		}
	}
}
