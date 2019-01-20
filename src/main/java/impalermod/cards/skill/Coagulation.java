package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class Coagulation extends AbstractImpalerCard {
	public static final String ID = "Coagulation";
	public static final	String NAME = "Coagulation";
	public static final	String IMG = "cards/skill/Coagulation.png";
	public static final	String DESCRIPTION = "Lose !M! HP. NL Target loses all Bleeding and the same amount of Strength next turn. NL Exhaust."; // ( !M! )
	public static final	String DESCRIPTION_UPGRADED = "Lose !M! HP. NL Target loses all Bleeding and the same amount of Strength next turn.";

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static int SELF_DMG = 8;

	public Coagulation() {
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
			SELF_DMG = 16;
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));

		int bleedingCount = GetPowerCount(m, "Bleeding");
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Bleeding"));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new LoseStrengthPower(m, bleedingCount), bleedingCount));
	}

	private int GetPowerCount(AbstractCreature c, String powerId) {
		AbstractPower power =  c.getPower(powerId);
		return power != null ? power.amount : 0;
	}

	public AbstractCard makeCopy() {
		return new Coagulation();
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
