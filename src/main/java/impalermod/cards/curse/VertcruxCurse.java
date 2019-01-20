package impalermod.cards.curse;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.SpiritPower;

public class VertcruxCurse extends AbstractImpalerCard {
	public static final String ID = "VertcruxCurse";
	public static final	String NAME = "Curse of Vertcrux";
	public static final	String IMG = "cards/curse/CurseBase.png";
	public static final	String DESCRIPTION = "Unplayable. NL When Exhausted, lose !M! Spirit.";

	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.CURSE;

	private static final int POOL = 0; // 2
	private static final int COST = -2;

	private static final int POWER = 2;

	public VertcruxCurse() {
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
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SpiritPower(AbstractDungeon.player, -this.magicNumber), -this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new VertcruxCurse();
	}


	public void upgrade() {

	}
}
