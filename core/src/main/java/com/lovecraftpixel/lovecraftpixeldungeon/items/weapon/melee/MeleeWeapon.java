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

package com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee;

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.Char;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Amok;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Bleeding;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Blindness;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Buff;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Burning;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Frost;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Levitation;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.MagicalSleep;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Ooze;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Poison;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Slow;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Vertigo;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.buffs.Weakness;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.hero.Hero;
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
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.Potion;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfExperience;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfFrost;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfHealing;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfInvisibility;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfLevitation;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfMindVision;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfParalyticGas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfPurity;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfStrength;
import com.lovecraftpixel.lovecraftpixeldungeon.items.potions.PotionOfToxicGas;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.Weapon;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.plants.Plant;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.windows.WndBag;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MeleeWeapon extends Weapon {
	
	public int tier;

	public static final String AC_POISON = "POISON";
	public Potion poisonType = null;
	public Plant.Seed seedTypeUsed = null;
	public int poisonTurnsRemaining = 0;

	{
		poisonType = null;
		seedTypeUsed = null;
		poisonTurnsRemaining = 0;
	}

	private static final String TURNS			= "turns";
	private static final String SEED			= "seed";
	private static final String TYPE			= "type";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put(TURNS, poisonTurnsRemaining);
		bundle.put(SEED, seedTypeUsed);
		bundle.put(TYPE, poisonType);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		poisonTurnsRemaining = bundle.getInt(TURNS);
		seedTypeUsed = (Plant.Seed) bundle.get(SEED);
		poisonType = (Potion) bundle.get(TYPE);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		if (poisonType == null) {
			actions.add( AC_POISON );
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if(action.equals(AC_POISON)){
			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.SEED, Messages.get(this, "choosepoison"));
		}
	}

	public String getThisClassName(){
		return this.name;
	}

	public void setPoisonType(Potion poison, Plant.Seed seed){
		poisonType = poison;
		seedTypeUsed = seed;
		poisonTurnsRemaining = 5;
	}

	@Override
	public Emitter emitter() {
		if(poisonType == null) return null;
		Emitter emitter = new Emitter();
		emitter.pos(12.5f, 3);
		emitter.fillTarget = false;
		if(poisonType instanceof PotionOfInvisibility){
			emitter.pour(BlindweedPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfPurity){
			emitter.pour(DreamfoilPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfParalyticGas){
			emitter.pour(EarthrootPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfMindVision){
			emitter.pour(FadeleafPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfLiquidFlame){
			emitter.pour(FirebloomPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfFrost){
			emitter.pour(IceCapPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfStrength){
			emitter.pour(RotBerryPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfToxicGas){
			emitter.pour(SorrowMossPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfExperience){
			emitter.pour(StarFlowerPoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfLevitation){
			emitter.pour(StormVinePoisonParticle.FACTORY, 0.1f);
		} else if(poisonType instanceof PotionOfHealing){
			emitter.pour(SunGrassPoisonParticle.FACTORY, 0.1f);
		}
		return emitter;
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(final Item item) {
			if (item != null) {
				if(item instanceof Plant.Seed){
					Plant.Seed seed = (Plant.Seed) item;
					Potion potion = null;
					try{
						potion = (Potion)seed.alchemyClass.newInstance();
					} catch (Exception e) {
						LovecraftPixelDungeon.reportException(e);
					}
					setPoisonType(potion, seed);
				}
				item.detach(Dungeon.hero.belongings.backpack);
				Dungeon.hero.sprite.operate(Dungeon.hero.pos);
				Dungeon.hero.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(MeleeWeapon.class, "poisonedblade", getThisClassName()));
			}
		}
	};

	@Override
	public int min(int lvl) {
		return  tier +  //base
				lvl;    //level scaling
	}

	@Override
	public int max(int lvl) {
		return  5*(tier+1) +    //base
				lvl*(tier+1);   //level scaling
	}

	public int STRReq(int lvl){
		lvl = Math.max(0, lvl);
		//strength req decreases at +1,+3,+6,+10,etc.
		return (8 + tier * 2) - (int)(Math.sqrt(8 * lvl + 1) - 1)/2;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if(poisonType != null){
			float duration = Random.Float(3f, 10f);
			if(poisonType instanceof PotionOfInvisibility){
				Buff.affect(defender, Blindness.class, duration);
			} else if(poisonType instanceof PotionOfPurity){
				Buff.affect(defender, MagicalSleep.class);
			} else if(poisonType instanceof PotionOfParalyticGas){
				Buff.affect(defender, Slow.class, duration);
			} else if(poisonType instanceof PotionOfMindVision){
				Buff.affect(defender, Amok.class, duration);
			} else if(poisonType instanceof PotionOfLiquidFlame){
				Buff.affect(defender, Burning.class);
				Buff.affect(defender, Ooze.class);
			} else if(poisonType instanceof PotionOfFrost){
				Buff.affect(defender, Frost.class, duration);
			} else if(poisonType instanceof PotionOfStrength){
				Buff.affect(defender, Weakness.class, duration);
			} else if(poisonType instanceof PotionOfToxicGas){
				Buff.affect(defender, Poison.class);
			} else if(poisonType instanceof PotionOfExperience){
				Buff.affect(defender, Bleeding.class);
			} else if(poisonType instanceof PotionOfLevitation){
				Buff.affect(defender, Levitation.class, duration);
				Buff.affect(defender, Vertigo.class, duration);
			} else if(poisonType instanceof PotionOfHealing){
				//TODO: Buff
			}
			poisonTurnsRemaining--;
			if(poisonTurnsRemaining == 0){
				poisonType = null;
				seedTypeUsed = null;
			}
		}
		return super.proc(attacker, defender, damage);
	}
	
	@Override
	public String info() {

		String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, imbue.damageFactor(min()), imbue.damageFactor(max()), STRReq());
			if (STRReq() > Dungeon.hero.STR()) {
				info += " " + Messages.get(Weapon.class, "too_heavy");
			} else if (Dungeon.hero.STR() > STRReq()){
				info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
			}
		} else {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));
			if (STRReq(0) > Dungeon.hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
			}
		}

		String stats_desc = Messages.get(this, "stats_desc");
		if (!stats_desc.equals("")) info+= "\n\n" + stats_desc;

		switch (imbue) {
			case LIGHT:
				info += "\n\n" + Messages.get(Weapon.class, "lighter");
				break;
			case HEAVY:
				info += "\n\n" + Messages.get(Weapon.class, "heavier");
				break;
			case NONE:
		}

		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + Messages.get(enchantment, "desc");
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		}

		if(poisonType != null){
			info += "\n\n" + Messages.get(this, "poisoninfo", seedTypeUsed.name(), poisonTurnsRemaining);
		}
		
		return info;
	}
	
	@Override
	public int price() {
		int price = 20 * tier;
		if (hasGoodEnchant()) {
			price *= 1.5;
		}
		if (cursedKnown && (cursed || hasCurseEnchant())) {
			price /= 2;
		}
		if (levelKnown && level() > 0) {
			price *= (level() + 1);
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

}
