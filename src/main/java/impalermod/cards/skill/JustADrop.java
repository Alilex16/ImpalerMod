package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BleedingPower;

public class JustADrop extends AbstractImpalerCard {
	public static final String ID = "JustADrop";
	public static final	String NAME = "Just A Drop";
	public static final	String IMG = "cards/skill/placeholderskill.png";
	public static final	String DESCRIPTION = "Apply !M! Bleeding.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0;

	private static final int BLEED_AMOUNT = 1;
	private static final int UPGRADE_BLEED_AMOUNT = 1;

	public JustADrop() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = BLEED_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

		if (AbstractDungeon.player.hasRelic("LizardSkull"))
		{
			AbstractDungeon.player.getRelic("LizardSkull").flash();
			++this.magicNumber;
		}

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new BleedingPower(m, this.magicNumber, true), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
	}

	public AbstractCard makeCopy() {
		return new JustADrop();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_BLEED_AMOUNT);
		}
	}
	
}
