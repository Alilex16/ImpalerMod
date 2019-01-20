package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class Prickle extends AbstractImpalerCard {
	public static final String ID = "Prickle";
	public static final	String NAME = "Prickle";
	public static final	String IMG = "cards/skill/placeholderskill.png";
	public static final	String DESCRIPTION = "Lose !M! HP. NL Gain [R]. NL Exhaust.";
	public static final	String DESCRIPTION_UPGRADED = "Lose !M! HP. NL Gain [R].";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0;

    private static int SELF_DMG = 2;
    private static final int ENERGY_AMOUNT = 1;

	public Prickle() {
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
			SELF_DMG = 4;
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(ENERGY_AMOUNT));
	}

	public AbstractCard makeCopy() {
		return new Prickle();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.exhaust = false;
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
}