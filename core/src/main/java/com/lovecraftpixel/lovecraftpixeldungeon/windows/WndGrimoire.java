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

import com.lovecraftpixel.lovecraftpixeldungeon.Dungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.Mob;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.GameScene;
import com.lovecraftpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.RenderedTextMultiline;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.ScrollPane;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class WndGrimoire extends Window {

	private static final int WIDTH      = 112;
	private static final int HEIGHT     = 160;

	private static final int ITEM_HEIGHT	= 20;

	private ScrollPane list;

	private ArrayList<ListItem> mobs = new ArrayList<>();

	public WndGrimoire() {
		
		super();
		resize( WIDTH, HEIGHT );

		RenderedTextMultiline title = PixelScene.renderMultiline(Messages.get(this, "title"), 9);
		title.hardlight(TITLE_COLOR);
		title.maxWidth( WIDTH - 2 );
		title.setPos( (WIDTH - title.width())/2f, 1 + ((ITEM_HEIGHT) - title.height())/2f);
		PixelScene.align(title);
		add(title);
		
		Component content = new Component();
		
		float pos = 0;
		mobs.clear();
		for (Class<? extends Mob> mobclass : Dungeon.hero.seenEnemies) {
			ListItem mob = new ListItem( mobclass );
			mob.setRect( 0, pos, WIDTH, ITEM_HEIGHT );
			content.add( mob );
			mobs.add(mob);
			pos += mob.height();
		}

		content.setSize( WIDTH, pos );

		list = new ScrollPane( content ){
			@Override
			public void onClick( float x, float y ) {
				int size = mobs.size();
				for (int i=0; i < size; i++) {
					if (mobs.get( i ).onClick( x, y )) {
						break;
					}
				}
			}
		};
		add( list );

		list.setRect( 0, title.height()*2 + 1, WIDTH, height - title.height()*2 - 1 );
	}

	private static class ListItem extends Component {

		private Mob mob;

		private Image sprite;
		private RenderedTextMultiline label;
		private ColorBlock line;

		public ListItem( Class<? extends Mob> cl ) {
			super();

			try {
				mob = cl.newInstance();
				sprite.copy( mob.sprite() );
				label.text(Messages.titleCase(mob.name) );
			} catch (Exception e) {
				LovecraftPixelDungeon.reportException(e);
			}
		}

		@Override
		protected void createChildren() {
			sprite = new Image();
			add( sprite );

			label = PixelScene.renderMultiline( 7 );
			add( label );

			line = new ColorBlock( 1, 1, 0xFF222222);
			add(line);
		}

		@Override
		protected void layout() {
			sprite.y = y + 1 + (height - 1 - sprite.height) / 2f;
			PixelScene.align(sprite);

			line.size(width, 1);
			line.x = 0;
			line.y = y;

			label.maxWidth((int)(width - sprite.width - 1));
			label.setPos(sprite.x + sprite.width + 1,  y + 1 + (height - 1 - label.height()) / 2f);
			PixelScene.align(label);
		}

		public boolean onClick( float x, float y ) {
			if (inside( x, y )) {
				GameScene.show( new WndInfoMob(mob) );
				return true;
			} else {
				return false;
			}
		}
	}
}
