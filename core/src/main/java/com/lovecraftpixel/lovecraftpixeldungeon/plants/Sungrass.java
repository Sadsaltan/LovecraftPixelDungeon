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

package com.lovecraftpixel.lovecraftpixeldungeon.plants;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.FlavourBuff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.livingplants.LivingPlantSunGrass;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Speck;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.ShaftParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfHealing;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.BuffIndicator;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.RandomL;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class Sungrass extends Plant {
	
	{
		image = 4;
	}
	
	@Override
	public void activate() {
		if (RandomL.randomBoolean()){
			spawnLivingPlant(new LivingPlantSunGrass());
		} else {
			effectChar();
		}
	}

	private void effectChar(){
		Char ch = Actor.findChar(pos);

		if (ch == Dungeon.hero) {
			Buff.affect( ch, Health.class ).boost(ch.HT);
		}

		if (Dungeon.level.heroFOV[pos]) {
			CellEmitter.get( pos ).start( ShaftParticle.FACTORY, 0.2f, 3 );
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_SUNGRASS;

			plantClass = Sungrass.class;
			alchemyClass = PotionOfHealing.class;

			bones = true;
		}
	}
	
	public static class Health extends Buff {
		
		private static final float STEP = 1f;
		
		private int pos;
		private int healCurr = 1;
		private int count = 0;
		private int level;

		{
			type = buffType.POSITIVE;
		}
		
		@Override
		public boolean act() {
			if (target.pos != pos) {
				detach();
			}
			if (count == 5) {
				if (level <= healCurr*.025*target.HT) {
					target.HP = Math.min(target.HT, target.HP + level);
					target.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
					detach();
				} else {
					target.HP = Math.min(target.HT, target.HP+(int)(healCurr*.025*target.HT));
					level -= (healCurr*.025*target.HT);
					if (healCurr < 6)
						healCurr ++;
					target.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
				}
				if (target.HP == target.HT && target instanceof Hero){
					((Hero)target).resting = false;
				}
				count = 1;
			} else {
				count++;
			}
			if (level <= 0) {
				detach();
			} else {
				BuffIndicator.refreshHero();
			}
			spend( STEP );
			return true;
		}

		public int absorb( int damage ) {
			level -= damage;
			if (level <= 0) {
				detach();
			} else {
				BuffIndicator.refreshHero();
			}
			return damage;
		}

		public void boost( int amount ){
			level += amount;
			pos = target.pos;
		}
		
		@Override
		public int icon() {
			return BuffIndicator.HEALING;
		}
		
		@Override
		public void tintIcon(Image icon) {
			FlavourBuff.greyIcon(icon, target.HT/4f, level);
		}
		
		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", level);
		}

		private static final String POS	= "pos";
		private static final String HEALCURR = "healCurr";
		private static final String COUNT = "count";
		private static final String LEVEL = "level";

		@Override
		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle( bundle );
			bundle.put( POS, pos );
			bundle.put( HEALCURR, healCurr);
			bundle.put( COUNT, count);
			bundle.put( LEVEL, level);
		}
		
		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle( bundle );
			pos = bundle.getInt( POS );
			healCurr = bundle.getInt( HEALCURR );
			count = bundle.getInt( COUNT );
			level = bundle.getInt( LEVEL );

		}
	}
}