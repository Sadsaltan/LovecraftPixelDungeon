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

package com.lovecraftpixel.lovecraftpixeldungeon.ui;

import com.lovecraftpixel.lovecraftpixeldungeon.Chrome;
import com.watabou.noosa.Game;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Button;

public class Tag extends Button {

	private float r;
	private float g;
	private float b;
	protected NinePatch bg;
	
	protected float lightness = 0;
	
	public Tag( int color ) {
		super();
		
		this.r = (color >> 16) / 255f;
		this.g = ((color >> 8) & 0xFF) / 255f;
		this.b = (color & 0xFF) / 255f;
	}
	
	@Override
	protected void createChildren() {
		
		super.createChildren();
		
		bg = Chrome.get( Chrome.Type.TAG );
		bg.hardlight( r, g, b );
		add( bg );
	}
	
	@Override
	protected void layout() {
		
		super.layout();
		
		bg.x = x;
		bg.y = y;
		bg.size( width, height );
	}
	
	public void flash() {
		lightness = 1f;
	}

	public void flip(boolean value){
		bg.flipHorizontal(value);
	}
	
	@Override
	public void update() {
		super.update();
		
		if (visible && lightness > 0.5) {
			if ((lightness -= Game.elapsed) > 0.5) {
				bg.ra = bg.ga = bg.ba = 2 * lightness - 1;
				bg.rm = 2 * r * (1 - lightness);
				bg.gm = 2 * g * (1 - lightness);
				bg.bm = 2 * b * (1 - lightness);
			} else {
				bg.hardlight( r, g, b );
			}
		}
	}
}
