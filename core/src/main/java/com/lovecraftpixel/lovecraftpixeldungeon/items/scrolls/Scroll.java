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

package com.lovecraftpixel.lovecraftpixeldungeon.items.scrolls;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Blindness;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.ItemStatusHandler;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.UnstableSpellbook;
import com.lovecraftpixel.lovecraftpixeldungeon.journal.Catalog;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.HeroSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Scroll extends Item {
	
	public static final String AC_READ	= "READ";
	
	protected static final float TIME_TO_READ	= 1f;

	protected int initials;

	private static final Class<?>[] scrolls = {
		ScrollOfIdentify.class,
		ScrollOfMagicMapping.class,
		ScrollOfRecharging.class,
		ScrollOfRemoveCurse.class,
		ScrollOfTeleportation.class,
		ScrollOfUpgrade.class,
		ScrollOfRage.class,
		ScrollOfTerror.class,
		ScrollOfLullaby.class,
		ScrollOfMagicalInfusion.class,
		ScrollOfPsionicBlast.class,
		ScrollOfMirrorImage.class
	};

	private static final HashMap<String, Integer> runes = new HashMap<String, Integer>() {
		{
			put("KAUNAN",ItemSpriteSheet.SCROLL_KAUNAN);
			put("SOWILO",ItemSpriteSheet.SCROLL_SOWILO);
			put("LAGUZ",ItemSpriteSheet.SCROLL_LAGUZ);
			put("YNGVI",ItemSpriteSheet.SCROLL_YNGVI);
			put("GYFU",ItemSpriteSheet.SCROLL_GYFU);
			put("RAIDO",ItemSpriteSheet.SCROLL_RAIDO);
			put("ISAZ",ItemSpriteSheet.SCROLL_ISAZ);
			put("MANNAZ",ItemSpriteSheet.SCROLL_MANNAZ);
			put("NAUDIZ",ItemSpriteSheet.SCROLL_NAUDIZ);
			put("BERKANAN",ItemSpriteSheet.SCROLL_BERKANAN);
			put("ODAL",ItemSpriteSheet.SCROLL_ODAL);
			put("TIWAZ",ItemSpriteSheet.SCROLL_TIWAZ);
		}
	};
	
	private static ItemStatusHandler<Scroll> handler;
	
	private String rune;

	public boolean ownedByBook = false;
	
	{
		stackable = true;
		defaultAction = AC_READ;
	}
	
	@SuppressWarnings("unchecked")
	public static void initLabels() {
		handler = new ItemStatusHandler<>( (Class<? extends Scroll>[])scrolls, runes );
	}
	
	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}

	public static void saveSelectively( Bundle bundle, ArrayList<Item> items ) {
		handler.saveSelectively( bundle, items );
	}

	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<>( (Class<? extends Scroll>[])scrolls, runes, bundle );
	}
	
	public Scroll() {
		super();
		reset();
	}

	@Override
	public void reset(){
		super.reset();
		if (handler != null) {
			image = handler.image(this);
			rune = handler.label(this);
		}
	};
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_READ );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_READ )) {

			if(hero.knowl <= 0){
				GLog.n( Messages.get(this, "lacking_knowl") );
				hero.sprite.showStatus( CharSprite.NEGATIVE, "?" );
				return;
			}
			
			if (hero.buff( Blindness.class ) != null) {
				GLog.w( Messages.get(this, "blinded") );
			} else if (hero.buff(UnstableSpellbook.bookRecharge.class) != null
					&& hero.buff(UnstableSpellbook.bookRecharge.class).isCursed()
					&& !(this instanceof ScrollOfRemoveCurse)) {
				GLog.n( Messages.get(this, "cursed") );
			} else {
				curUser = hero;
				curItem = detach( hero.belongings.backpack );
				hero.loseKnowl(1);
				doRead();
			}
			
		}
	}
	
	public abstract void doRead();
	
	//currently only used in scrolls owned by the unstable spellbook
	public abstract void empoweredRead();

	protected void readAnimation() {
		curUser.spend( TIME_TO_READ );
		curUser.busy();
		((HeroSprite)curUser.sprite).read();
	}
	
	public boolean isKnown() {
		return handler.isKnown( this );
	}
	
	public void setKnown() {
		if (!ownedByBook) {
			if (!isKnown()) {
				handler.know(this);
				updateQuickslot();
			}
			
			if (Dungeon.hero.isAlive()) {
				Catalog.setSeen(getClass());
			}
		}
	}
	
	@Override
	public Item identify() {
		setKnown();
		return super.identify();
	}
	
	@Override
	public String name() {
		return isKnown() ? name : Messages.get(Scroll.class, rune);
	}
	
	@Override
	public String info() {
		return isKnown() ?
			desc() :
			Messages.get(this, "unknown_desc");
	}

	public Integer initials(){
		return isKnown() ? initials : null;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return isKnown();
	}
	
	public static HashSet<Class<? extends Scroll>> getKnown() {
		return handler.known();
	}
	
	public static HashSet<Class<? extends Scroll>> getUnknown() {
		return handler.unknown();
	}
	
	public static boolean allKnown() {
		return handler.known().size() == scrolls.length;
	}
	
	@Override
	public int price() {
		return 30 * quantity;
	}
}
