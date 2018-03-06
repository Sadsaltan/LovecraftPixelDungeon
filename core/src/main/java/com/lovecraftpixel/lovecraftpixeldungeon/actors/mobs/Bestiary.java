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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs;

import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.bats.Bat;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.brutes.Brute;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.brutes.Shielded;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.crabs.Crab;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.criminals.Bandit;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.criminals.Thief;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.elementals.Elemental;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.eyes.Eye;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.gnolls.Gnoll;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.golems.Golem;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.guards.Guard;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.monks.Monk;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.monks.Senior;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.rats.Albino;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.rats.Rat;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.scorpions.Acidic;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.scorpions.Scorpio;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.shamans.Shaman;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.skeletons.Skeleton;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.spinners.Spinner;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.succubi.Succubus;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.swarms.Swarm;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.hostile.warlocks.Warlock;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Bestiary {
	
	public static ArrayList<Class<? extends Mob>> getMobRotation(int depth ){
		ArrayList<Class<? extends Mob>> mobs = standardMobRotation( depth );
		addRareMobs(depth, mobs);
		swapMobAlts(mobs);
		Random.shuffle(mobs);
		return mobs;
	}
	
	//returns a rotation of standard mobs, unshuffled.
	private static ArrayList<Class<? extends Mob>> standardMobRotation( int depth ){
		switch(depth){
			
			// Sewers
			case 1: default:
				//1x rat
				return new ArrayList<Class<? extends Mob>>(Arrays.asList(
						Rat.class));
			case 2:
				//10x rat
				return new ArrayList<Class<? extends Mob>>(Arrays.asList(
						Rat.class, Rat.class, Rat.class, Rat.class, Rat.class,
						Rat.class, Rat.class, Rat.class, Rat.class, Rat.class));
			case 3:
				//4x rat, 3x gnoll, 1x swarm
				return new ArrayList<>(Arrays.asList(Rat.class, Rat.class,
						Rat.class, Rat.class, Gnoll.class, Gnoll.class, Gnoll.class,
						Swarm.class));
			case 4:
				//1x rat, 2x gnoll, 2x crab, 2x swarm
				return new ArrayList<>(Arrays.asList(Rat.class,
						Gnoll.class, Gnoll.class,
						Crab.class, Crab.class, Swarm.class,
						Swarm.class));
				
			// Prison
			case 6:
				//3x skeleton, 1x thief, 1x swarm
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Swarm.class));
			case 7:
				//3x skeleton, 1x thief, 1x shaman, 1x guard
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Shaman.class,
						Guard.class));
			case 8:
				//3x skeleton, 1x thief, 2x shaman, 2x guard
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Shaman.class, Shaman.class,
						Guard.class, Guard.class));
			case 9:
				//3x skeleton, 1x thief, 2x shaman, 3x guard
				return new ArrayList<>(Arrays.asList(Skeleton.class, Skeleton.class, Skeleton.class,
						Thief.class,
						Shaman.class, Shaman.class,
						Guard.class, Guard.class, Guard.class));
				
			// Caves
			case 11:
				//5x bat, 1x brute
				return new ArrayList<>(Arrays.asList(
						Bat.class, Bat.class, Bat.class, Bat.class, Bat.class,
						Brute.class));
			case 12:
				//5x bat, 5x brute, 1x spinner
				return new ArrayList<>(Arrays.asList(
						Bat.class, Bat.class, Bat.class, Bat.class, Bat.class,
						Brute.class, Brute.class, Brute.class, Brute.class, Brute.class,
						Spinner.class));
			case 13:
				//1x bat, 3x brute, 1x shaman, 1x spinner
				return new ArrayList<>(Arrays.asList(
						Bat.class,
						Brute.class, Brute.class, Brute.class,
						Shaman.class,
						Spinner.class));
			case 14:
				//1x bat, 3x brute, 1x shaman, 4x spinner
				return new ArrayList<>(Arrays.asList(
						Bat.class,
						Brute.class, Brute.class, Brute.class,
						Shaman.class,
						Spinner.class, Spinner.class, Spinner.class, Spinner.class));
				
			// City
			case 16:
				//5x elemental, 5x warlock, 1x monk
				return new ArrayList<>(Arrays.asList(
						Elemental.class, Elemental.class, Elemental.class, Elemental.class, Elemental.class,
						Warlock.class, Warlock.class, Warlock.class, Warlock.class, Warlock.class,
						Monk.class));
			case 17:
				//2x elemental, 2x warlock, 2x monk
				return new ArrayList<>(Arrays.asList(
						Elemental.class, Elemental.class,
						Warlock.class, Warlock.class,
						Monk.class, Monk.class));
			case 18:
				//1x elemental, 1x warlock, 2x monk, 1x golem
				return new ArrayList<>(Arrays.asList(
						Elemental.class,
						Warlock.class,
						Monk.class, Monk.class,
						Golem.class));
			case 19:
				//1x elemental, 1x warlock, 2x monk, 3x golem
				return new ArrayList<>(Arrays.asList(
						Elemental.class,
						Warlock.class,
						Monk.class, Monk.class,
						Golem.class, Golem.class, Golem.class));
				
			// Halls
			case 22:
				//3x succubus, 3x evil eye
				return new ArrayList<>(Arrays.asList(
						Succubus.class, Succubus.class, Succubus.class,
						Eye.class, Eye.class, Eye.class));
			case 23:
				//2x succubus, 4x evil eye, 2x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class, Succubus.class,
						Eye.class, Eye.class, Eye.class, Eye.class,
						Scorpio.class, Scorpio.class));
			case 24:
				//1x succubus, 2x evil eye, 3x scorpio
				return new ArrayList<>(Arrays.asList(
						Succubus.class,
						Eye.class, Eye.class,
						Scorpio.class, Scorpio.class, Scorpio.class));
		}
		
	}
	
	//has a chance to add a rarely spawned mobs to the rotation
	public static void addRareMobs( int depth, ArrayList<Class<?extends Mob>> rotation ){
		
		switch (depth){
			
			// Sewers
			default:
				return;
			case 4:
				if (Random.Float() < 0.01f) rotation.add(Skeleton.class);
				if (Random.Float() < 0.01f) rotation.add(Thief.class);
				return;
				
			// Prison
			case 6:
				if (Random.Float() < 0.2f)  rotation.add(Shaman.class);
				return;
			case 8:
				if (Random.Float() < 0.02f) rotation.add(Bat.class);
				return;
			case 9:
				if (Random.Float() < 0.02f) rotation.add(Bat.class);
				if (Random.Float() < 0.01f) rotation.add(Brute.class);
				return;
				
			// Caves
			case 13:
				if (Random.Float() < 0.02f) rotation.add(Elemental.class);
				return;
			case 14:
				if (Random.Float() < 0.02f) rotation.add(Elemental.class);
				if (Random.Float() < 0.01f) rotation.add(Monk.class);
				return;
				
			// City
			case 19:
				if (Random.Float() < 0.02f) rotation.add(Succubus.class);
				return;
		}
	}
	
	//switches out regular mobs for their alt versions when appropriate
	private static void swapMobAlts(ArrayList<Class<?extends Mob>> rotation){
		for (int i = 0; i < rotation.size(); i++){
			if (Random.Int( 50 ) == 0) {
				Class<? extends Mob> cl = rotation.get(i);
				if (cl == Rat.class) {
					cl = Albino.class;
				} else if (cl == Thief.class) {
					cl = Bandit.class;
				} else if (cl == Brute.class) {
					cl = Shielded.class;
				} else if (cl == Monk.class) {
					cl = Senior.class;
				} else if (cl == Scorpio.class) {
					cl = Acidic.class;
				}
				rotation.set(i, cl);
			}
		}
	}
}
