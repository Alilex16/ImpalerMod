package impalermod.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ExhaustStatusCardInHandAction extends AbstractGameAction {
    private AbstractPlayer player;

	public ExhaustStatusCardInHandAction(AbstractPlayer player) {
        {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
		this.player = player;
		//this.actionType = ActionType.CARD_MANIPULATION;
	}

	@Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {

            Iterator var5 = this.player.hand.group.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();
                if (c.type == AbstractCard.CardType.STATUS) {
                    this.player.hand.moveToExhaustPile(c);
                }
            }
        }

        this.tickDuration();
    }
}