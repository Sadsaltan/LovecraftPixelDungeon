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

package com.lovecraftpixel.lovecraftpixeldungeon.levels;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Halo;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.GreenFlameParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.GrippingTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.Trap;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class OverworldLevel extends Level {

	{
		color1 = 0x111010;
		color2 = 0x161414;
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_OVERWORLD;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_CAVES;
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
	}

	@Override
	protected boolean build() {

		setSize(16, 16);

		map = MAP_START.clone();

		buildFlagMaps();
		cleanWalls();

		entrance = 9+2*16;
		exit = 7+8*16;

		resetTraps();

		return true;
	}

	private void resetTraps(){
		traps.clear();

		for (int i = 0; i < length(); i++){
			if (map[i] == Terrain.INACTIVE_TRAP) {
				Trap t = new GrippingTrap().reveal();
				t.active = false;
				setTrap(t, i);
				map[i] = Terrain.INACTIVE_TRAP;
			}
		}
	}

	@Override
	public Mob createMob() {
		return null;
	}

	@Override
	protected void createMobs() {

	}

	public Actor respawner() {
		return null;
	}

	@Override
	protected void createItems() {

	}

	@Override
	public void press( int cell, Char ch ) {
		super.press(cell, ch);
	}

	@Override
	public int randomRespawnCell() {
		return 10+2*16 + PathFinder.NEIGHBOURS8[Random.Int(8)];
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(SewerLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(SewerLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(SewerLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	public Group addVisuals() {
		super.addVisuals();
		addLevelVisuals(this, visuals);
		return visuals;
	}

	@Override
	public void updateFieldOfView(Char c, boolean[] fieldOfView) {
		for(int x = 0; x < 15; x++){
			for(int y = 0; y < 15; y++){
				fieldOfView[x+y*16] = true;
			}
		}
	}

	private static final int W = Terrain.WALL;
	private static final int B = Terrain.BARRICADE;
	private static final int e = Terrain.EMPTY;
	private static final int S = Terrain.STATUE;
	private static final int s = Terrain.STATUE_SP;
	private static final int w = Terrain.WATER;

	private static final int T = Terrain.INACTIVE_TRAP;

	private static final int X = Terrain.EXIT;

	private static final int M = Terrain.WALL_DECO;

	private static final int[] MAP_START =
			{       //1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16
				/*1*/ W, W, W, W, W, W, W, B, B, B, B, W, W, W, W, W,
				/*2*/ W, W, W, W, W, W, W, T, e, e, T, W, W, W, W, W,
				/*3*/ W, W, W, W, W, W, W, e, w, e, e, W, W, W, W, W,
				/*4*/ W, W, W, W, W, W, W, T, w, e, T, W, W, W, W, W,
				/*5*/ s, e, T, e, e, e, e, e, e, e, e, W, W, W, W, W,
				/*6*/ s, e, w, w, w, e, e, w, w, w, w, e, w, w, T, S,
				/*7*/ s, e, e, e, e, e, e, e, e, e, e, e, e, e, e, S,
				/*8*/ s, e, w, w, e, W, W, W, W, W, e, w, e, e, e, S,
				/*9*/ W, W, W, e, e, W, e, X, e, W, e, w, W, W, W, W,
				/*10*/W, W, W, e, e, W, e, e, e, W, e, e, W, W, W, W,
				/*11*/W, W, W, w, e, W, M, e, M, W, e, w, W, W, W, W,
				/*12*/W, W, W, w, e, e, e, e, e, e, e, w, W, W, W, W,
				/*13*/W, W, W, w, e, e, e, e, e, e, e, w, W, W, W, W,
				/*14*/s, e, T, e, e, e, w, w, e, e, e, w, e, e, e, S,
				/*15*/s, e, e, e, e, w, w, e, e, e, e, e, e, e, e, S,
				/*16*/W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			};

	public static void addLevelVisuals(Level level, Group group){
		for (int i=0; i < level.length(); i++) {
			if (level.map[i] == Terrain.WALL_DECO) {
				group.add( new Torch( i ) );
			}
		}
	}

	public static class Torch extends Emitter {

		private int pos;

		public Torch( int pos ) {
			super();

			this.pos = pos;

			PointF p = DungeonTilemap.tileCenterToWorld( pos );
			pos( p.x - 1, p.y + 2, 2, 0 );

			pour( GreenFlameParticle.FACTORY, 0.15f );

			add( new Halo( 12, 0x289F10, 0.4f ).point( p.x, p.y + 1 ) );
		}

		@Override
		public void update() {
			if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
				super.update();
			}
		}
	}

}
