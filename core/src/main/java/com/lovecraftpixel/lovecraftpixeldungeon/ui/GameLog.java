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

import com.lovecraftpixel.lovecraftpixeldungeon.scenes.PixelScene;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.CharSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.GLog;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Signal;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class GameLog extends Component implements Signal.Listener<String> {

	private static final int MAX_LINES = 3;

	private static final Pattern PUNCTUATION = Pattern.compile( ".*[.,;?! ]$" );

	private RenderedTextMultiline lastEntry;
	private int lastColor;

	private static ArrayList<Entry> entries = new ArrayList<Entry>();

	public GameLog() {
		super();
		GLog.update.replace( this );

		recreateLines();
	}

	private synchronized void recreateLines() {
		for (Entry entry : entries) {
			lastEntry = PixelScene.renderMultiline( entry.text, 6 );
			lastEntry.hardlight( lastColor = entry.color );
			add( lastEntry );
		}
	}

	public synchronized void newLine() {
		lastEntry = null;
	}

	@Override
	public synchronized void onSignal( String text ) {

		if (length != entries.size()){
			clear();
			recreateLines();
		}

		int color = CharSprite.DEFAULT;
		if (text.startsWith( GLog.POSITIVE )) {
			text = text.substring( GLog.POSITIVE.length() );
			color = CharSprite.POSITIVE;
		} else
		if (text.startsWith( GLog.NEGATIVE )) {
			text = text.substring( GLog.NEGATIVE.length() );
			color = CharSprite.NEGATIVE;
		} else
		if (text.startsWith( GLog.WARNING )) {
			text = text.substring( GLog.WARNING.length() );
			color = CharSprite.WARNING;
		} else
		if (text.startsWith( GLog.HIGHLIGHT )) {
			text = text.substring( GLog.HIGHLIGHT.length() );
			color = CharSprite.NEUTRAL;
		} else
		if (text.startsWith( GLog.BOOK )) {
			text = text.substring( GLog.BOOK.length() );
			color = CharSprite.BOOK;
		} else
		if (text.startsWith( GLog.KNOWL )) {
			text = text.substring( GLog.KNOWL.length() );
			color = CharSprite.KNOWLEDGE;
		} else
		if (text.startsWith( GLog.MENTAL )) {
			text = text.substring( GLog.MENTAL.length() );
			color = CharSprite.MENTAL;
		}

		if (lastEntry != null && color == lastColor && lastEntry.nLines < MAX_LINES) {

			String lastMessage = lastEntry.text();
			lastEntry.text( lastMessage.length() == 0 ? text : lastMessage + " " + text );

			entries.get( entries.size() - 1 ).text = lastEntry.text();

		} else {

			lastEntry = PixelScene.renderMultiline( text, 6 );
			lastEntry.hardlight( color );
			lastColor = color;
			add( lastEntry );

			entries.add( new Entry( text, color ) );

		}

		if (length > 0) {
			int nLines;
			do {
				nLines = 0;
				for (int i = 0; i < length-1; i++) {
					nLines += ((RenderedTextMultiline) members.get(i)).nLines;
				}

				if (nLines > MAX_LINES) {
					RenderedTextMultiline r = ((RenderedTextMultiline) members.get(0));
					remove(r);
					r.destroy();

					entries.remove( 0 );
				}
			} while (nLines > MAX_LINES);
			if (entries.isEmpty()) {
				lastEntry = null;
			}
		}

		layout();
	}

	@Override
	protected void layout() {
		float pos = y;
		for (int i=length-1; i >= 0; i--) {
			RenderedTextMultiline entry = (RenderedTextMultiline)members.get( i );
			entry.maxWidth((int)width);
			entry.setPos(x, pos-entry.height());
			pos -= entry.height();
		}
	}

	@Override
	public void destroy() {
		GLog.update.remove( this );
		super.destroy();
	}

	private static class Entry {
		public String text;
		public int color;
		public Entry( String text, int color ) {
			this.text = text;
			this.color = color;
		}
	}

	public static void wipe() {
		entries.clear();
	}
}
