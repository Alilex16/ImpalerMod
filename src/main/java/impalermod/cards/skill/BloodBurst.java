package impalermod.cards.skill;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.BloodBurstAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class BloodBurst extends AbstractImpalerCard {
	public static final String ID = "BloodBurst";
	public static final	String NAME = "Blood Burst";
	public static final	String IMG = "cards/skill/BloodBurst.png";
	public static final	String DESCRIPTION = "Discard any number of cards in your hand. NL Lose 1 HP and apply !M! Bleeding for each discarded card. NL Exhaust.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Discard any number of cards in your hand. NL Lose 2 HP and apply !M! Bleeding for each discarded card. NL Exhaust.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static int SELF_DMG = 1;
	private static final int BLEEDING_AMOUNT = 1;
	private static final int UPGRADE_BLEEDING_AMOUNT = 1;

	public BloodBurst() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = BLEEDING_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
		this.CheckBleedingEnergy();
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 2;
			this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
			this.initializeDescription();
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

		if (AbstractDungeon.player.hasRelic("LizardSkull"))
		{
			AbstractDungeon.player.getRelic("LizardSkull").flash();
			++this.magicNumber;
		}

		AbstractDungeon.actionManager.addToBottom(new BloodBurstAction(m, this.magicNumber, SELF_DMG));
	}

	public AbstractCard makeCopy() {
		return new BloodBurst();
	}

	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_BLEEDING_AMOUNT);
		}
	}
}
