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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Burning;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.BlobEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.FlameParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Heap;
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
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Steamweed;
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
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SteamWeedItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.StormvineItem;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants.SungrassItem;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;

public class Fire extends Blob {

	@Override
	protected void evolve() {

		boolean[] flamable = Dungeon.level.flamable;
		int cell;
		int fire;
		
		Freezing freeze = (Freezing)Dungeon.level.blobs.get( Freezing.class );

		boolean observe = false;

		for (int i = area.left-1; i <= area.right; i++) {
			for (int j = area.top-1; j <= area.bottom; j++) {
				cell = i + j*Dungeon.level.width();
				if (cur[cell] > 0) {
					
					if (freeze != null && freeze.volume > 0 && freeze.cur[cell] > 0){
						freeze.clear(cell);
						off[cell] = cur[cell] = 0;
						continue;
					}

					burn( cell );

					fire = cur[cell] - 1;
					if (fire <= 0 && flamable[cell]) {

						Dungeon.level.destroy( cell );

						observe = true;
						GameScene.updateMap( cell );

					}

				} else if (freeze == null || freeze.volume <= 0 || freeze.cur[cell] <= 0) {

					if (flamable[cell]
							&& (cur[cell-1] > 0
							|| cur[cell+1] > 0
							|| cur[cell-Dungeon.level.width()] > 0
							|| cur[cell+Dungeon.level.width()] > 0)) {
						fire = 4;
						burn( cell );
						area.union(i, j);
					} else {
						fire = 0;
					}

				} else {
					fire = 0;
				}

				volume += (off[cell] = fire);
			}
		}

		if (observe) {
			Dungeon.observe();
		}
	}
	
	private void burn( int pos ) {
		Char ch = Actor.findChar( pos );
		if (ch != null && !ch.immunities().contains(this.getClass())) {
			Buff.affect( ch, Burning.class ).reignite( ch );
		}
		
		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null) {
			heap.burn();
		}

		Plant plant = Dungeon.level.plants.get( pos );
		if (plant != null){
			if(plant instanceof BlandfruitBush){
				Dungeon.level.drop(new BlandfruitItem(), pos);
			} else if(plant instanceof Blindweed){
				Dungeon.level.drop(new BlindweedItem(), pos);
			} else if(plant instanceof WandOfRegrowth.Dewcatcher){
				Dungeon.level.drop(new DewcatcherItem(), pos);
			} else if(plant instanceof Dreamfoil){
				Dungeon.level.drop(new DreamfoilItem(), pos);
			} else if(plant instanceof Earthroot){
				Dungeon.level.drop(new EarthrootItem(), pos);
			} else if(plant instanceof Fadeleaf){
				Dungeon.level.drop(new FadeleafItem(), pos);
			} else if(plant instanceof Firebloom){
				Dungeon.level.drop(new FirebloomItem(), pos);
			} else if(plant instanceof Icecap){
				Dungeon.level.drop(new IcecapItem(), pos);
			} else if(plant instanceof Rotberry){
				Dungeon.level.drop(new RotberryItem(), pos);
			} else if(plant instanceof WandOfRegrowth.Seedpod){
				Dungeon.level.drop(new SeedpodItem(), pos);
			} else if(plant instanceof Sorrowmoss){
				Dungeon.level.drop(new SorrowmossItem(), pos);
			} else if(plant instanceof Starflower){
				Dungeon.level.drop(new StarflowerItem(), pos);
			} else if(plant instanceof Stormvine){
				Dungeon.level.drop(new StormvineItem(), pos);
			} else if(plant instanceof Sungrass){
				Dungeon.level.drop(new SungrassItem(), pos);
			} else if(plant instanceof Steamweed){
				Dungeon.level.drop(new SteamWeedItem(), pos);
			}
			plant.wither();
		}
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start( FlameParticle.FACTORY, 0.03f, 0 );
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
