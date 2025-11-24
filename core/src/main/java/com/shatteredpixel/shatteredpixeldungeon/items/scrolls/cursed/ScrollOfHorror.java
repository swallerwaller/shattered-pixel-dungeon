package com.shatteredpixel.shatteredpixeldungeon.items.scrolls.cursed;

import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ScrollOfHorror extends CursedScroll {

    {
        icon = ItemSpriteSheet.Icons.SCROLL_HORROR;
    }

    @Override
    public void doRead() {

        detach(curUser.belongings.backpack);
        new Flare( 5, 32 ).color( 0x00FF00, true ).show( curUser.sprite, 2f );

        identify();

        readAnimation();
    }

}
