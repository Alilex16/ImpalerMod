package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BloodBrewingPower;
import impalermod.relics.BleedingEnergy;

public class BloodBrewing extends AbstractImpalerCard {
	public static final String ID = "BloodBrewing";
	public static final	String NAME = "Blood Brewing";
	public static final	String IMG = "cards/skill/BloodBrewing.png";
	public static final	String DESCRIPTION = "Lose !M! HP. NL Obtain a random Potion next turn. NL Exhaust.";
	public static final	String DESCRIPTION_UPGRADED = "Lose !M! HP. NL Obtain a random Potion next turn.";

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0;

	private static int SELF_DMG = 10;
	private static final int UPGRADE_SELF_DMG = -4;
	private static final int POTION_AMOUNT = 1;

	public BloodBrewing() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.CheckBleedingEnergy();
		this.baseMagicNumber = SELF_DMG;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
            SELF_DMG = 20;

		    if (upgraded)
            {
                SELF_DMG = 12;
            }
		}

        this.initializeDescription();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BloodBrewingPower(p, POTION_AMOUNT), POTION_AMOUNT));
	}

	public AbstractCard makeCopy() {
		return new BloodBrewing();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_SELF_DMG);
			if (BleedingEnergy.bleedingEnergyEquipped)
			{
				this.upgradeMagicNumber(UPGRADE_SELF_DMG);
			}
			this.exhaust = false;
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
	
}
