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
import com.pluginpixel.pluginpixeldungeon.Plugins;
import com.pluginpixel.pluginpixeldungeon.actors.Char;
import com.pluginpixel.pluginpixeldungeon.actors.blobs.Blob;
import com.pluginpixel.pluginpixeldungeon.actors.blobs.ParalyticGas;
import com.pluginpixel.pluginpixeldungeon.actors.buffs.Buff;
import com.pluginpixel.pluginpixeldungeon.actors.buffs.Roots;
import com.pluginpixel.pluginpixeldungeon.plants.Earthroot;
import com.pluginpixel.pluginpixeldungeon.scenes.GameScene;
import com.pluginpixel.pluginpixeldungeon.sprites.mods.LivingEarthrootPlantSprite;
import com.watabou.utils.Random;

public class LivingPlantEarthRoot extends LivingPlant {

    {
        spriteClass = LivingEarthrootPlantSprite.class;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        Dungeon.level.drop(new Earthroot.Seed(), pos);
        if(Dungeon.plugins.contains(Plugins.MOREGASESPLUGIN)){
            GameScene.add(Blob.seed(pos, 500, ParalyticGas.class));
        }
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if(Random.randomBoolean()) Buff.affect(enemy, Roots.class);
        return super.attackProc(enemy, damage);
    }
}
