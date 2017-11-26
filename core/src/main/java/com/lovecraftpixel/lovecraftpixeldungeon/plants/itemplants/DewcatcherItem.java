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

package com.lovecraftpixel.lovecraftpixeldungeon.plants.itemplants;

import com.lovecraftpixel.lovecraftpixeldungeon.items.wands.WandOfRegrowth;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;

public class DewcatcherItem extends PlantItem {

    {
        image = ItemSpriteSheet.PLANT_ITEMS_DEWCATCHER;
        seed = new WandOfRegrowth.Dewcatcher.Seed();
    }

    @Override
    public String name() {
        return Messages.get(WandOfRegrowth.Dewcatcher.class, "name");
    }

    @Override
    public String desc() {
        String info = super.desc();
        info = info + "\n\n" + Messages.get(WandOfRegrowth.Dewcatcher.class, "desc");
        return info;
    }
}
