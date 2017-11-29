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

import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Bless;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.EarthImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.FireImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.ToxicImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.Potion;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfParalyticGas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Plant;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Starflower;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.RandomL;

public class StarflowerHerb extends Herb {

    {
        image = ItemSpriteSheet.STARFLOWER_HERB;
    }

    public Herb setSeed(Plant.Seed seed){
        this.seed = seed;
        return this;
    }

    @Override
    public void eatEffect(Hero hero) {
        curItem.detach(hero.belongings.backpack);
        if(RandomL.randomBoolean()){
            hero.increaseMentalHealth(2);
            hero.loseKnowl(2);
        } else {
            hero.reduceMentalHealth(2);
            hero.gainKnowl(2);
        }
        try {
            Potion potion = (Potion) seed.alchemyClass.newInstance();
            if(potion instanceof PotionOfLiquidFlame){
                Buff.affect(hero, FireImbue.class).set(FireImbue.DURATION);
            } else
            if(potion instanceof PotionOfToxicGas){
                Buff.affect(hero, ToxicImbue.class).set(ToxicImbue.DURATION);
            } else
            if(potion instanceof PotionOfParalyticGas){
                Buff.affect(hero, EarthImbue.class, EarthImbue.DURATION);
            } else {
                potion.apply(hero);
            }
        } catch (Exception e) {
            LovecraftPixelDungeon.reportException(e);
        }

        Buff.prolong(hero, Bless.class, Bless.DURATION*2);
    }

    @Override
    public String name() {
        return Messages.get(Starflower.class, "name")+ " " + Messages.get(Herb.class, "herb_name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc") + "\n\n" + Messages.get(Herb.class, "seed_used", seed.name()) + "\n" + Messages.get(seed.alchemyClass, "desc");
    }
}
