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

package com.lovecraftpixel.lovecraftpixeldungeon.levels.painters;

import com.lovecraftpixel.lovecraftpixeldungeon.levels.Level;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Terrain;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.rooms.Room;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class HallsPainter extends RegularPainter {
	
	@Override
	protected void decorate(Level level, ArrayList<Room> rooms) {
		
		int[] map = level.map;
		int w = level.width();
		int l = level.length();
		
		for (int i=w + 1; i < l - w - 1; i++) {
			if (map[i] == Terrain.EMPTY) {
				
				int count = 0;
				for (int j = 0; j < PathFinder.NEIGHBOURS8.length; j++) {
					if ((Terrain.flags[map[i + PathFinder.NEIGHBOURS8[j]]] & Terrain.PASSABLE) > 0) {
						count++;
					}
				}
				
				if (Random.Int( 80 ) < count) {
					map[i] = Terrain.EMPTY_DECO;
				}
				
			} else
			if (map[i] == Terrain.WALL &&
					map[i-1] != Terrain.WALL_DECO && map[i-w] != Terrain.WALL_DECO &&
					Random.Int( 20 ) == 0) {
				
				map[i] = Terrain.WALL_DECO;
				
			}
		}
	}
}
