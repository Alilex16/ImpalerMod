package impalermod.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.deprecated.GreyBloodPower;

public class GreyBlood extends AbstractImpalerCard {
	public static final String ID = "GreyBlood";
	public static final	String NAME = "Grey Blood";
	public static final	String IMG = "cards/attack/placeholderattack.png";
	public static final	String DESCRIPTION = "This turn, instead of Heal, gain Block + !M!. Instead of Block, gain Heal + !M!. NL Ethereal.";
    //public static final	String DESCRIPTION = "This turn, Heal and Block has been reversed. NL Gain !M! extra Block and Heal. NL Ethereal.";
	public static final	String DESCRIPTION_UPGRADED = "This turn, instead of Heal, gain Block + !M!. Instead of Block, gain Heal + !M!.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0;

	private static final int POWER = 2;
    private static final int UPGRADED_POWER = 1;

	public GreyBlood() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = POWER;
		this.magicNumber = this.baseMagicNumber;
		this.isEthereal = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GreyBloodPower(p, 1), 1));
	}

	public AbstractCard makeCopy() {
		return new GreyBlood();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADED_POWER);
			this.isEthereal = false;
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
}