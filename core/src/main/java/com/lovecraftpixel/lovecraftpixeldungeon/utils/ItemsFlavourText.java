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

package com.lovecraftpixel.lovecraftpixeldungeon.utils;

import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.armor.Armor;
import com.lovecraftpixel.lovecraftpixeldungeon.items.artifacts.Artifact;
import com.lovecraftpixel.lovecraftpixeldungeon.items.rings.Ring;
import com.lovecraftpixel.lovecraftpixeldungeon.items.stones.InventoryStone;
import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.Wand;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsFlavourText {

    public String getText(Item item){
        String string;
        string = "msg_" + getType(item)+ "_" + Random.Int(1, 10);
        return setColorAndQoutationMarks(Messages.get(this, string));
    }

    private String setColorAndQoutationMarks(String string){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("+");
        arrayList.add("$");
        arrayList.add("~");
        arrayList.add("#");
        arrayList.add("†");
        arrayList.add("@");
        arrayList.add("∆");
        arrayList.add("π");
        arrayList.add("Ω");
        arrayList.add("∑");
        arrayList.add("€");
        arrayList.add("¥");
        return divideStringAndSetColorAndQoutationMarks(string, Random.element(arrayList));
    }

    public String divideStringAndSetColorAndQoutationMarks(String string, String colorFlag){
        String text = "";
        List<String> tokens = Arrays.asList(string.split("(?<= )|(?= )|(?<=\n)|(?=\n)"));
        for (String str : tokens){
            text += " "+colorFlag+str+colorFlag+" ";
        }
        return colorFlag+"'"+text.substring(2, text.length()-2)+"'"+colorFlag;
    }

    public String getType(Item type){
        if(type instanceof Armor){
            return "armor";
        } else if(type instanceof Wand){
            return "wand";
        } else if(type instanceof MeleeWeapon){
            return "meleew";
        } else if(type instanceof Ring){
            return "ring";
        } else if(type instanceof MissileWeapon){
            return "missilew";
        } else if(type instanceof InventoryStone){
            return "stone";
        } else if(type instanceof Artifact){
            return "artifact";
        } else {
            return null;
        }
    }
}
