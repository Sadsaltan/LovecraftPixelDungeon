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

package com.lovecraftpixel.lovecraftpixeldungeon.items.tools;

import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Plant;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.BlandfruitHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.BlindweedHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.DewcatcherHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.DreamfoilHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.EarthrootHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.FadeleafHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.FirebloomHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.IcecapHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.RotberryHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.SeedpodHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.SorrowmossHerb;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs.StarflowerHerb;
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
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.StormvineItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SungrassItem;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.windows.WndBag;

import java.util.ArrayList;

public class Tincturebottle extends Item {

	public static final String AC_BREW = "BREW";

	{
		image = ItemSpriteSheet.TINCTUREBOTTLE;
		defaultAction = AC_BREW;
		stackable = false;
	}

	public PlantItem plant;
	public Plant.Seed seed;
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions(hero);
		actions.add( AC_BREW );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_BREW )) {
			GameScene.selectItem( itemSelectorPlant, WndBag.Mode.PLANTITEM, Messages.get(this, "prompt_plant") );
		}
	}

	public void brew(){
		if(plant instanceof FirebloomItem){
			new FirebloomHerb().setSeed(seed).collect();
		}
		if(plant instanceof FadeleafItem){
			new FadeleafHerb().setSeed(seed).collect();
		}
		if(plant instanceof EarthrootItem){
			new EarthrootHerb().setSeed(seed).collect();
		}
		if(plant instanceof DreamfoilItem){
			new DreamfoilHerb().setSeed(seed).collect();
		}
		if(plant instanceof DewcatcherItem){
			new DewcatcherHerb().setSeed(seed).collect();
		}
		if(plant instanceof BlindweedItem){
			new BlindweedHerb().setSeed(seed).collect();
		}
		if(plant instanceof BlandfruitItem){
			new BlandfruitHerb().setSeed(seed).collect();
		}
		if(plant instanceof IcecapItem){
			new IcecapHerb().setSeed(seed).collect();
		}
		if(plant instanceof RotberryItem){
			new RotberryHerb().setSeed(seed).collect();
		}
		if(plant instanceof SeedpodItem){
			new SeedpodHerb().setSeed(seed).collect();
		}
		if(plant instanceof SorrowmossItem){
			new SorrowmossHerb().setSeed(seed).collect();
		}
		if(plant instanceof StarflowerItem){
			new StarflowerHerb().setSeed(seed).collect();
		}
		if(plant instanceof StormvineItem){
			new StormvineHerb().setSeed(seed).collect();
		}
		if(plant instanceof SungrassItem){
			new SungrassHerb().setSeed(seed).collect();
		}
	}

	private final WndBag.Listener itemSelectorPlant = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				plant = (PlantItem) item;
				item.detach(curUser.belongings.backpack);
				GameScene.selectItem( itemSelectorSeed, WndBag.Mode.SEED, Messages.get(Tincturebottle.class, "prompt_seed") );
			}
		}
	};

	private final WndBag.Listener itemSelectorSeed = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				seed = (Plant.Seed) item;
				item.detach(curUser.belongings.backpack);
				brew();
			}
		}
	};
	
	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}
	
	@Override
	public int price() {
		return 10 * quantity;
	}
}
