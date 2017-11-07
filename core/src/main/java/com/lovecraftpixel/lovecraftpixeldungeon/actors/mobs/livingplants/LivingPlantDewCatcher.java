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

package com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.livingplants;

import com.pluginpixel.pluginpixeldungeon.Dungeon;
import com.pluginpixel.pluginpixeldungeon.items.Dewdrop;
import com.pluginpixel.pluginpixeldungeon.levels.Level;
import com.pluginpixel.pluginpixeldungeon.sprites.mods.LivingDewCatcherPlantSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class LivingPlantDewCatcher extends LivingPlant {

    {
        spriteClass = LivingDewCatcherPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        effect();
    }

    private void effect(){
        int nDrops = Random.NormalIntRange(2, 8);

        ArrayList<Integer> candidates = new ArrayList<Integer>();
        for (int i : PathFinder.NEIGHBOURS8){
            if (Level.passable[pos+i]){
                candidates.add(pos+i);
            }
        }

        for (int i = 0; i < nDrops && !candidates.isEmpty(); i++){
            Integer c = Random.element(candidates);
            Dungeon.level.drop(new Dewdrop(), c).sprite.drop(pos);
            candidates.remove(c);
        }
    }
}
