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
import com.megacrit.cardcrawl.powers.VulnerablePower;
import impalermod.actions.LoseMaxHPAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class RigidRupture extends AbstractImpalerCard {
	public static final String ID = "RigidRupture";
	public static final	String NAME = "Rigid Rupture ";
	public static final	String IMG = "cards/attack/RigidRupture.png";
	public static final	String DESCRIPTION = "Lose 4 HP. NL Deal !D! damage. NL Apply !M! Vulnerable. NL Lowers Max HP equal to damage done.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 8 HP. NL Deal !D! damage. NL Apply !M! Vulnerable. NL Lowers Max HP equal to damage done.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static int SELF_DMG = 4;
	private static final int POWER = 6;
	private static final int UPGRADE_POWER = 3;
	private static final int VULNERABLE_AMOUNT = 1;
	private static final int VULNERABLE_AMOUNT_BONUS = 1;


	public RigidRupture() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		//this.baseMagicNumber = SELF_DMG;
		this.magicNumber = this.baseMagicNumber = VULNERABLE_AMOUNT;
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

	public void use(AbstractPlayer p, AbstractMonster m)
	{
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new LoseMaxHPAction(m, this.damage));
	}

	public AbstractCard makeCopy() {
		return new RigidRupture();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(VULNERABLE_AMOUNT_BONUS);
			this.exhaust = false;
            this.initializeDescription();
		}
	}
	
}
