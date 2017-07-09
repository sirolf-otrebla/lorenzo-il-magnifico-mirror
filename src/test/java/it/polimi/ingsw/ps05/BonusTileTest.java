package it.polimi.ingsw.ps05;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.ps05.model.BonusTile;
import it.polimi.ingsw.ps05.model.BonusTileType;
import it.polimi.ingsw.ps05.model.ColorEnumeration;
import it.polimi.ingsw.ps05.model.Player;
import it.polimi.ingsw.ps05.model.effects.Effect;
import it.polimi.ingsw.ps05.model.effects.ImmediateEffect;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.ActionResult;
import it.polimi.ingsw.ps05.model.resourcesandbonuses.GoldResource;

public class BonusTileTest {

	@Test
	public void constructorTest() {
		ArrayList<Effect> effectList = new ArrayList<>();
		ImmediateEffect e = new ImmediateEffect();
		ArrayList<ActionResult> list = new ArrayList<>();
		list.add(new GoldResource(5));
		e.setEffectList(list);
		effectList.add(e);
		Player p = new Player(0, "marco", ColorEnumeration.Black);
		
		BonusTile tile = new BonusTile(effectList, BonusTileType.Default);
		assertEquals(effectList,tile.getEffectList());
		assertEquals(BonusTileType.Default,tile.getType());
		tile.setRelatedPlayer(p);
		assertEquals(p,tile.getRelatedPlayer());
	}

}
