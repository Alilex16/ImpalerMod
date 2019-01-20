package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class LivingOffScrapsFollowUpAction extends AbstractGameAction {
    private AbstractPlayer player;

	public LivingOffScrapsFollowUpAction(AbstractPlayer player, int amount) {
        {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
	    this.amount = amount;
		this.player = player;
		//this.actionType = ActionType.CARD_MANIPULATION;
	}

	@Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            Iterator var1 = LivingOffScrapsAction.scrapsCards.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if ((c.rawDescription.contains("Lose") || c.rawDescription.contains("lose")) && (c.rawDescription.contains("HP")))
                {
                    AbstractDungeon.actionManager.addToBottom(new HealAction(this.player, this.player, amount)); // if bloody, don't discard, gain !M! HP per card kept
                }
                else
                {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                }
            }

            LivingOffScrapsAction.scrapsCards.clear();
        }

        this.tickDuration();
    }
}