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

package com.lovecraftpixel.lovecraftpixeldungeon.levels.rooms.special;

import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.WaterOfAwareness;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.WaterOfHealth;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.WaterOfTransmutation;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.WellWater;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Level;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Terrain;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class MagicWellRoom extends SpecialRoom {

	private static final Class<?>[] WATERS =
		{WaterOfAwareness.class, WaterOfHealth.class, WaterOfTransmutation.class};
	
	public Class<?extends WellWater> overrideWater = null;
	
	public void paint( Level level ) {

		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY );
		
		Point c = center();
		Painter.set( level, c.x, c.y, Terrain.WELL );
		
		@SuppressWarnings("unchecked")
		Class<? extends WellWater> waterClass =
			overrideWater != null ?
			overrideWater :
			(Class<? extends WellWater>)Random.element( WATERS );
			
		if (waterClass == WaterOfTransmutation.class) {
			SpecialRoom.disableGuaranteedWell();
		}
		
		WellWater.seed(c.x + level.width() * c.y, 1, waterClass, level);
		
		entrance().set( Door.Type.REGULAR );
	}
}
