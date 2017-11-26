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
import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.EarthImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.FireImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.ToxicImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.CellEmitter;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.EarthParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.Potion;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfParalyticGas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Earthroot;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Plant;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.RandomL;
import com.watabou.noosa.Camera;

public class EarthrootHerb extends Herb {

    {
        image = ItemSpriteSheet.FIREBLOOM_HERB;
    }

    public Herb setSeed(Plant.Seed seed){
        this.seed = seed;
        return this;
    }

    @Override
    public void execute(final Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_EAT )) {
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

            effectChar(hero);
        }
    }

    private void effectChar(Char ch){

        Buff.affect( ch, Earthroot.Armor.class ).level(ch.HT*2);

        if (Dungeon.level.heroFOV[ch.pos]) {
            CellEmitter.bottom( ch.pos ).start( EarthParticle.FACTORY, 0.05f, 4 );
            Camera.main.shake( 0.5f, 0.2f );
        }
    }

    @Override
    public String name() {
        return Messages.get(Earthroot.class, "name")+ " " + Messages.get(Herb.class, "herb_name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc") + "\n\n" + Messages.get(Herb.class, "seed_used", seed.name()) + "\n" + Messages.get(seed.alchemyClass, "desc");
    }
}
