package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.BonedActuationExhaustAction;
import impalermod.cards.AbstractImpalerCard;

public class BonedActuation extends AbstractImpalerCard {
	public static final String ID = "BonedActuation";
	public static final	String NAME = "Boned Actuation";
	public static final	String IMG = "cards/skill/BonedActuation.png";
	public static final	String DESCRIPTION = "Ethereal. NL Exhaust a card. If Exhausted card is a Bloody card, this card is no longer Ethereal. NL Heal !M! HP twice. ";
	public static final	String DESCRIPTION_BLOODY = "Exhaust a card. NL Heal !M! HP twice.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int HEAL_AMOUNT = 3;
	private static final int UPGRADE_HEAL_AMOUNT = 2;

	public BonedActuation() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = HEAL_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.isEthereal = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new BonedActuationExhaustAction(p, p, 1, false, this, false, false));
		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new BonedActuation();
	}

	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_HEAL_AMOUNT);
		}
	}
}
