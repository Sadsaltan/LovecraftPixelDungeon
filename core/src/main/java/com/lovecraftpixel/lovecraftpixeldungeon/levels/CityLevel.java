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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.npcs.Imp;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.painters.CityPainter;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.painters.Painter;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.BlazingTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.CursingTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.DisarmingTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.DisintegrationTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.ExplosiveTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.FlashingTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.FrostTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.GuardianTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.PitfallTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.RockfallTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.StormTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.SummoningTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.VenomTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.WarpingTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.levels.traps.WeakeningTrap;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class CityLevel extends RegularLevel {

	{
		color1 = 0x4b6636;
		color2 = 0xf2f2f2;
	}
	
	@Override
	protected int standardRooms() {
		//7 to 10, average 7.9
		return 12+Random.chances(new float[]{4, 3, 2, 1});
	}
	
	@Override
	protected int specialRooms() {
		//2 to 3, average 2.33
		return 4 + Random.chances(new float[]{2, 1});
	}
	
	@Override
	public String tilesTex() {
		return Assets.TILES_CITY;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_CITY;
	}
	
	@Override
	protected Painter painter() {
		return new CityPainter()
				.setWater(feeling == Feeling.WATER ? 0.90f : 0.30f, 4)
				.setGrass(feeling == Feeling.GRASS ? 0.80f : 0.20f, 3)
				.setTraps(nTraps(), trapClasses(), trapChances());
	}
	
	@Override
	protected Class<?>[] trapClasses() {
		return new Class[]{ FrostTrap.class, StormTrap.class, VenomTrap.class, BlazingTrap.class, DisintegrationTrap.class,
				ExplosiveTrap.class, RockfallTrap.class, FlashingTrap.class, GuardianTrap.class, WeakeningTrap.class,
				SummoningTrap.class, WarpingTrap.class, CursingTrap.class,
				PitfallTrap.class, DisarmingTrap.class };
	}

	@Override
	protected float[] trapChances() {
		return new float[]{ 8, 8, 8, 8, 8,
				4, 4, 4, 4, 4,
				2, 2, 2,
				1, 1 };
	}
	
	@Override
	protected void createItems() {
		super.createItems();
		
		Imp.Quest.spawn( this );
	}
	
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(CityLevel.class, "water_name");
			case Terrain.HIGH_GRASS:
				return Messages.get(CityLevel.class, "high_grass_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.ENTRANCE:
				return Messages.get(CityLevel.class, "entrance_desc");
			case Terrain.EXIT:
				return Messages.get(CityLevel.class, "exit_desc");
			case Terrain.WALL_DECO:
			case Terrain.EMPTY_DECO:
				return Messages.get(CityLevel.class, "deco_desc");
			case Terrain.EMPTY_SP:
				return Messages.get(CityLevel.class, "sp_desc");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(CityLevel.class, "statue_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(CityLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}
	
	@Override
	public Group addVisuals() {
		super.addVisuals();
		addCityVisuals( this, visuals );
		return visuals;
	}

	public static void addCityVisuals( Level level, Group group ) {
		for (int i=0; i < level.length(); i++) {
			if (level.map[i] == Terrain.WALL_DECO) {
				group.add( new Smoke( i ) );
			}
		}
	}
	
	private static class Smoke extends Emitter {
		
		private int pos;
		
		private static final Emitter.Factory factory = new Factory() {
			
			@Override
			public void emit( Emitter emitter, int index, float x, float y ) {
				SmokeParticle p = (SmokeParticle)emitter.recycle( SmokeParticle.class );
				p.reset( x, y );
			}
		};
		
		public Smoke( int pos ) {
			super();
			
			this.pos = pos;
			
			PointF p = DungeonTilemap.tileCenterToWorld( pos );
			pos( p.x - 6, p.y - 4, 12, 12 );
			
			pour( factory, 0.2f );
		}
		
		@Override
		public void update() {
			if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
				super.update();
			}
		}
	}
	
	public static final class SmokeParticle extends PixelParticle {
		
		public SmokeParticle() {
			super();
			
			color( 0x000000 );
			speed.set( Random.Float( -2, 4 ), -Random.Float( 3, 6 ) );
		}
		
		public void reset( float x, float y ) {
			revive();
			
			this.x = x;
			this.y = y;
			
			left = lifespan = 2f;
		}
		
		@Override
		public void update() {
			super.update();
			float p = left / lifespan;
			am = p > 0.8f ? 1 - p : p * 0.25f;
			size( 6 - p * 3 );
		}
	}
}