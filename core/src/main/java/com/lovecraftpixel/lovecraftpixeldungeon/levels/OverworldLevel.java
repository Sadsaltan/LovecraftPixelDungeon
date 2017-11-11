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
import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.blobs.Blob;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.rooms.Room;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.tiles.CustomTiledVisual;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class OverworldLevel extends Level {

	{
		color1 = 0x111010;
		color2 = 0x161414;
	}

	private enum State{
		START,
		WITCH,
		BAR,
		LIB
	}

	private State state;

	@Override
	public String tilesTex() {
		return Assets.TILES_OVERWORLD;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_CAVES;
	}

	private static final String STATE	        = "state";

	@Override
	public void storeInBundle( Bundle bundle ) {
		bundle.put( STATE, state );
		super.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		state = bundle.getEnum( STATE, State.class );
		super.restoreFromBundle(bundle);
	}

	@Override
	protected boolean build() {

		setSize(16, 16);

		map = MAP_START.clone();

		buildFlagMaps();
		cleanWalls();

		state = State.START;
		entrance = 9+2*16;
		exit = 7+8*16;

		return true;
	}

	@Override
	public Group addVisuals() {
		if(state == State.START){
			CustomTiledVisual town = new ExtraTilesTown();
			town.pos(0, 0);
			customTiles.add(town);
			((GameScene) LovecraftPixelDungeon.scene()).addCustomTile(town);
		}
		if(state == State.WITCH){
			CustomTiledVisual witch = new ExtraTilesWitch();
			witch.pos(0, 0);
			customTiles.add(witch);
			((GameScene) LovecraftPixelDungeon.scene()).addCustomTile(witch);
		}
		if(state == State.BAR){
			CustomTiledVisual bar = new ExtraTilesBar();
			bar.pos(0, 0);
			customTiles.add(bar);
			((GameScene) LovecraftPixelDungeon.scene()).addCustomTile(bar);
		}
		if(state == State.LIB){
			CustomTiledVisual lib = new ExtraTilesLib();
			lib.pos(0, 0);
			customTiles.add(lib);
			((GameScene) LovecraftPixelDungeon.scene()).addCustomTile(lib);
		}
		return super.addVisuals();
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
		if (ch == Dungeon.hero){
			if (state == State.START
					&& ((Room)new Room().set(0, 12, 2, 14)).inside(cellToPoint(cell))){
				progress('w');
			}
			if (state == State.START
					&& ((Room)new Room().set(4, 3, 6, 6)).inside(cellToPoint(cell))){
				progress('b');
			}
			if (state == State.START
					&& ((Room)new Room().set(12, 12, 14, 14)).inside(cellToPoint(cell))){
				progress('l');
			}

			if (state == State.WITCH
					&& ((Room)new Room().set(0, 13, 2, 15)).inside(cellToPoint(cell))){
				progress('t');
			}
			if (state == State.BAR
					&& ((Room)new Room().set(4, 4, 6, 7)).inside(cellToPoint(cell))){
				progress('t');
			}
			if (state == State.LIB
					&& ((Room)new Room().set(12, 13, 14, 15)).inside(cellToPoint(cell))){
				progress('t');
			}
		}
		super.press(cell, ch);
	}

	@Override
	public int randomRespawnCell() {
		return 10+2*16 + PathFinder.NEIGHBOURS8[Random.Int(8)];
	}

	@Override
	public void updateFieldOfView(Char c, boolean[] fieldOfView) {
		for(int x = 0; x < 16; x++){
			for(int y = 0; y < 15; y++){
				fieldOfView[x+y*16] = true;
			}
		}
	}

	private void changeMap(int[] map){
		this.map = map.clone();
		buildFlagMaps();
		cleanWalls();

		for (Blob blob: blobs.values()){
			blob.fullyClear();
		}
		addVisuals();

		GameScene.resetMap();
		Dungeon.observe();
	}

	public void progress(Character character){
		switch (character){
			case 'w':
				state = State.WITCH;
				changeMap(MAP_WITCH);
				break;
			case 'b':
				state = State.BAR;
				changeMap(MAP_BAR);
				break;
			case 't':
				state = State.START;
				changeMap(MAP_START);
				break;
			case 'l':
				state = State.LIB;
				changeMap(MAP_LIB);
				break;
		}
	}

	private static final int e = Terrain.WATER;
	private static final int w = Terrain.EMPTY;
	private static final int s = Terrain.STATUE_SP;

	private static final int X = Terrain.EXIT;

	private static final int[] MAP_START =
			{       //1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16
				/*1*/ s, s, s, s, s, s, s, w, w, w, w, s, s, s, s, s,
				/*2*/ s, s, s, s, s, s, s, e, e, e, e, s, s, s, s, s,
				/*3*/ s, s, s, s, s, s, s, e, e, e, e, s, s, s, s, s,
				/*4*/ s, s, s, s, s, s, s, e, e, e, e, s, s, s, s, s,
				/*5*/ w, e, e, e, e, e, e, e, e, e, e, s, s, s, s, s,
				/*6*/ w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w,
				/*7*/ w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w,
				/*8*/ w, e, e, e, e, s, s, s, s, s, e, e, e, e, e, w,
				/*9*/ s, s, s, e, e, s, e, X, e, s, e, e, s, s, s, s,
				/*10*/s, s, s, e, e, s, e, e, e, s, e, e, s, s, s, s,
				/*11*/s, s, s, e, e, s, s, e, s, s, e, e, s, s, s, s,
				/*12*/s, s, s, e, e, e, e, e, e, e, e, e, s, s, s, s,
				/*13*/s, s, s, e, e, e, e, e, e, e, e, e, s, s, s, s,
				/*14*/w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w,
				/*15*/w, e, e, e, e, e, e, e, e, e, e, e, e, e, e, w,
				/*16*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
			};

	private static final int[] MAP_WITCH =
			{       //1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16
				/*1*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*2*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*3*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*4*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*5*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*6*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*7*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*8*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*9*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*10*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*11*/s, w, w, w, w, w, w, s, s, s, s, s, s, s, s, s,
				/*12*/s, s, s, w, s, w, w, s, s, s, s, s, s, s, s, s,
				/*13*/s, s, s, w, w, w, s, s, s, s, s, s, s, s, s, s,
				/*14*/s, w, w, w, w, w, s, s, s, s, s, s, s, s, s, s,
				/*15*/s, w, w, w, w, w, s, s, s, s, s, s, s, s, s, s,
				/*16*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
			};

	private static final int[] MAP_BAR =
			{       //1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16
				/*1*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*2*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*3*/ s, w, w, w, w, w, w, s, s, s, s, s, s, s, s, s,
				/*4*/ s, w, w, w, w, w, w, s, s, s, s, s, s, s, s, s,
				/*5*/ s, w, w, w, w, w, w, s, s, s, s, s, s, s, s, s,
				/*6*/ s, w, w, w, w, w, w, s, s, s, s, s, s, s, s, s,
				/*7*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*8*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*9*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*10*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*11*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*12*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*13*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*14*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*15*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*16*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
			};

	private static final int[] MAP_LIB =
			{       //1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16
				/*1*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*2*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*3*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*4*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*5*/ s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
				/*6*/ s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*7*/ s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*8*/ s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*9*/ s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*10*/s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*11*/s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*12*/s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*13*/s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*14*/s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*15*/s, s, s, s, s, s, s, s, s, s, w, w, w, w, w, s,
				/*16*/s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s,
			};

	public static class ExtraTilesTown extends CustomTiledVisual {

		public ExtraTilesTown(){
			super(Assets.OVERWORLD);
		}

		@Override
		public CustomTiledVisual create() {
			tileH = 16;
			tileW = 16;
			mapSimpleImage(0, 0);
			return super.create();
		}
	}

	public static class ExtraTilesWitch extends CustomTiledVisual {

		public ExtraTilesWitch(){
			super(Assets.OVERWORLDW);
		}

		@Override
		public CustomTiledVisual create() {
			tileH = 16;
			tileW = 16;
			mapSimpleImage(0, 0);
			return super.create();
		}
	}

	public static class ExtraTilesBar extends CustomTiledVisual {

		public ExtraTilesBar(){
			super(Assets.OVERWORLDB);
		}

		@Override
		public CustomTiledVisual create() {
			tileH = 16;
			tileW = 16;
			mapSimpleImage(0, 0);
			return super.create();
		}
	}

	public static class ExtraTilesLib extends CustomTiledVisual {

		public ExtraTilesLib(){
			super(Assets.OVERWORLDL);
		}

		@Override
		public CustomTiledVisual create() {
			tileH = 16;
			tileW = 16;
			mapSimpleImage(0, 0);
			return super.create();
		}
	}

}
