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

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.Badges;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.HeroSubClass;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Speck;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.ShadowParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.Ring;
import com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.Wand;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;
import com.lovecraftpixel.lovecraftpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;

import java.util.ArrayList;

public class DewVial extends Item {

	private static final int MAX_VOLUME	= 100;

	private static final String AC_DRINK	= "DRINK";
	private static final String AC_UPGRADE	= "UPGRADE";

	private static final float TIME_TO_DRINK = 1f;

	private static final String TXT_STATUS	= "%d/%d";

	{
		image = ItemSpriteSheet.VIAL;

		defaultAction = AC_DRINK;

		unique = true;
	}

	private int volume = 0;

	private static final String VOLUME	= "volume";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( VOLUME, volume );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		volume	= bundle.getInt( VOLUME );
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (volume > 0) {
			actions.add( AC_DRINK );
		}
		if (this.isACUpgradeable()) {
			actions.add( AC_UPGRADE );
		}
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_DRINK )) {

			if (volume > 0) {
				
				//20 drops for a full heal normally, 15 for the warden
				float dropHealPercent = hero.subClass == HeroSubClass.WARDEN ? 0.0667f : 0.05f;
				float missingHealthPercent = 1f - (hero.HP / (float)hero.HT);
				
				//trimming off 0.01 drops helps with floating point errors
				int dropsNeeded = (int)Math.ceil((missingHealthPercent / dropHealPercent) - 0.01f);
				dropsNeeded = (int)GameMath.gate(1, dropsNeeded, volume);
				
				int heal = Math.round( hero.HT * dropHealPercent * dropsNeeded );
				
				int effect = Math.min( hero.HT - hero.HP, heal );
				if (effect > 0) {
					hero.HP += effect;
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 + dropsNeeded/5 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "value", effect) );
				}

				volume -= dropsNeeded;

				hero.spend( TIME_TO_DRINK );
				hero.busy();

				Sample.INSTANCE.play( Assets.SND_DRINK );
				hero.sprite.operate( hero.pos );

				if(isFull()){
					image = ItemSpriteSheet.VIAL_FULL;
				} else if(volume == 0){
					image = ItemSpriteSheet.VIAL;
				} else if(volume <= MAX_VOLUME/2){
					image = ItemSpriteSheet.VIAL_LITTLE;
				} else {
					image = ItemSpriteSheet.VIAL_MIDDLE;
				}

				updateQuickslot();


			} else {
				GLog.w( Messages.get(this, "empty") );
			}

		}
		if(action.equals(AC_UPGRADE)){
			GameScene.selectItem( itemSelector, WndBag.Mode.UPGRADEABLE, Messages.get(this, "upgrade") );
		}
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				upgrade( curUser );

				curUser.increaseMentalHealth(1);
				curUser.gainKnowl(1);

				//logic for telling the user when item properties change from upgrades
				//...yes this is rather messy
				if (item instanceof Weapon){
					Weapon w = (Weapon) item;
					boolean wasCursed = w.cursed;
					boolean hadCursedEnchant = w.hasCurseEnchant();
					boolean hadGoodEnchant = w.hasGoodEnchant();

					w.upgrade();
					withdrawBlessOrUpgrade();

					if (hadCursedEnchant && !w.hasCurseEnchant()){
						removeCurse( Dungeon.hero );
					} else if (wasCursed && !w.cursed){
						weakenCurse( Dungeon.hero );
					}
					if (hadGoodEnchant && !w.hasGoodEnchant()){
						GLog.w( Messages.get(Weapon.class, "incompatible") );
					}

				} else if (item instanceof Armor){
					Armor a = (Armor) item;
					boolean wasCursed = a.cursed;
					boolean hadCursedGlyph = a.hasCurseGlyph();
					boolean hadGoodGlyph = a.hasGoodGlyph();

					a.upgrade();
					withdrawBlessOrUpgrade();

					if (hadCursedGlyph && !a.hasCurseGlyph()){
						removeCurse( Dungeon.hero );
					} else if (wasCursed && !a.cursed){
						weakenCurse( Dungeon.hero );
					}
					if (hadGoodGlyph && !a.hasGoodGlyph()){
						GLog.w( Messages.get(Armor.class, "incompatible") );
					}

				} else if (item instanceof Wand || item instanceof Ring) {
					boolean wasCursed = item.cursed;

					item.upgrade();
					withdrawBlessOrUpgrade();

					if (wasCursed && !item.cursed){
						removeCurse( Dungeon.hero );
					}

				} else {
					item.upgrade();
					withdrawBlessOrUpgrade();
				}

				Badges.validateItemLevelAquired( item );
			}
		}
	};

	public static void upgrade( Hero hero ) {
		hero.sprite.emitter().start( Speck.factory( Speck.UP ), 0.2f, 3 );
	}

	public static void weakenCurse( Hero hero ){
		GLog.p( Messages.get(ScrollOfUpgrade.class, "weaken_curse") );
		hero.sprite.emitter().start( ShadowParticle.UP, 0.05f, 5 );
	}

	public static void removeCurse( Hero hero ){
		GLog.p( Messages.get(ScrollOfUpgrade.class, "remove_curse") );
		hero.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
	}

	public void empty() {
		volume = 0;
		if(isFull()){
			image = ItemSpriteSheet.VIAL_FULL;
		} else if(volume == 0){
			image = ItemSpriteSheet.VIAL;
		} else if(volume <= MAX_VOLUME/2){
			image = ItemSpriteSheet.VIAL_LITTLE;
		} else {
			image = ItemSpriteSheet.VIAL_MIDDLE;
		}
		updateQuickslot();
	}

	public void withdrawBlessOrUpgrade() {
		volume = volume-(MAX_VOLUME/2);
		if(isFull()){
			image = ItemSpriteSheet.VIAL_FULL;
		} else if(volume == 0){
			image = ItemSpriteSheet.VIAL;
		} else if(volume <= MAX_VOLUME/2){
			image = ItemSpriteSheet.VIAL_LITTLE;
		} else {
			image = ItemSpriteSheet.VIAL_MIDDLE;
		}
		updateQuickslot();
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	public boolean isFull() {
		return volume >= MAX_VOLUME;
	}

	public boolean isBlessable() {
		return volume >= MAX_VOLUME/2;
	}

	public boolean isACUpgradeable() {
		return volume >= MAX_VOLUME/2;
	}

	public void collectDew( Dewdrop dew ) {

		GLog.i( Messages.get(this, "collected") );
		volume += dew.quantity;
		if (volume >= MAX_VOLUME) {
			volume = MAX_VOLUME;
			GLog.p( Messages.get(this, "full") );
		}

		if(isFull()){
			image = ItemSpriteSheet.VIAL_FULL;
		} else if(volume == 0){
			image = ItemSpriteSheet.VIAL;
		} else if(volume <= (MAX_VOLUME/2)){
			image = ItemSpriteSheet.VIAL_LITTLE;
		} else {
			image = ItemSpriteSheet.VIAL_MIDDLE;
		}

		updateQuickslot();
	}

	public void fill() {
		volume = MAX_VOLUME;
		if(isFull()){
			image = ItemSpriteSheet.VIAL_FULL;
		} else if(volume == 0){
			image = ItemSpriteSheet.VIAL;
		} else if(volume <= MAX_VOLUME/2){
			image = ItemSpriteSheet.VIAL_LITTLE;
		} else {
			image = ItemSpriteSheet.VIAL_MIDDLE;
		}
		updateQuickslot();
	}

	@Override
	public String status() {
		return Messages.format( TXT_STATUS, volume, MAX_VOLUME );
	}

}
