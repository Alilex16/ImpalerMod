package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RampingPullAction extends AbstractGameAction {
    private AbstractCard card;
    private int damageGain;

    public RampingPullAction(AbstractCard card, int damageGain) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.damageGain = damageGain;
        this.card = card;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.isDone = true;
        } else {
            AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
            if (card.type == AbstractCard.CardType.ATTACK) {
                AbstractDungeon.actionManager.addToBottom(new DamageGainOnAttackDrawAction(this.card, damageGain));
            }

            this.isDone = true;
        }
    }
}


