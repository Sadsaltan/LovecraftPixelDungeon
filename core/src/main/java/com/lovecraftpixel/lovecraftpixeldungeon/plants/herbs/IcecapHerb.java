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

import com.lovecraftpixel.lovecraftpixeldungeon.actors.Actor;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Chill;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Frost;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Icecap;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Plant;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;

public class IcecapHerb extends Herb {

    {
        image = ItemSpriteSheet.ICECAP_HERB;
    }

    public Herb setSeed(Plant.Seed seed){
        this.seed = seed;
        return this;
    }

    @Override
    public void eatEffect(Hero hero) {
        for(int pos : PathFinder.NEIGHBOURS8){
            Char ch = Actor.findChar( hero.pos+pos );
            if (ch != null && !ch.immunities().contains(this.getClass())) {
                if (ch.buff(Frost.class) != null){
                    Buff.affect(ch, Frost.class, 2f);
                } else {
                    Buff.affect(ch, Chill.class, 5f);
                    Chill chill = ch.buff(Chill.class);
                    if (chill != null && chill.cooldown() >= 10f){
                        Buff.affect(ch, Frost.class, 5f);
                    }
                }
            }
        }
        super.eatEffect(hero);
    }

    @Override
    public String name() {
        return Messages.get(Icecap.class, "name")+ " " + Messages.get(Herb.class, "herb_name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc") + "\n\n" + Messages.get(Herb.class, "seed_used", seed.name()) + "\n" + Messages.get(seed.alchemyClass, "herbdesc");
    }
}
