package impalermod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class MakeTempCardInHandUpgradedAction extends AbstractGameAction {
    private AbstractCard cardToMake;
    private boolean upgrade;

    public MakeTempCardInHandUpgradedAction(AbstractCard card, int amount, boolean upgraded) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
        this.cardToMake = card;
        this.upgrade = upgraded;
}

    public void update() {
        if (this.duration == 0.5F) {
            AbstractCard c;
            int i;
            if (this.amount < 6) {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    if(this.upgrade){
                        c.upgrade();
                    }
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                }
            } else {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    if(this.upgrade){
                        c.upgrade();
                    }
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
                }
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
