package impalermod.cards.curse;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import impalermod.cards.AbstractImpalerCard;

public class HeathenCurse extends AbstractImpalerCard {
	public static final String ID = "HeathenCurse";
	public static final	String NAME = "Heathen";
	public static final	String IMG = "cards/curse/CurseBase.png";
	public static final	String DESCRIPTION = "Unplayable. NL When drawn, gain !M! Vulnerable. NL When Exhausted, lose !M! Dexterity.";

	private static final CardRarity RARITY = CardRarity.CURSE;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.CURSE;

	private static final int POOL = 1;
	private static final int COST = -2;

	private static final int POWER = 1;

	public HeathenCurse() {
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
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, this.magicNumber, false), 1, true, AbstractGameAction.AttackEffect.NONE));

	}

	@Override
	public void triggerOnEndOfTurnForPlayingCard() {
		this.dontTriggerOnUseCard = true;
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem((AbstractCard)this, true));
		//AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
	}

	public void triggerOnExhaust()
	{
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -this.magicNumber), -this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new HeathenCurse();
	}


	public void upgrade() {

	}
}
