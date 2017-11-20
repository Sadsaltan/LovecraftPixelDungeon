package com.lovecraftpixel.lovecraftpixeldungeon.utils;

import android.content.Context;

import com.lovecraftpixel.lovecraftpixeldungeon.Assets;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
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

public class BookTitles {
    ArrayList<String> texts = new ArrayList<String>();

    private String fileName;


    public BookTitles(String filename, Context context) throws IOException{
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
                    texts.add(line.substring(1));
                }
            }
        }
        bufRead.close();
        isr.close();
        fIn.close();
    }

    public String compose(){
        String text = texts.get(Random.Int(texts.size()-1));
        return Messages.get(this, "foundbook")+" "+text;
    }

    public static String getRandomTitle(){
        String name;
        String pathfile;
        pathfile = Assets.BOOKTITLES;
        try {
            name = new BookTitles(pathfile, Game.instance).compose();
        } catch (IOException e) {
            e.printStackTrace();
            name = "Error";
        }
        return name;
    }
}
