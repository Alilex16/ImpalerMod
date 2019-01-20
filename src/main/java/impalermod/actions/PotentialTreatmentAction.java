package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;


public class PotentialTreatmentAction extends AbstractGameAction {
    private AbstractCard card;

    public PotentialTreatmentAction(AbstractCard card, int amount) {

        this.duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            this.card.magicNumber += this.amount; // this.card.baseMagicNumber
            this.card.applyPowers();
        }
        this.tickDuration();
    }
}