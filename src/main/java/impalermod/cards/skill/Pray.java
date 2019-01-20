package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;

public class Pray extends AbstractImpalerCard {
	public static final String ID = "Pray";
	public static final	String NAME = "Pray";
	public static final	String IMG = "cards/skill/Pray.png";
	public static final	String DESCRIPTION = "Unplayable. NL When discarded from your hand, gain !M! HP. NL When Exhausted, shuffle a random Curse in your discard pile.";
	public static final	String[] DESCRIPTION_EXTENDED = new String[] {"I can't ~pray~ like this.", "Are you not listening to my prayers ?!"};

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = -2;

	private static final int HEAL_AMOUNT = 5;
	private static final int UPGRADE_HEAL = 3;

	public Pray() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = HEAL_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true; // will this exhaust the card when discarded?? TEST!!
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
	}

	public void triggerOnExhaust()
	{
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse(),1));
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		this.cantUseMessage = DESCRIPTION_EXTENDED[0];
		return false;
	}

	public void triggerOnManualDiscard()
	{
		AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
	}


    public AbstractCard makeCopy() {
		return new Pray();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_HEAL);
            this.initializeDescription();
		}
	}
	
}
