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

package com.lovecraftpixel.lovecraftpixeldungeon.scenes;

import android.content.Intent;
import android.net.Uri;

import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.effects.Flare;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.Archs;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.ExitButton;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.Icons;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.RenderedTextMultiline;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.Window;
import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.utils.Random;

public class AboutScene extends PixelScene {

	private static final String TTL_SHPX = "Lovecraft Pixel Dungeon";

	private static final String TXT_SHPX =
			"Design, Code, & Graphics: TypedScroll";

	private static final String LNK_SHPX = "www.youtube.com/watch?v=oHg5SJYRHA0";

	private static final String TTL_WATA = "Shattered Pixel Dungeon";

	private static final String TXT_WATA =
			"Design, Code & Graphics: 00Evan";
	
	private static final String LNK_WATA = "shatteredpixel.tumblr.com";
	
	@Override
	public void create() {
		super.create();

		final float colWidth = Camera.main.width / (LovecraftPixelDungeon.landscape() ? 2 : 1);
		final float colTop = (Camera.main.height / 2) - (LovecraftPixelDungeon.landscape() ? 30 : 90);
		final float wataOffset = LovecraftPixelDungeon.landscape() ? colWidth : 0;

		Image shpx = Icons.WATA.get();
		shpx.x = (colWidth - shpx.width()) / 2;
		shpx.y = colTop;
		align(shpx);
		add( shpx );

		new Flare( Random.Int(14, 28), Random.Int(50, 100) ).color(Random.Int(0x000000, 0xFFFFFF), true ).show( shpx, 0 ).angularSpeed = +Random.Int(-60, 60);
		new Flare( Random.Int(14, 28), Random.Int(50, 100) ).color(Random.Int(0x000000, 0xFFFFFF), true ).show( shpx, 0 ).angularSpeed = +Random.Int(-60, 60);
		new Flare( Random.Int(14, 28), Random.Int(50, 100) ).color(Random.Int(0x000000, 0xFFFFFF), true ).show( shpx, 0 ).angularSpeed = +Random.Int(-60, 60);

		RenderedText shpxtitle = renderText( TTL_SHPX, 8 );
		shpxtitle.hardlight( Window.SHPX_COLOR );
		add( shpxtitle );

		shpxtitle.x = (colWidth - shpxtitle.width()) / 2;
		shpxtitle.y = shpx.y + shpx.height + 5;
		align(shpxtitle);

		RenderedTextMultiline shpxtext = renderMultiline( TXT_SHPX, 8 );
		shpxtext.maxWidth((int)Math.min(colWidth, 120));
		add( shpxtext );

		shpxtext.setPos((colWidth - shpxtext.width()) / 2, shpxtitle.y + shpxtitle.height() + 12);
		align(shpxtext);

		RenderedTextMultiline shpxlink = renderMultiline( "lovecraftpixel.8chan.com", 8 );
		shpxlink.maxWidth(shpxtext.maxWidth());
		shpxlink.hardlight( Window.SHPX_COLOR );
		add( shpxlink );

		shpxlink.setPos((colWidth - shpxlink.width()) / 2, shpxtext.bottom() + 6);
		align(shpxlink);

		TouchArea shpxhotArea = new TouchArea( shpxlink.left(), shpxlink.top(), shpxlink.width(), shpxlink.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://" + LNK_SHPX ) );
				Game.instance.startActivity( intent );
			}
		};
		add( shpxhotArea );

		Image wata = Icons.SHPX.get();
		wata.x = wataOffset + (colWidth - wata.width()) / 2;
		wata.y = LovecraftPixelDungeon.landscape() ?
						colTop:
						shpxlink.top() + wata.height + 20;
		align(wata);
		add( wata );

		new Flare( Random.Int(14, 28), Random.Int(50, 100) ).color(Random.Int(0x000000, 0xFFFFFF), true ).show( wata, 0 ).angularSpeed = +Random.Int(-60, 60);
		new Flare( Random.Int(14, 28), Random.Int(50, 100) ).color(Random.Int(0x000000, 0xFFFFFF), true ).show( wata, 0 ).angularSpeed = +Random.Int(-60, 60);
		new Flare( Random.Int(14, 28), Random.Int(50, 100) ).color(Random.Int(0x000000, 0xFFFFFF), true ).show( wata, 0 ).angularSpeed = +Random.Int(-60, 60);

		RenderedText wataTitle = renderText( TTL_WATA, 8 );
		wataTitle.hardlight(Window.TITLE_COLOR);
		add( wataTitle );

		wataTitle.x = wataOffset + (colWidth - wataTitle.width()) / 2;
		wataTitle.y = wata.y + wata.height + 11;
		align(wataTitle);

		RenderedTextMultiline wataText = renderMultiline( TXT_WATA, 8 );
		wataText.maxWidth((int)Math.min(colWidth, 120));
		add( wataText );

		wataText.setPos(wataOffset + (colWidth - wataText.width()) / 2, wataTitle.y + wataTitle.height() + 12);
		align(wataText);
		
		RenderedTextMultiline wataLink = renderMultiline( LNK_WATA, 8 );
		wataLink.maxWidth((int)Math.min(colWidth, 120));
		wataLink.hardlight(Window.TITLE_COLOR);
		add(wataLink);
		
		wataLink.setPos(wataOffset + (colWidth - wataLink.width()) / 2 , wataText.bottom() + 6);
		align(wataLink);
		
		TouchArea hotArea = new TouchArea( wataLink.left(), wataLink.top(), wataLink.width(), wataLink.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://" + LNK_WATA ) );
				Game.instance.startActivity( intent );
			}
		};
		add( hotArea );

		
		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		LovecraftPixelDungeon.switchNoFade(TitleScene.class);
	}
}
