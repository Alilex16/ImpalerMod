package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.PotentialTreatmentAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.LoseSpiritPower;
import impalermod.powers.SpiritPower;

public class PotentialTreatment extends AbstractImpalerCard {
	public static final String ID = "PotentialTreatment";
	public static final	String NAME = "Potential Treatment";
	public static final	String IMG = "cards/skill/PotentialTreatment.png";
	public static final	String DESCRIPTION = "Ethereal. NL When drawn, gain 1 Spirit for this turn. Heal !M! HP. NL Gain 1 additional HP this combat.";
	public static final	String DESCRIPTION_UPGRADED = "When drawn, gain 1 Spirit for this turn. Heal !M! HP. NL Gain 2 additional HP this combat.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int HEAL_AMOUNT = 2;
	private static int UPGRADE_HEAL = 1;
	private static final int UPGRADE_HEAL_UPGRADE = 1;


	public PotentialTreatment() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = HEAL_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.isEthereal = true;
	}

    @Override
	public void triggerWhenDrawn()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SpiritPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseSpiritPower(AbstractDungeon.player, 1), 1));
    }

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new PotentialTreatmentAction(this, UPGRADE_HEAL));
	}

	public AbstractCard makeCopy() {
		return new PotentialTreatment();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.isEthereal = false;
			this.rawDescription = DESCRIPTION_UPGRADED;
			UPGRADE_HEAL += UPGRADE_HEAL_UPGRADE;
			this.initializeDescription();
		}
	}
}
