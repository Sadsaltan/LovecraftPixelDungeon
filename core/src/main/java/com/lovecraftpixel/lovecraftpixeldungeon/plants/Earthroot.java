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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.livingplants.LivingPlantEarthRoot;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.EarthParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfParalyticGas;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.BuffIndicator;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.RandomL;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class Earthroot extends Plant {
	
	{
		image = 5;
	}
	
	@Override
	public void activate() {
		if (RandomL.randomBoolean()){
			spawnLivingPlant(new LivingPlantEarthRoot());
		} else {
			effectChar();
		}
	}

	private void effectChar(){
		Char ch = Actor.findChar(pos);

		if (ch == Dungeon.hero) {
			Buff.affect( ch, Armor.class ).level(ch.HT);
		}

		if (Dungeon.level.heroFOV[pos]) {
			CellEmitter.bottom( pos ).start( EarthParticle.FACTORY, 0.05f, 8 );
			Camera.main.shake( 1, 0.4f );
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_EARTHROOT;

			plantClass = Earthroot.class;
			alchemyClass = PotionOfParalyticGas.class;

			bones = true;
		}
	}
	
	public static class Armor extends Buff {
		
		private static final float STEP = 1f;
		
		private int pos;
		private int level;

		{
			type = buffType.POSITIVE;
		}
		
		@Override
		public boolean attachTo( Char target ) {
			pos = target.pos;
			return super.attachTo( target );
		}
		
		@Override
		public boolean act() {
			if (target.pos != pos) {
				detach();
			}
			spend( STEP );
			return true;
		}
		
		public int absorb( int damage ) {
			if (level <= damage-damage/2) {
				detach();
				return damage - level;
			} else {
				level -= damage-damage/2;
				BuffIndicator.refreshHero();
				return damage/2;
			}
		}
		
		public void level( int value ) {
			if (level < value) {
				level = value;
				BuffIndicator.refreshHero();
			}
			pos = target.pos;
		}
		
		@Override
		public int icon() {
			return BuffIndicator.ARMOR;
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

		private static final String POS		= "pos";
		private static final String LEVEL	= "level";
		
		@Override
		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle( bundle );
			bundle.put( POS, pos );
			bundle.put( LEVEL, level );
		}
		
		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle( bundle );
			pos = bundle.getInt( POS );
			level = bundle.getInt( LEVEL );
		}
	}
}
