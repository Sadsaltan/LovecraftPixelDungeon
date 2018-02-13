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

package com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.enchantments;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSprite.Glowing;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.QuickSlotButton;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.RandomL;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TimeFreezing extends Weapon.Enchantment {

	private static Glowing FAINT_YELLOW = new Glowing( 0x7E8052 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		// lvl 0 - 33%
		// lvl 1 - 50%
		// lvl 2 - 60%
		int level = Math.max( 0, weapon.level() );
		
		if (Random.Int( level + 3 ) >= 2) {
			
			if (RandomL.randomBoolean()) {
				if(attacker == Dungeon.hero){
					timeFreeze buff = new timeFreeze();
					buff.setCharge(level*2);
					buff.attachTo(Dungeon.hero);
					attacker.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "zawarudo"));
					GLog.p(Messages.get(this, "timestops"));
				}
			}
			defender.damage( Random.Int( 1, level + 2 ), this );
			
		}

		return damage;

	}

	public class timeFreeze extends Buff {

		float partialTime = 1f;
		int charge = 10;

		ArrayList<Integer> presses = new ArrayList<Integer>();

		public void processTime(float time){
			partialTime += time;

			while (partialTime >= 1f){
				partialTime --;
				charge --;
			}

			QuickSlotButton.refresh();

			if (charge < 0){
				charge = 0;
				detach();
			}

		}

		public void setCharge(int charge){
			this.charge = charge;
		}

		public void setDelayedPress(int cell){
			if (!presses.contains(cell))
				presses.add(cell);
		}

		private void triggerPresses(){
			for (int cell : presses)
				Dungeon.level.press(cell, null);

			presses = new ArrayList<>();
		}

		@Override
		public boolean attachTo(Char target) {
			if (Dungeon.level != null)
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					mob.sprite.add(CharSprite.State.PARALYSED);
			GameScene.freezeEmitters = true;
			return super.attachTo(target);
		}

		@Override
		public void detach(){
			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
				mob.sprite.remove(CharSprite.State.PARALYSED);
			GameScene.freezeEmitters = false;

			QuickSlotButton.refresh();
			super.detach();
			triggerPresses();
		}

		private static final String PRESSES = "presses";
		private static final String PARTIALTIME = "partialtime";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);

			int[] values = new int[presses.size()];
			for (int i = 0; i < values.length; i ++)
				values[i] = presses.get(i);
			bundle.put( PRESSES , values );

			bundle.put( PARTIALTIME , partialTime );
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);

			int[] values = bundle.getIntArray( PRESSES );
			for (int value : values)
				presses.add(value);

			partialTime = bundle.getFloat( PARTIALTIME );
		}
	}
	
	@Override
	public Glowing glowing() {
		return FAINT_YELLOW;
	}
}
