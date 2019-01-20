package impalermod.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.cards.AbstractImpalerCard;
import impalermod.cards.attack.Bite_Impaler;

import java.util.Iterator;

public class HeavyJawAction extends AbstractGameAction {
    //private AbstractCard card;
    private AbstractImpalerCard cardToModify;
    private int damageGain;
    private int healGain;

    public HeavyJawAction(int damageGain, int healGain) { // public HeavyJawAction(AbstractCard card, int damageGain, int healGain) {
        this.duration = Settings.ACTION_DUR_FAST; // 0.0F?
        this.actionType = ActionType.CARD_MANIPULATION;
        //this.cardToModify = card;
        this.damageGain = damageGain;
        this.healGain = healGain;
        //this.card = card;
    }


    public static boolean isBite(AbstractImpalerCard c) {
        return c instanceof Bite_Impaler;
    }


    public static void countCards() {


        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        AbstractImpalerCard c;
        while (var1.hasNext()) {
            c = (AbstractImpalerCard) var1.next();
            if (isBite(c)) {
                //
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractImpalerCard) var1.next();
            if (isBite(c)) {
                //++count;
            }
        }

        var1 = AbstractDungeon.player.discardPile.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractImpalerCard) var1.next();
            if (isBite(c)) {
                //++count;
            }
        }
    }


    public void update() {

        this.cardToModify.cardID = "Bite_Impaler";

        //this.card.cardID = "Bite_Impaler";

        if (isBite(cardToModify)) {
            this.cardToModify.baseDamage += damageGain;
            this.cardToModify.magicNumber += healGain;
            this.cardToModify.applyPowers();
        }

        this.isDone = true;
    }
}


