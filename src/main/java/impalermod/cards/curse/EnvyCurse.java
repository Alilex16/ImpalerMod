package impalermod.cards.curse;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;

public class EnvyCurse extends AbstractImpalerCard {
	public static final String ID = "EnvyCurse";
	public static final	String NAME = "Envy";
	public static final	String IMG = "cards/curse/CurseBase.png";
	public static final	String DESCRIPTION = "Unplayable. NL When exhausted, exhaust a random card in your hand.";

	private static final CardRarity RARITY = CardRarity.CURSE;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.CURSE;

	private static final int POOL = 1;
	private static final int COST = -2;

	private static final int POWER = 1;

	public EnvyCurse() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.magicNumber = this.baseMagicNumber = POWER;
		this.color = CardColor.CURSE;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!dontTriggerOnUseCard && p.hasRelic("Blue Candle")) {
			useBlueCandle(p);
		}
	}

	@Override
	public void triggerWhenDrawn() {
		AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
	}

	@Override
	public void triggerOnEndOfTurnForPlayingCard() {
		this.dontTriggerOnUseCard = true;
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem((AbstractCard)this, true));
		//AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
	}

	public void triggerOnExhaust()
	{
		AbstractDungeon.actionManager.addToBottom(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, POWER, true));
	}

	public AbstractCard makeCopy() {
		return new EnvyCurse();
	}


	public void upgrade() {

	}
}
