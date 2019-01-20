package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import impalermod.cards.AbstractImpalerCard;

public class MenacingCrunch extends AbstractImpalerCard {
	public static final String ID = "MenacingCrunch";
	public static final	String NAME = "Menacing Crunch";
	public static final	String IMG = "cards/attack/MenacingCrunch.png";
	public static final	String DESCRIPTION = "Deal !D! damage. If you're Vulnerable, apply !M! Vulnerable.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 0;

	private static final int POWER = 3;
	private static final int UPGRADE_POWER = 1;
	private static final int VULNERABLE_AMOUNT = 1;
    private static final int UPGRADED_VULNERABLE_AMOUNT = 1;


	public MenacingCrunch() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = VULNERABLE_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

		if (p.hasPower(VulnerablePower.POWER_ID))
		{
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, true), this.magicNumber));
		}
	}

    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        return true;
    }


	public AbstractCard makeCopy() {
		return new MenacingCrunch();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADED_VULNERABLE_AMOUNT);
		}
	}
	
}
