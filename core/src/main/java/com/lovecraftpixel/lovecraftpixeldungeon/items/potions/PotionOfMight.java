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

package com.lovecraftpixel.lovecraftpixeldungeon.items.potions;

import com.lovecraftpixel.lovecraftpixeldungeon.Badges;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;

public class PotionOfMight extends Potion {

	{
		initials = 6;
	}
	
	@Override
	public void apply( Hero hero ) {
		setKnown();
		
		hero.STR++;
		hero.MHP++;
		hero.HTBoost += 5;
		hero.updateHT( true );
		hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "msg_1") );
		GLog.p( Messages.get(this, "msg_2") );

		Badges.validateStrengthAttained();

		hero.increaseMentalHealth(3);
	}
	
	@Override
	public int price() {
		return isKnown() ? 100 * quantity : super.price();
	}
}
