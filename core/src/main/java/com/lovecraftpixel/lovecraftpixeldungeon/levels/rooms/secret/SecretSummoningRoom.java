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

package com.lovecraftpixel.lovecraftpixeldungeon.levels.rooms.secret;

import com.lovecraftpixel.lovecraftpixeldungeon.items.Generator;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Heap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Level;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Terrain;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.painters.Painter;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.SummoningTrap;
import com.watabou.utils.Point;

public class SecretSummoningRoom extends SecretRoom {
	
	//minimum of 3x3 traps, max of 6x6 traps
	
	@Override
	public int maxWidth() {
		return 8;
	}
	
	@Override
	public int maxHeight() {
		return 8;
	}
	
	@Override
	public void paint(Level level) {
		super.paint(level);
		
		Painter.fill(level, this, Terrain.WALL);
		Painter.fill(level, this, 1, Terrain.SECRET_TRAP);
		
		Point center = center();
		level.drop(Generator.random(), level.pointToCell(center)).type = Heap.Type.SKELETON;
		
		for (Point p : getPoints()){
			int cell = level.pointToCell(p);
			if (level.map[cell] == Terrain.SECRET_TRAP){
				level.setTrap(new SummoningTrap().hide(), cell);
			}
		}
		
		entrance().set(Door.Type.HIDDEN);
	}
	
}
