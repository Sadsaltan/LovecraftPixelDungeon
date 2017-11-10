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
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenderedTextMultiline extends Component {

	private int maxWidth = Integer.MAX_VALUE;
	public int nLines;

	private String text;
	private List<String> tokens;
	private ArrayList<RenderedText> words = new ArrayList<>();

	private int size;
	private float zoom;
	private int color = -1;

	private static final String SPACE = " ";
	private static final String NEWLINE = "\n";
	private static final String UNDERSCORE = "_";
	private static final String CROSS = "+";
	private static final String MONEY = "$";
	private static final String WAVES = "~";
	private static final String GUTTER = "#";
	private static final String DEAD = "†";
	private static final String AT = "@";
	private static final String TRIANGLE = "∆";
	private static final String PI = "π";
	private static final String OMEGA = "Ω";
	private static final String E = "∑";
	private static final String EURO = "€";
	private static final String YEN = "¥";

	private boolean chinese = false;

	public RenderedTextMultiline(int size){
		this.size = size;
	}

	public RenderedTextMultiline(String text, int size){
		this.size = size;
		text(text);
	}

	public void text(String text){
		this.text = text;

		if (text != null && !text.equals("")) {
			//conversion for chinese text

			chinese = text.replaceAll("\\p{Han}", "").length() != text.length();

			if (chinese){
				tokens = Arrays.asList(text.split(""));
			} else {
				tokens = Arrays.asList(text.split("(?<= )|(?= )|(?<=\n)|(?=\n)"));
			}
			build();
		}
	}

	public void text(String text, int maxWidth){
		this.maxWidth = maxWidth;
		text(text);
	}

	public String text(){
		return text;
	}

	public void maxWidth(int maxWidth){
		if (this.maxWidth != maxWidth){
			this.maxWidth = maxWidth;
			layout();
		}
	}

	public int maxWidth(){
		return maxWidth;
	}

	private synchronized void build(){
		clear();
		words = new ArrayList<>();
		boolean highlighting = false;
		for (String str : tokens){
			if (str.equals(UNDERSCORE) || str.equals(CROSS) || str.equals(MONEY) || str.equals(WAVES) || str.equals(GUTTER)
					|| str.equals(DEAD) || str.equals(AT) || str.equals(TRIANGLE) || str.equals(PI)
					|| str.equals(OMEGA) || str.equals(E) || str.equals(EURO) || str.equals(YEN)){
				highlighting = !highlighting;
			} else if (str.equals(NEWLINE)){
				words.add(null);
			} else if (!str.equals(SPACE)){
				RenderedText word;
				if (str.startsWith(UNDERSCORE) && str.endsWith(UNDERSCORE)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0xFFFF44); //yellow
				} else if (str.startsWith(CROSS) && str.endsWith(CROSS)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x9A1586); //purple
				} else if (str.startsWith(MONEY) && str.endsWith(MONEY)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x8DC324); //melon
				} else if (str.startsWith(WAVES) && str.endsWith(WAVES)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x26F5F9); //lightblue
				} else if (str.startsWith(GUTTER) && str.endsWith(GUTTER)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0xD40B0B); //red
				} else if (str.startsWith(DEAD) && str.endsWith(DEAD)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x39E522); //green
				} else if (str.startsWith(AT) && str.endsWith(AT)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0xDB7937); //orange
				} else if (str.startsWith(TRIANGLE) && str.endsWith(TRIANGLE)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x373FDB); // dark blue
				} else if (str.startsWith(PI) && str.endsWith(PI)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0xDB78AF); //pink
				} else if (str.startsWith(OMEGA) && str.endsWith(OMEGA)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x6B0030); //burgund
				} else if (str.startsWith(E) && str.endsWith(E)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x034700); //darkgreen
				} else if (str.startsWith(EURO) && str.endsWith(EURO)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x3439ff); //blue
				}else if (str.startsWith(YEN) && str.endsWith(YEN)){
					word = new RenderedText(str.substring(1, str.length()-1), size);
					word.hardlight(0x99A20A); //dark yellow
				}else {
					if (str.startsWith(UNDERSCORE) || str.startsWith(CROSS) || str.startsWith(MONEY) || str.startsWith(WAVES) || str.startsWith(GUTTER)
							|| str.startsWith(DEAD) || str.startsWith(AT) || str.startsWith(TRIANGLE) || str.startsWith(PI)
							|| str.startsWith(OMEGA) || str.startsWith(E) || str.startsWith(EURO) || str.startsWith(YEN)){
						highlighting = !highlighting;
						word = new RenderedText(str.substring(1, str.length()), size);
					} else if (str.endsWith(UNDERSCORE) || str.endsWith(CROSS) || str.endsWith(MONEY) || str.endsWith(WAVES) || str.endsWith(GUTTER)
							|| str.endsWith(DEAD) || str.endsWith(AT) || str.endsWith(TRIANGLE) || str.endsWith(PI)
							|| str.endsWith(OMEGA) || str.endsWith(E) || str.endsWith(EURO) || str.endsWith(YEN)) {
						word = new RenderedText(str.substring(0, str.length()-1), size);
					} else {
						word = new RenderedText(str, size);
					}
					if (highlighting) word.hardlight(0xFFFF44);
					else if (color != -1) word.hardlight(color);

					if (str.endsWith(UNDERSCORE) || str.endsWith(CROSS) || str.endsWith(MONEY) || str.endsWith(WAVES) || str.endsWith(GUTTER)
							|| str.endsWith(DEAD) || str.endsWith(AT) || str.endsWith(TRIANGLE) || str.endsWith(PI)
							|| str.endsWith(OMEGA) || str.endsWith(E) || str.endsWith(EURO) || str.endsWith(YEN)) highlighting = !highlighting;
				}
				word.scale.set(zoom);
				words.add(word);
				add(word);

				if (height < word.baseLine()) height = word.baseLine();

			}
		}
		layout();
	}

	public synchronized void zoom(float zoom){
		this.zoom = zoom;
		for (RenderedText word : words) {
			if (word != null) word.scale.set(zoom);
		}
	}

	public synchronized void hardlight(int color){
		this.color = color;
		for (RenderedText word : words) {
			if (word != null) word.hardlight( color );
		}
	}

	public synchronized void invert(){
		if (words != null) {
			for (RenderedText word : words) {
				if (word != null) {
					word.ra = 0.77f;
					word.ga = 0.73f;
					word.ba = 0.62f;
					word.rm = -0.77f;
					word.gm = -0.73f;
					word.bm = -0.62f;
				}
			}
		}
	}

	@Override
	protected synchronized void layout() {
		super.layout();
		float x = this.x;
		float y = this.y;
		float height = 0;
		nLines = 1;

		for (RenderedText word : words){
			if (word == null) {
				//newline
				y += height+0.5f;
				x = this.x;
				nLines++;
			} else {
				if (word.height() > height) height = word.baseLine();

				if ((x - this.x) + word.width() > maxWidth){
					y += height+0.5f;
					x = this.x;
					nLines++;
				}

				word.x = x;
				word.y = y;
				PixelScene.align(word);
				x += word.width();
				if (!chinese) x ++;
				else x--;

				if ((x - this.x) > width) width = (x - this.x);

			}
		}
		this.height = (y - this.y) + height+0.5f;
	}
}
