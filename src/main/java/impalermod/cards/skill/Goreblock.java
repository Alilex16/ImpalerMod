package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class Goreblock extends AbstractImpalerCard {
	public static final String ID = "Goreblock";
	public static final	String NAME = "Goreblock";
	public static final	String IMG = "cards/skill/Goreblock.png";
	public static final	String DESCRIPTION = "Lose 2 HP. NL Apply !M! Vulnerable. NL Gain !B! Block.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 4 HP. NL Apply !M! Vulnerable. NL Gain !B! Block.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static int SELF_DMG = 2;
	private static final int POWER = 13;
	private static final int UPGRADE_POWER = 3;
	private static final int VULNERABLE_AMOUNT = 2;
	private static final int UPGRADE_VULNERABLE_AMOUNT = 1;

	public Goreblock() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseBlock = POWER;
		this.baseMagicNumber = VULNERABLE_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.CheckBleedingEnergy();
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 4;
			this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
			this.initializeDescription();
		}
	}


	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
	}

	public AbstractCard makeCopy() {
		return new Goreblock();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_VULNERABLE_AMOUNT);
		}
	}
	
}
