/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.scrolls.cursed;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class CursedScroll extends Scroll {
	
	
	public static final LinkedHashMap<Class<?extends Scroll>, Class<?extends CursedScroll>> regToExo = new LinkedHashMap<>();
	public static final LinkedHashMap<Class<?extends CursedScroll>, Class<?extends Scroll>> exoToReg = new LinkedHashMap<>();
	static{
		regToExo.put(ScrollOfUpgrade.class, ScrollOfDowngrade.class);
		exoToReg.put(ScrollOfDowngrade.class, ScrollOfUpgrade.class);

		regToExo.put(ScrollOfIdentify.class, ScrollOfDisidentify.class);
		exoToReg.put(ScrollOfDisidentify.class, ScrollOfIdentify.class);
		
		regToExo.put(ScrollOfRemoveCurse.class, ScrollOfCurse.class);
		exoToReg.put(ScrollOfCurse.class, ScrollOfRemoveCurse.class);

		regToExo.put(ScrollOfMirrorImage.class, ScrollOfPhantomReflection.class);
		exoToReg.put(ScrollOfPhantomReflection.class, ScrollOfMirrorImage.class);

		regToExo.put(ScrollOfRecharging.class, ScrollOfDecharging.class);
		exoToReg.put(ScrollOfDecharging.class, ScrollOfRecharging.class);

		regToExo.put(ScrollOfTeleportation.class, ScrollOfMisstep.class);
		exoToReg.put(ScrollOfMisstep.class, ScrollOfTeleportation.class);

		regToExo.put(ScrollOfLullaby.class, ScrollOfNightmare.class);
		exoToReg.put(ScrollOfNightmare.class, ScrollOfLullaby.class);

		regToExo.put(ScrollOfMagicMapping.class, ScrollOfForgetfullness.class);
		exoToReg.put(ScrollOfForgetfullness.class, ScrollOfMagicMapping.class);

		regToExo.put(ScrollOfRage.class, ScrollOfMadness.class);
		exoToReg.put(ScrollOfMadness.class, ScrollOfRage.class);

		regToExo.put(ScrollOfRetribution.class, ScrollOfBalance.class);
		exoToReg.put(ScrollOfBalance.class, ScrollOfRetribution.class);
		
		regToExo.put(ScrollOfTerror.class, ScrollOfHorror.class);
		exoToReg.put(ScrollOfHorror.class, ScrollOfTerror.class);
		
		regToExo.put(ScrollOfTransmutation.class, ScrollOfDegradation.class);
		exoToReg.put(ScrollOfDegradation.class, ScrollOfTransmutation.class);
	}
	
	@Override
	public boolean isKnown() {
		return anonymous || (handler != null && handler.isKnown( exoToReg.get(this.getClass()) ));
	}
	
	@Override
	public void setKnown() {
		if (!isKnown()) {
			handler.know(exoToReg.get(this.getClass()));
			updateQuickslot();
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		if (handler != null && handler.contains(exoToReg.get(this.getClass()))) {
			image = handler.image(exoToReg.get(this.getClass())) + 16;
			rune = handler.label(exoToReg.get(this.getClass()));
		}
	}
	
	@Override
	//30 gold less than its none-exotic equivalent
	public int value() {
		return (Reflection.newInstance(exoToReg.get(getClass())).value() - 30) * quantity;
	}

	@Override
	//2 less energy than its none-exotic equivalent
	public int energyVal() {
		return (Reflection.newInstance(exoToReg.get(getClass())).energyVal() - 2) * quantity;
	}

}
