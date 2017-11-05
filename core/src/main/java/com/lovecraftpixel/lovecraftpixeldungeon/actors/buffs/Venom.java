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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs;

import com.lovecraftpixel.lovecraftpixeldungeon.Badges;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Languages;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.BuffIndicator;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

//FIXME do proper translation stuff for new text here in 0.6.3 (heromsg, ondeath, rankings_desc)
public class Venom extends Buff implements Hero.Doom {

	private int damage = 1;
	protected float left;

	private static final String DAMAGE	= "damage";
	private static final String LEFT	= "left";

	{
		type = buffType.NEGATIVE;
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( DAMAGE, damage );
		bundle.put( LEFT, left );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		damage = bundle.getInt( DAMAGE );
		left = bundle.getFloat( LEFT );
	}

	public void set(float duration, int damage) {
		this.left = Math.max(duration, left);
		if (this.damage < damage) this.damage = damage;
	}

	@Override
	public int icon() {
		return BuffIndicator.POISON;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns(left), damage);
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {
			target.damage(damage, this);
			if (damage < ((Dungeon.depth+1)/2)+1)
				damage++;
			
			spend( TICK );
			if ((left -= TICK) <= 0) {
				detach();
			}
		} else {
			detach();
		}

		return true;
	}
	
	@Override
	public void onDeath() {
		Badges.validateDeathFromPoison();
		
		Dungeon.fail( getClass() );
		if (Messages.lang() == Languages.ENGLISH){
			GLog.n("You died from venom...");
		} else {
			GLog.n(Messages.get(Poison.class, "ondeath"));
		}
	}

}
