package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class Bloodkinesis extends AbstractImpalerCard {
	public static final String ID = "Bloodkinesis";
	public static final	String NAME = "Bloodkinesis";
	public static final	String IMG = "cards/skill/Bloodkinesis.png";
	public static final	String DESCRIPTION = "Lose 5 HP. NL Gain [R] and draw !M! Card.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 10 HP. NL Gain [R] and draw !M! Card.";
	public static final	String DESCRIPTION_UPGRADED = "Lose 5 HP. NL Gain [R] and draw !M! Cards.";
	public static final	String DESCRIPTION_UPGRADED_BLEEDING_ENERGY = "Lose 10 HP. NL Gain [R] and draw !M! Cards.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0;

    private static int SELF_DMG = 5;
    private static final int ENERGY_AMT = 1;
	private static final int CARD_DRAW = 1;
	private static final int UPGRADE_CARD_DRAW = 1;

	public Bloodkinesis() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = CARD_DRAW;
		this.magicNumber = this.baseMagicNumber;
		this.CheckBleedingEnergy();
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 10;
			this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
			this.initializeDescription();
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(ENERGY_AMT));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Bloodkinesis();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_CARD_DRAW);
			this.rawDescription = DESCRIPTION_UPGRADED;
			if (BleedingEnergy.bleedingEnergyEquipped)
			{
				this.rawDescription = DESCRIPTION_UPGRADED_BLEEDING_ENERGY;
			}
			this.initializeDescription();
		}
	}
	
}
