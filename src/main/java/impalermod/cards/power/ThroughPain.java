package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.ThroughPainPower;
import impalermod.relics.BleedingEnergy;

public class ThroughPain extends AbstractImpalerCard {
	public static final String ID = "ThroughPain";
	public static final	String NAME = "Through Pain";
	public static final	String IMG = "cards/power/ThroughPain.png";
	public static final	String DESCRIPTION = "Lose 4 HP. NL Start every turn with !M! Artifact.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 8 HP. NL Start every turn with !M! Artifact.";

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;

	private static final int POOL = 1;
	private static final int COST = 2;
	private static final int UPGRADE_COSTS = 1;

	private static int SELF_DMG = 4;
	private static final int POWER = 1;

	public ThroughPain() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = this.magicNumber = POWER;
		this.CheckBleedingEnergy();
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 8;
			this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
			this.initializeDescription();
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));
		AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1",0.3f));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThroughPainPower(p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new ThroughPain();
	}


	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			this.upgradeBaseCost(UPGRADE_COSTS);
		}
	}
}
