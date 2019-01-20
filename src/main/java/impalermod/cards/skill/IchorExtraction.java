package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class IchorExtraction extends AbstractImpalerCard {
	public static final String ID = "IchorExtraction";
	public static final	String NAME = "Ichor Extraction";
	public static final	String IMG = "cards/skill/placeholderskill.png";
	public static final	String DESCRIPTION = "Lose 2 HP. NL Draw !M! cards. NL Exhaust a card.";
    public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 4 HP. NL Draw !M! cards. NL Exhaust a card.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0;

	private static int SELF_DMG = 2;
	private static final int DRAW_AMOUNT = 2;
	private static final int UPGRADE_DRAW_AMOUNT = 1;

	public IchorExtraction() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = DRAW_AMOUNT;
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
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 1, false));
	}

	public AbstractCard makeCopy() {
		return new IchorExtraction();
	}

	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_DRAW_AMOUNT);
		}
	}
}
