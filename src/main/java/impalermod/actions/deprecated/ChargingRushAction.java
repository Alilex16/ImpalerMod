package impalermod.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import impalermod.cards.AbstractImpalerCard;

public class ChargingRushAction extends AbstractGameAction {

	AbstractCard cardToModify;

	public ChargingRushAction(AbstractImpalerCard card, int amount) {
		this.setValues(target, source, amount);
		this.actionType = ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
		//this.amount = this.cardToModify.magicNumber;
		this.cardToModify.baseMagicNumber = amount;
	}

	@Override
	public void update() {

		if (target == null || target.isDying) {
			isDone = true;
			return;
		}

		if (this.cardToModify.baseMagicNumber == 0)
			return;

		if (this.cardToModify.baseMagicNumber >= 2)
		{
			this.cardToModify.baseMagicNumber -= 2;
		}
		else
		{
			this.cardToModify.baseMagicNumber -= 1;
		}


		//target.loseBlock(amount);
		// amount = this.cardToModify.magicNumber;

        isDone = true;
	}

}