package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;

public class Suck extends AbstractImpalerCard {
	public static final String ID = "Suck";
	public static final	String NAME = "Suck";
	public static final	String IMG = "cards/skill/placeholderskill.png";
	public static final	String DESCRIPTION = "Heal !M! HP. NL When played, permanently increase this card's heal by 1. NL Exhaust.";
	public static final	String DESCRIPTION_UPGRADED = "Heal !M! HP. NL When played, permanently increase this card's heal by 2. NL Exhaust.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static final int HEAL_AMOUNT = 1;

	private static int HEAL_BASE;
	private static final int HEAL_GROWTH = 1;
	private static final int UPGRADE_HEAL_GROWTH = 1;


	public Suck() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		HEAL_BASE = HEAL_GROWTH;
		this.misc = HEAL_AMOUNT;
		this.baseMagicNumber = this.misc;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;

	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new IncreaseMiscAction(this.uuid, this.misc, HEAL_BASE));
		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
	}

	public void applyPowers() {
		this.baseMagicNumber = this.misc;
		this.magicNumber = this.baseMagicNumber;
		super.applyPowers();
		this.initializeDescription();
	}


	public AbstractCard makeCopy() {
		return new Suck();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.name = "Suck Hard";
			HEAL_BASE = HEAL_GROWTH + UPGRADE_HEAL_GROWTH;
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
	
}
