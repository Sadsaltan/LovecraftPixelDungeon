/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.noosa.audio;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.watabou.noosa.Game;

import java.io.IOException;

public enum Music implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
	
	INSTANCE;
	
	private MediaPlayer player;
	
	private String lastPlayed;
	private boolean looping;
	
	private boolean enabled = true;
	
	public void play( String assetName, boolean looping ) {
		
		if (isPlaying() && lastPlayed.equals( assetName )) {
			return;
		}
		
		stop();
		
		lastPlayed = assetName;
		this.looping = looping;

		if (!enabled || assetName == null) {
			return;
		}
		
		try {
			
			AssetFileDescriptor afd = Game.instance.getAssets().openFd( assetName );
			
			MediaPlayer mp = new MediaPlayer();
			mp.setAudioStreamType( AudioManager.STREAM_MUSIC );
			mp.setDataSource( afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength() );
			mp.setOnPreparedListener( this );
			mp.setOnErrorListener( this );
			mp.prepareAsync();
			
		} catch (IOException e) {
			
			player = null;
			
		}
	}
	
	public void mute() {
		lastPlayed = null;
		stop();
	}

	@Override
	public void onPrepared( MediaPlayer mp ) {
		player = mp;
		player.start();
		player.setLooping(looping);
	}
	
	@Override
	public boolean onError( MediaPlayer mp, int what, int extra ) {
		mp.release();
		player = null;
		return true;
	}
	
	public void pause() {
		if (player != null) {
			player.pause();
		}
	}
	
	public void resume() {
		if (player != null) {
			player.start();
			player.setLooping(looping);
		}
	}
	
	public void stop() {
		if (player != null) {
			player.stop();
			player.release();
			player = null;
		}
	}
	
	public void volume( float value ) {
		if (player != null) {
			player.setVolume( value, value );
		}
	}
	
	public boolean isPlaying() {
		return player != null && player.isPlaying();
	}
	
	public void enable( boolean value ) {
		enabled = value;
		if (isPlaying() && !value) {
			stop();
		} else
		if (!isPlaying() && value) {
			play( lastPlayed, looping );
		}
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public static final PhoneStateListener callMute = new PhoneStateListener(){
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{
			if( state == TelephonyManager.CALL_STATE_RINGING ) {
				INSTANCE.pause();
				
			} else if( state == TelephonyManager.CALL_STATE_IDLE ) {
				if (!Game.instance.isPaused()) {
					INSTANCE.resume();
				}
			}
			
			super.onCallStateChanged(state, incomingNumber);
		}
	};
}
