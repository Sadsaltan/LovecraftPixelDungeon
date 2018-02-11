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

import com.lovecraftpixel.lovecraftpixeldungeon.Chrome;
import com.lovecraftpixel.lovecraftpixeldungeon.LovecraftPixelDungeon;
import com.lovecraftpixel.lovecraftpixeldungeon.actors.mobs.npcs.Gardner;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Generator;
import com.lovecraftpixel.lovecraftpixeldungeon.items.Item;
import com.lovecraftpixel.lovecraftpixeldungeon.items.quest.ElderSign;
import com.lovecraftpixel.lovecraftpixeldungeon.items.quest.YellowSign;
import com.lovecraftpixel.lovecraftpixeldungeon.items.tools.Scissors;
import com.lovecraftpixel.lovecraftpixeldungeon.items.tools.Tincturebottle;
import com.lovecraftpixel.lovecraftpixeldungeon.items.weapon.guns.Pistol;
import com.lovecraftpixel.lovecraftpixeldungeon.messages.Messages;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.GardnerSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.ItemSpriteSheet;
import com.lovecraftpixel.lovecraftpixeldungeon.sprites.LivingFirebloomPlantSprite;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.Archs;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.ExitButton;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.Icons;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.RenderedTextMultiline;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.ScrollPane;
import com.lovecraftpixel.lovecraftpixeldungeon.ui.Window;
import com.lovecraftpixel.lovecraftpixeldungeon.utils.BookTitles;
import com.lovecraftpixel.lovecraftpixeldungeon.windows.WndTitledMessage;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

//TODO: update this class with relevant info as new versions come out.
public class ChangesScene extends PixelScene {

	private final ArrayList<ChangeInfo> infos = new ArrayList<>();

	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2 ;
		title.y = 4;
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 16;

		panel.size( pw, ph );
		panel.x = (w - pw) / 2f;
		panel.y = title.y + title.height();
		align( panel );
		add( panel );

		ScrollPane list = new ScrollPane( new Component() ){

			@Override
			public void onClick(float x, float y) {
				for (ChangeInfo info : infos){
					if (info.onClick( x, y )){
						return;
					}
				}
			}

		};
		add( list );
		
		//**********************
		//       vI.I.I
 		//**********************

		ChangeInfo changes = new ChangeInfo("vI.I.I", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		infos.add(changes);
		
		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.BOOK_GOLD, null), "Custom Names",
				"You now have to give your hero a name before you start a new game, it can also be a random generated name."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.BOOK_PURPLE, null), "Mental Health",
				"Your Mental Health is very important. It can be a hard task keeping your sanity in the dungeons..."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.BOOK_BLUE, null), "Knowledge",
				"Discovering Secrets raises your knowledge. Knowledge can be used in multiple ways."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.BOOK_RED, null), "Flavour Text",
				"All kinds of items have now colorful quotes randomly assigned to them."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.BOOK_BROWN, null), "Grimoire",
				"You now can fill a book about all the creatures you encountered. It works just like a Pokedex."));

		changes.addButton( new ChangeButton( Icons.get(Icons.MASTERY), "Book Titles",
				"Clicking on bookshelfs now gives you a random flavour booktitle just like this one: \n\n" + BookTitles.getRandomTitle()));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.BOOK_GREEN, null), "Wells",
				"Wells grant a mental health and knowledge boost."));

		changes = new ChangeInfo("Mobs", false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton(new LivingFirebloomPlantSprite(), "Living Plants", "Living Plants look like normal plants. If you step on a Living Plant, it wakes up and attacks everything around it. When slain, they drop their respective seed and have some other nasty or helpful effects."));

		changes.addButton( new ChangeButton(new GardnerSprite(), new Gardner().name, new Gardner().description()));

		changes = new ChangeInfo("Items", false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton( new ItemSprite(Generator.random(Generator.Category.WEAPON).image, null), "Poisoned Weapon",
				"You now have the ability to poison your weapons with seeds."));

		changes.addButton( new ChangeButton( Icons.get(Icons.FOODBAG), "Food Bag",
				"The Food Bag stores your food, simple as that."));

		changes.addButton( new ChangeButton( new ItemSprite(new Scissors().image, null), "Scissors",
				"Scissors let you cut plants and store them in your inventory."));

		changes.addButton( new ChangeButton( new ItemSprite(new Tincturebottle().image, null), "Tincture Bottle",
				"Can be used to combine seeds and cut plants into Herbs."));

		changes.addButton( new ChangeButton( new ItemSprite(Generator.random(Generator.Category.PLANT).image, null), "Cut Plants",
				"Plants can be cut and used to make Herbs."));

		changes.addButton( new ChangeButton( new ItemSprite(Generator.random(Generator.Category.HERBS).image, null), "Herbs",
				"Herbs are brewed with seeds and cut plants."));

		changes.addButton( new ChangeButton( new ItemSprite(new YellowSign().image, null), new YellowSign().name(),
				"Opens a portal to Aldebaran."));

		changes.addButton( new ChangeButton( new ItemSprite(new ElderSign().image, null), new ElderSign().name(),
				"Opens a portal to Neu Schwabenland."));

		changes.addButton( new ChangeButton( new ItemSprite(new Pistol().image, null), "Guns",
				"You can now also find guns in this Guneon."));

		changes = new ChangeInfo("Blobs", false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton( Icons.get(Icons.BUSY), "Sunlight",
				"Sunlight lets grass and flowers grow in the dungeon."));

		changes.addButton( new ChangeButton( Icons.get(Icons.BUSY), "Storm",
				"Storms flood the dungeon and lets lightning strikes."));

		changes.addButton( new ChangeButton( Icons.get(Icons.BUSY), "Spores",
				"Spores reduce your mental health."));

		Component content = list.content();
		content.clear();

		float posY = 0;
		float nextPosY = 0;
		boolean second =false;
		for (ChangeInfo info : infos){
			if (info.major) {
				posY = nextPosY;
				second = false;
				info.setRect(0, posY, panel.innerWidth(), 0);
				content.add(info);
				posY = nextPosY = info.bottom();
			} else {
				if (!second){
					second = true;
					info.setRect(0, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = info.bottom();
				} else {
					second = false;
					info.setRect(panel.innerWidth()/2f, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = Math.max(info.bottom(), nextPosY);
					posY = nextPosY;
				}
			}
		}


		content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop() - 1,
				panel.innerWidth(),
				panel.innerHeight() + 2);
		list.scrollTo(0, 0);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}

	@Override
	protected void onBackPressed() {
		LovecraftPixelDungeon.switchNoFade(TitleScene.class);
	}

	private static class ChangeInfo extends Component {

		protected ColorBlock line;

		private RenderedText title;
		private boolean major;

		private RenderedTextMultiline text;

		private ArrayList<ChangeButton> buttons = new ArrayList<>();

		public ChangeInfo( String title, boolean majorTitle, String text){
			super();
			
			if (majorTitle){
				this.title = PixelScene.renderText( title, 9 );
				line = new ColorBlock( 1, 1, 0xFF222222);
				add(line);
			} else {
				this.title = PixelScene.renderText( title, 6 );
				line = new ColorBlock( 1, 1, 0xFF333333);
				add(line);
			}
			major = majorTitle;

			add(this.title);

			if (text != null && !text.equals("")){
				this.text = PixelScene.renderMultiline(text, 6);
				add(this.text);
			}

		}

		public void hardlight( int color ){
			title.hardlight( color );
		}

		public void addButton( ChangeButton button ){
			buttons.add(button);
			add(button);

			button.setSize(16, 16);
			layout();
		}

		public boolean onClick( float x, float y ){
			for( ChangeButton button : buttons){
				if (button.inside(x, y)){
					button.onClick();
					return true;
				}
			}
			return false;
		}

		@Override
		protected void layout() {
			float posY = this.y + 2;
			if (major) posY += 2;

			title.x = x + (width - title.width()) / 2f;
			title.y = posY;
			PixelScene.align( title );
			posY += title.baseLine() + 2;

			if (text != null) {
				text.maxWidth((int) width());
				text.setPos(x, posY);
				posY += text.height();
			}

			float posX = x;
			float tallest = 0;
			for (ChangeButton change : buttons){

				if (posX + change.width() >= right()){
					posX = x;
					posY += tallest;
					tallest = 0;
				}

				//centers
				if (posX == x){
					float offset = width;
					for (ChangeButton b : buttons){
						offset -= b.width();
						if (offset <= 0){
							offset += b.width();
							break;
						}
					}
					posX += offset / 2f;
				}

				change.setPos(posX, posY);
				posX += change.width();
				if (tallest < change.height()){
					tallest = change.height();
				}
			}
			posY += tallest + 2;

			height = posY - this.y;
			
			if (major) {
				line.size(width(), 1);
				line.x = x;
				line.y = y+2;
			} else if (x == 0){
				line.size(1, height());
				line.x = width;
				line.y = y;
			} else {
				line.size(1, height());
				line.x = x;
				line.y = y;
			}
		}
	}

	//not actually a button, but functions as one.
	private static class ChangeButton extends Component {

		protected Image icon;
		protected String title;
		protected String message;

		public ChangeButton( Image icon, String title, String message){
			super();
			
			this.icon = icon;
			add(this.icon);

			this.title = Messages.titleCase(title);
			this.message = message;

			layout();
		}

		public ChangeButton( Item item, String message ){
			this( new ItemSprite(item), item.name(), message);
		}

		protected void onClick() {
			LovecraftPixelDungeon.scene().add(new ChangesWindow(new Image(icon), title, message));
		}

		@Override
		protected void layout() {
			super.layout();

			icon.x = x + (width - icon.width) / 2f;
			icon.y = y + (height - icon.height) / 2f;
			PixelScene.align(icon);
		}
	}
	
	private static class ChangesWindow extends WndTitledMessage {
	
		public ChangesWindow( Image icon, String title, String message ) {
			super( icon, title, message);
			
			add( new TouchArea( chrome ) {
				@Override
				protected void onClick( Touchscreen.Touch touch ) {
					hide();
				}
			} );
			
		}
		
	}
}
