package impalermod.cards.curse;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.UndeadCursePower;

public class UndeadCurse extends AbstractImpalerCard {
	public static final String ID = "UndeadCurse";
	public static final	String NAME = "Undead";
	public static final	String IMG = "cards/curse/Undead.png";
	public static final	String DESCRIPTION = "Unplayable. NL Whenever you heal this turn, lose HP instead.";

	private static final CardRarity RARITY = CardRarity.CURSE;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.CURSE;

	private static final int POOL = 1;
	private static final int COST = -2;

	private static final int POWER = 1;

	public static boolean undeadCursed = false;

	public UndeadCurse() {
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
        undeadCursed = true;
		AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new UndeadCursePower(AbstractDungeon.player))); //(AbstractDungeon.player, 1), 1));

	}

	@Override
	public void triggerOnEndOfTurnForPlayingCard() {
		this.dontTriggerOnUseCard = true;
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem((AbstractCard)this, true));
	}

	public AbstractCard makeCopy() {
		return new UndeadCurse();
	}


	public void upgrade() {

	}
}
