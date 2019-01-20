package impalermod.patches;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import basemod.helpers.CardTags;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.events.shrines.Duplicator;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import impalermod.relics.CrimsonAmulet;
import java.lang.reflect.Field;
import java.util.Iterator;

//@SpirePatch(clz = Vampires.class, method = SpirePatch.CONSTRUCTOR)
//public class Vampires


/*
@SpirePatch(cls = "com.megacrit.cardcrawl.events.city.Vampires", method = "static")
public class VampiresPatchText
{

    public static void Replace(Vampires __instance) // does not work // insert
    {
        try

        {
            String NewOption = "[Simplicity] #gUpgrade #gall #gBites #gand #gDefends.";
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
*/



@SpirePatch(cls="com.megacrit.cardcrawl.events.city.Vampires", method="replaceAttacks")
public class VampiresPatch {


    @SpireInsertPatch( //@SpireInsertPatch(
            rloc = 0 // 8

    )


    // if press "ACCEPT" change what happens next .... + text

    public static void replace(Vampires __instance) // does not work // insert // replace // Override
    {
        try {
            AbstractDungeon.player.getRelic(CrimsonAmulet.ID);
        } catch (Exception e) {
            e.printStackTrace();
        }




        /*

        AbstractDungeon.player.getRelic(CrimsonAmulet.ID);


        String NewOption = "[Simplicity] #gUpgrade #gall #gBites #gand #gDefends.";

        __instance.imageEventText.updateDialogOption((0),NewOption);
        __instance.imageEventText.setDialogOption(NewOption);



        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();

        while(true) {
            while(i.hasNext()) {
                AbstractCard e = (AbstractCard)i.next();
                if (e instanceof CustomCard && ((CustomCard)e).isStrike()) {
                    e.baseDamage += 1;
                    //i.remove();
                } else if (CardTags.hasTag(e, BaseModCardTags.BASIC_STRIKE)) {
                    //i.remove();
                    e.baseDamage += 1;
                }
            }

            return;
        }

        */
    }
}

