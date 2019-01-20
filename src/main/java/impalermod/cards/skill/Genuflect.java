package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.LoseSpiritPower;
import impalermod.powers.SpiritPower;

public class Genuflect extends AbstractImpalerCard {
	public static final String ID = "Genuflect";
	public static final	String NAME = "Genuflect";
	public static final	String IMG = "cards/skill/placeholderskill.png";
	public static final	String DESCRIPTION = "Gain !M! Spirit, at the end of your turn, lose !M! Spirit.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;

	private static final int COST = 0;
	private static final int STR_AMT = 2;
	private static final int UPGRADE_STR = 2;

	public Genuflect() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = STR_AMT;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpiritPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseSpiritPower(p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Genuflect();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_STR);
		}
	}
	
}
