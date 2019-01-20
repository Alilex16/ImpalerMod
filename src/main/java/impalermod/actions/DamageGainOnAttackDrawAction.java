package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class DamageGainOnAttackDrawAction extends AbstractGameAction {
    private AbstractCard card;

    public DamageGainOnAttackDrawAction(AbstractCard card, int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            this.card.baseDamage += this.amount;
            this.card.applyPowers();
        }
        this.tickDuration();

    }
}