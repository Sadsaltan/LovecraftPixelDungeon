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

import android.content.Context;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.watabou.noosa.Game;
import com.watabou.utils.Random;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class is released under GNU general public license
 */

public class GibberishGenerator {
    ArrayList<String> texts = new ArrayList<String>();

    private String fileName;


    public GibberishGenerator(String filename, Context context) throws IOException{
        fileName = filename;
        refresh(context);
    }

    public void refresh(Context context) throws IOException{

        InputStream fIn;
        InputStreamReader isr;
        BufferedReader bufRead;
        String line;

        fIn = context.getAssets().open(fileName, Context.MODE_WORLD_READABLE);
        isr = new InputStreamReader(fIn);
        bufRead = new BufferedReader(isr);
        line="";

        while(line != null){
            line = bufRead.readLine();
            if(line != null && !line.equals("")){
                if(line.charAt(0) == '-'){
                    texts.add(line.substring(2));
                }
            }
        }
        bufRead.close();
        isr.close();
        fIn.close();
    }

    public String compose(int charlenght){
        String text = "";
        String currentstring;
        for(int i = charlenght; i > 0; i--){
            currentstring = texts.get(Random.Int(texts.size()-1));
            if(currentstring.length() > 1){
                i = (i - currentstring.length())+1;
            }
            text = text + currentstring;
        }
        return text;
    }

    public static String getRandomGibberish(int charlenght){
        String name;
        String pathfile;
        pathfile = Assets.GIBBERISH;
        try {
            name = new GibberishGenerator(pathfile, Game.instance).compose(charlenght);
        } catch (IOException e) {
            e.printStackTrace();
            name = "Error";
        }
        return name.replaceAll(name.substring(0, 1), name.substring(0, 1).toUpperCase());
    }
}
