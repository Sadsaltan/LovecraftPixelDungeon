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

package com.lovecraftpixel.lovecraftpixeldungeon.levels.rooms.standard;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.items.quest.YellowSign;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Level;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.Terrain;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.painters.Painter;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.tiles.CustomTiledVisual;
import com.watabou.utils.Point;

public class YellowSignRoom extends StandardRoom {
	
	@Override
	public int minWidth() {
		return Math.max(super.minWidth(), 5);
	}
	
	@Override
	public int minHeight() {
		return Math.max(super.minHeight(), 5);
	}

	public void paint( Level level ) {

		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

		Painter.fill(level, this, Terrain.WALL);
		Painter.fill(level, this, 1, Terrain.EMPTY);

		YellowSignMaker vis = new YellowSignMaker();
		Point c = center();
		vis.pos(c.x - 1, c.y - 1);

		level.customTiles.add(vis);
		
		Painter.fill(level, c.x-1, c.y-1, 3, 3, Terrain.EMPTY_DECO);

		level.addItemToSpawn(new YellowSign());

		YellowSign.ritualPos = c.x + (level.width() * c.y);
	}

	public static class YellowSignMaker extends CustomTiledVisual {

		public YellowSignMaker(){
			super( Assets.YELLOW_SIGN );
		}

		@Override
		public CustomTiledVisual create() {
			tileH = tileW = 3;
			mapSimpleImage(0, 0);
			return super.create();
		}

		@Override
		public String name(int tileX, int tileY) {
			return Messages.get(YellowSign.class, "name");
		}

		@Override
		public String desc(int tileX, int tileY) {
			return Messages.get(YellowSign.class, "desc");
		}
	}

}
