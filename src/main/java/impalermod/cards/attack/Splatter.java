package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BleedingPower;
import impalermod.relics.BleedingEnergy;

public class Splatter extends AbstractImpalerCard {
	public static final String ID = "Splatter";
	public static final	String NAME = "Splatter";
	public static final	String IMG = "cards/attack/placeholderattack.png";
	public static final	String DESCRIPTION = "Lose 2 HP. NL Deal !D! damage. NL Apply !M! Weak. NL If target is Bleeding, apply 1 additional Weak.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 4 HP. NL Deal !D! damage. NL Apply !M! Weak. NL If target is Bleeding, apply 1 additional Weak.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static int SELF_DMG = 2;
	private static final int POWER = 4;
	private static final int UPGRADE_POWER = 2;
	private static final int WEAK_AMOUNT = 2;
    private static final int UPGRADE_WEAK_AMOUNT = 1;


	public Splatter() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = WEAK_AMOUNT;
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

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, true), this.magicNumber));

		if (m.hasPower(BleedingPower.POWER_ID))
		{
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 1, true), 1));
		}
	}

	public AbstractCard makeCopy() {
		return new Splatter();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_WEAK_AMOUNT);
		}
	}
	
}
