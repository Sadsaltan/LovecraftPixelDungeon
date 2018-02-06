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
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.EarthImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.FireImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Hunger;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.ToxicImbue;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.BlandFruitBushPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.BlindweedPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.DreamfoilPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.EarthrootPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.FadeleafPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.FirebloomPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.IceCapPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.RotBerryPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.SorrowMossPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.StarFlowerPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.StormVinePoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.particles.SunGrassPoisonParticle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Generator;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.Potion;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfParalyticGas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.BlandfruitBush;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Blindweed;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Dreamfoil;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Earthroot;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Fadeleaf;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Firebloom;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Icecap;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Plant;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Rotberry;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Sorrowmoss;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Starflower;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Stormvine;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Sungrass;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.RandomL;
import com.watabou.noosa.particles.Emitter;

import java.util.ArrayList;

public class Herb extends Item {

    public static final String AC_EAT = "EAT";
    public float energy = Hunger.HUNGRY;

    ArrayList<Herb> herbs = new ArrayList<Herb>();

    {
        stackable = false;
    }

    public Plant.Seed seed;

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions(hero);
        actions.add( AC_EAT );
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        if (action.equals( AC_EAT )) {
            hero.buff( Hunger.class ).satisfy( energy );
            this.detach(hero.belongings.backpack);
            if(RandomL.randomBoolean()){
                hero.increaseMentalHealth(2);
                hero.loseKnowl(2);
            } else {
                hero.reduceMentalHealth(2);
                hero.gainKnowl(2);
            }
            eatEffect(hero);
            try {
                Potion potion = (Potion) seed.alchemyClass.newInstance();
                if(potion instanceof PotionOfLiquidFlame){
                    Buff.affect(hero, FireImbue.class).set(FireImbue.DURATION);
                    new PotionOfLiquidFlame().shatter(hero.pos);
                } else
                if(potion instanceof PotionOfToxicGas){
                    Buff.affect(hero, ToxicImbue.class).set(ToxicImbue.DURATION);
                    new PotionOfToxicGas().shatter(hero.pos);
                } else
                if(potion instanceof PotionOfParalyticGas){
                    Buff.affect(hero, EarthImbue.class, EarthImbue.DURATION);
                    new PotionOfParalyticGas().shatter(hero.pos);
                } else {
                    potion.apply(hero);
                }
            } catch (Exception e) {
                LovecraftPixelDungeon.reportException(e);
            }
        }
    }

    public void eatEffect(Hero hero){

    }

    @Override
    public boolean collect() {
        if(seed == null){
            seed = (Plant.Seed) Generator.random(Generator.Category.SEED);
        }
        return super.collect();
    }

    @Override
    public Emitter emitter() {
        if(seed == null) return null;
        Emitter emitter = new Emitter();
        emitter.pos(12.5f, 3);
        emitter.fillTarget = false;
        if(seed instanceof Blindweed.Seed){
            emitter.pour(BlindweedPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Dreamfoil.Seed){
            emitter.pour(DreamfoilPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Earthroot.Seed){
            emitter.pour(EarthrootPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Fadeleaf.Seed){
            emitter.pour(FadeleafPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Firebloom.Seed){
            emitter.pour(FirebloomPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Icecap.Seed){
            emitter.pour(IceCapPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Rotberry.Seed){
            emitter.pour(RotBerryPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Sorrowmoss.Seed){
            emitter.pour(SorrowMossPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Starflower.Seed){
            emitter.pour(StarFlowerPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Stormvine.Seed){
            emitter.pour(StormVinePoisonParticle.FACTORY, 1f);
        } else if(seed instanceof Sungrass.Seed){
            emitter.pour(SunGrassPoisonParticle.FACTORY, 1f);
        } else if(seed instanceof BlandfruitBush.Seed){
            emitter.pour(BlandFruitBushPoisonParticle.FACTORY, 1f);
        }
        return emitter;
    }

    @Override
    public int price() {
        return 5 * quantity;
    }
}
