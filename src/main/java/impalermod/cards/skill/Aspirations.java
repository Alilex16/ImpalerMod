package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.SpiritPower;

public class Aspirations extends AbstractImpalerCard {
	public static final String ID = "Aspirations";
	public static final	String NAME = "Aspirations";
	public static final	String IMG = "cards/skill/Aspirations.png";
	public static final	String DESCRIPTION = "Gain 1 Vulnerable. Apply 4 Weak. NL Gain !M! Spirit. NL Exhaust.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static final int POWER = 1;
	private static final int UPGRADE_POWER = 1;
	private static final int VULNERABLE_AMOUNT = 1;
	private static final int WEAK_AMOUNT = 4;

	public Aspirations() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = POWER;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, VULNERABLE_AMOUNT, false), VULNERABLE_AMOUNT));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new WeakPower(m, WEAK_AMOUNT, false), WEAK_AMOUNT));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpiritPower(p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Aspirations();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_POWER);
		}
	}
	
}
