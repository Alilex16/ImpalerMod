package impalermod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class MakeTempCardInDrawPileUpgradedAction extends AbstractGameAction {
    private AbstractCard cardToMake;
    private boolean randomSpot;
    private boolean cardOffset;
    private boolean upgrade;

    public MakeTempCardInDrawPileUpgradedAction(AbstractCard card, int amount, boolean upgraded, boolean randomSpot, boolean cardOffset) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
        this.cardToMake = card;
        this.randomSpot = randomSpot;
        this.cardOffset = cardOffset;
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
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, this.randomSpot, this.cardOffset));
                }
            } else {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    if(this.upgrade){
                        c.upgrade();
                    }
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, this.randomSpot, false));
                }
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
