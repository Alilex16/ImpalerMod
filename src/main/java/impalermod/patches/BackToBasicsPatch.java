package impalermod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import impalermod.cards.skill.Defend_Impaler;
import impalermod.cards.attack.Bite_Impaler;

import java.util.Iterator;



@SpirePatch(cls="com.megacrit.cardcrawl.events.city.BackToBasics", method="upgradeStrikeAndDefends")
public class BackToBasicsPatch {

	//public static final String ID = "impaler:BackToBasicsExtra";
	//private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
	//public static final String[] OPTIONS = eventStrings.OPTIONS;
	//private final String[] NewOption = new String[] {"[Simplicity] #gUpgrade #gall #gStrikes #gand #gDefends."} ;


	//@SpirePatch(clz = BackToBasics.class, method = "upgradeStrikeAndDefends")

	//@SpirePatch(clz = Duplicator.class, method = SpirePatch.CONSTRUCTOR)


	public static class Constructor {
		public static void Postfix(BackToBasics self) {
			// insert new choice at position 1

            //self.imageEventText.updateDialogOption(1, NewOption[0]);

			//self.imageEventText.updateDialogOption(1, OPTIONS[0]);
			//self.imageEventText.setDialogOption(BackToBasics.OPTIONS[1]);
		}
	}

	public static void Prefix (BackToBasics __instance) { //  replace

		//Iterator var1 = (Iterator) ReflectionHacks.getPrivate(__instance, BackToBasics.class, "var1");

        String NewOption = "[Simplicity] #gUpgrade #gall #gBites #gand #gDefends.";

        __instance.imageEventText.updateDialogOption((1),NewOption);
        __instance.imageEventText.setDialogOption(NewOption);


        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

		while(true) {
			AbstractCard c;
			do {
				if (!var1.hasNext()) {
					return;
				}

				c = (AbstractCard)var1.next();
			//} while(!(c instanceof Defend_Impaler) && !(c instanceof Bite_Impaler) && !(c instanceof Strike_Red) && !(c instanceof Defend_Red) && !(c instanceof Strike_Green) && !(c instanceof Defend_Green) && !(c instanceof Strike_Blue) && !(c instanceof Defend_Blue));
            } while(!(c instanceof Defend_Impaler) && !(c instanceof Bite_Impaler));

			if (c.canUpgrade()) {
				c.upgrade();
				AbstractDungeon.player.bottledCardUpgradeCheck(c);
				AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT));
			}
		}
	}
}
