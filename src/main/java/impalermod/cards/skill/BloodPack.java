package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;

public class BloodPack extends AbstractImpalerCard {
	public static final String ID = "BloodPack";
	public static final	String NAME = "Blood Pack";
	public static final	String IMG = "cards/skill/placeholderskill.png";
	public static final	String DESCRIPTION = "Ethereal. NL Heal !M! HP. NL Exhaust.";

	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 0;
	private static final int COST = 0;

	private static final int HEAL_AMT = 1;
	private static final int UPGRADE_HEAL = 1;

	public BloodPack() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = HEAL_AMT;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
		this.isEthereal = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new BloodPack();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_HEAL);
		}
	}
	
}
