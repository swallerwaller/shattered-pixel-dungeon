package com.shatteredpixel.shatteredpixeldungeon.items.scrolls.cursed;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.TormentedSpirit;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class ScrollOfDowngrade extends CursedScroll {

    {
        icon = ItemSpriteSheet.Icons.SCROLL_DOWNGRADE;
        preferredBag = Belongings.Backpack.class;
        talentFactor = 2f;
    }

    @Override
    protected boolean usableOnItem(Item item) {
        return ((item instanceof EquipableItem && item.isUpgradable()) || item instanceof Wand || item instanceof SpiritBow || item instanceof Scroll); //this checks if the item can accept upgrades and thus downgrades
    //detects if item can be cursed; if it is an equippable/upgradable item, a wand, a spirit bow, or a regular scroll
    }
    public void doRead() {

        TormentedSpirit spirit = null;
        for (int i : PathFinder.NEIGHBOURS8){
            if (Actor.findChar(curUser.pos+i) instanceof TormentedSpirit){
                spirit = (TormentedSpirit) Actor.findChar(curUser.pos+i);
            }
        }
        if (spirit != null){
            identify();
            Sample.INSTANCE.play( Assets.Sounds.READ );
            readAnimation();

            new Flare( 6, 32 ).show( curUser.sprite, 2f );

            if (curUser.buff(Degrade.class) != null) {
                Degrade.detach(curUser, Degrade.class);
            }

            detach(curUser.belongings.backpack);
            GLog.p(Messages.get(this, "spirit"));
            spirit.cleanse();
        } else {
            super.doRead();
        }
    }

}

//@Override
//public ArrayList<String> actions(Hero hero ) {
//    ArrayList<String> actions = super.actions( hero );
//    actions.add( AC_CAST );
//    return actions;
//}
