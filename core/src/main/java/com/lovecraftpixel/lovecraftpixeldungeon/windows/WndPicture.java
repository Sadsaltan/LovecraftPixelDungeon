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

package com.lovecraftpixel.lovecraftpixeldungeon.windows;

import com.watabou.noosa.Image;

public class WndPicture extends WndTabbed {

	public WndPicture(String assets, float scale) {
		super();

		Image image = new Image(assets);
		image.scale.set(scale);
		IconTitle titlebar = new IconTitle(image, null);
		titlebar.setRect( 0, 0, width, 0 );
		add( titlebar );

		resize( (int)image.width(), (int)image.height()  );
	}

	public WndPicture(String assets, float scalex, float scaley) {
		super();

		Image image = new Image(assets);
		image.scale.set(scalex, scaley);
		IconTitle titlebar = new IconTitle(image, null);
		titlebar.setRect( 0, 0, width, 0 );
		add( titlebar );

		resize( (int)image.width(), (int)image.height()  );
	}
}


