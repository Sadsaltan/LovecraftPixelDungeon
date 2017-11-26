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

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfRegrowth;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
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
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Stormvine;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Sungrass;
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
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.CellSelector;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class Scissors extends Item {

	public static final String AC_CUT = "CUT PLANT";

	{
		image = ItemSpriteSheet.SCISSORS;
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
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions(hero);
		actions.add( AC_CUT );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_CUT )) {
			GameScene.selectCell(informer);
		}
	}

	private static CellSelector.Listener informer = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
			if(Dungeon.depth != 1 &&
					!Dungeon.level.plants.get(cell).equals(null) &&
					!Dungeon.level.solid[cell]){
				Dungeon.level.uproot(cell);
				Plant plant = Dungeon.level.plants.get(cell);
				if(plant instanceof BlandfruitBush){
					Dungeon.level.drop(new BlandfruitItem(), cell);
				} else if(plant instanceof Blindweed){
					Dungeon.level.drop(new BlindweedItem(), cell);
				} else if(plant instanceof WandOfRegrowth.Dewcatcher){
					Dungeon.level.drop(new DewcatcherItem(), cell);
				} else if(plant instanceof Dreamfoil){
					Dungeon.level.drop(new DreamfoilItem(), cell);
				} else if(plant instanceof Earthroot){
					Dungeon.level.drop(new EarthrootItem(), cell);
				} else if(plant instanceof Fadeleaf){
					Dungeon.level.drop(new FadeleafItem(), cell);
				} else if(plant instanceof Firebloom){
					Dungeon.level.drop(new FirebloomItem(), cell);
				} else if(plant instanceof Icecap){
					Dungeon.level.drop(new IcecapItem(), cell);
				} else if(plant instanceof Rotberry){
					Dungeon.level.drop(new RotberryItem(), cell);
				} else if(plant instanceof WandOfRegrowth.Seedpod){
					Dungeon.level.drop(new SeedpodItem(), cell);
				} else if(plant instanceof Sorrowmoss){
					Dungeon.level.drop(new SorrowmossItem(), cell);
				} else if(plant instanceof Starflower){
					Dungeon.level.drop(new StarflowerItem(), cell);
				} else if(plant instanceof Stormvine){
					Dungeon.level.drop(new StormvineItem(), cell);
				} else if(plant instanceof Sungrass){
					Dungeon.level.drop(new SungrassItem(), cell);
				}
			} else {
				GLog.n(Messages.get(Scissors.class, "nothing"));
			}
		}
		@Override
		public String prompt() {
			return Messages.get(Scissors.class, "plant_cut");
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
