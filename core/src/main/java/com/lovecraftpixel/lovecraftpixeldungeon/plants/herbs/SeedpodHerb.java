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

package com.lovecraftpixel.lovecraftpixeldungeon.plants.herbs;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Generator;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfRegrowth;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Plant;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SeedpodHerb extends Herb {

    {
        image = ItemSpriteSheet.SEEDPOD_HERB;
    }

    public Herb setSeed(Plant.Seed seed){
        this.seed = seed;
        return this;
    }

    @Override
    public void eatEffect(Hero hero) {
        effectChar(hero.pos);
        hero.damage(1, this);
        super.eatEffect(hero);
    }

    private void effectChar(int pos){
        int nSeeds = 8;

        ArrayList<Integer> candidates = new ArrayList<Integer>();
        for (int i : PathFinder.NEIGHBOURS8){
            if (Dungeon.level.passable[pos+i]){
                candidates.add(pos+i);
            }
        }

        for (int i = 0; i < nSeeds && !candidates.isEmpty(); i++){
            Integer c = Random.element(candidates);
            Dungeon.level.drop(Generator.random(Generator.Category.SEED), c).sprite.drop(pos);
            candidates.remove(c);
        }
    }

    @Override
    public String name() {
        return Messages.get(WandOfRegrowth.Seedpod.class, "name")+ " " + Messages.get(Herb.class, "herb_name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc") + "\n\n" + Messages.get(Herb.class, "seed_used", this.seed.name()) + "\n" + Messages.get(this.seed.alchemyClass, "herbdesc");
    }
}
