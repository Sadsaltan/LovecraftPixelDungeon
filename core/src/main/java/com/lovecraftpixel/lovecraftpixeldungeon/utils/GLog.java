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

import android.util.Log;

import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.watabou.utils.Signal;

public class GLog {

	public static final String TAG = "GAME";
	
	public static final String POSITIVE		= "++ ";
	public static final String NEGATIVE		= "-- ";
	public static final String WARNING		= "** ";
	public static final String HIGHLIGHT	= "@@ ";
	public static final String BOOK			= "†† ";
	public static final String KNOWL		= "ΩΩ ";
	public static final String MENTAL		= "ππ ";
	
	public static Signal<String> update = new Signal<String>();
	
	public static void i( String text, Object... args ) {
		
		if (args.length > 0) {
			text = Messages.format( text, args );
		}
		
		Log.i( TAG, text );
		update.dispatch( text );
	}
	
	public static void p( String text, Object... args ) {
		i( POSITIVE + text, args );
	}
	
	public static void n( String text, Object... args ) {
		i( NEGATIVE + text, args );
	}
	
	public static void w( String text, Object... args ) {
		i( WARNING + text, args );
	}
	
	public static void h( String text, Object... args ) {
		i( HIGHLIGHT + text, args );
	}

	public static void b( String text, Object... args ) {
		i( BOOK + text, args );
	}

	public static void k( String text, Object... args ) {
		i( KNOWL + text, args );
	}

	public static void m( String text, Object... args ) {
		i( MENTAL + text, args );
	}
}
