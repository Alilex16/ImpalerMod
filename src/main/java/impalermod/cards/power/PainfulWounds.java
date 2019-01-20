package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.PainfulWoundsPower;
import impalermod.relics.BleedingEnergy;

public class PainfulWounds extends AbstractImpalerCard {
	public static final String ID = "PainfulWounds";
	public static final	String NAME = "Painful Wounds";
	public static final	String IMG = "cards/power/PainfulWounds.png";
	public static final	String DESCRIPTION = "Lose 10 HP. NL All damage caused by Bleeding is increased by !M!.";
	public static final	String DESCRIPTION_UPGRADED = "Lose 2 HP. NL All damage caused by Bleeding is increased by !M!.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 20 HP. NL All damage caused by Bleeding is increased by !M!.";
	public static final	String DESCRIPTION_UPGRADED_BLEEDING_ENERGY = "Lose 4 HP. NL All damage caused by Bleeding is increased by !M!.";

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;

	private static final int POOL = 1;
    private static final int COST = 0;

	private static final int POWER = 5;
	private static int SELF_DMG_BASE;
    private static int SELF_DMG = 10;
	private static final int UPGRADE_SELF_DMG = -8;


	public PainfulWounds() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.CheckBleedingEnergy();
		SELF_DMG_BASE = SELF_DMG;
		this.baseMagicNumber = this.magicNumber = POWER;
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 20;
			this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;

			if (upgraded)
			{
				SELF_DMG = 8;
				this.rawDescription = DESCRIPTION_UPGRADED_BLEEDING_ENERGY;
			}

			this.initializeDescription();
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.5F));
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG_BASE));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PainfulWoundsPower(p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new PainfulWounds();
	}

	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			SELF_DMG_BASE += UPGRADE_SELF_DMG;
			rawDescription = DESCRIPTION_UPGRADED;
			if (BleedingEnergy.bleedingEnergyEquipped)
			{
				SELF_DMG_BASE += UPGRADE_SELF_DMG;
				rawDescription = DESCRIPTION_UPGRADED_BLEEDING_ENERGY;

			}
			this.initializeDescription();
		}
	}
}