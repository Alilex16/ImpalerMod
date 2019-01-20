package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import impalermod.cards.AbstractImpalerCard;

public class DinnerTime extends AbstractImpalerCard {
	public static final String ID = "DinnerTime";
	public static final	String NAME = "Dinner Time ";
	public static final	String IMG = "cards/attack/DinnerTime.png";
	public static final	String DESCRIPTION = "Deal !D! damage to the weakest enemy !M! times. NL Apply 1 Weak. NL Exhaust.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int POWER = 2;
	private static final int ATTACK_AMOUNT = 4;
	private static final int UPGRADE_ATTACK_AMOUNT = 2;
	private static final int WEAK_AMOUNT = 1;

	public DinnerTime() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = ATTACK_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m)
	{
        AbstractMonster target = getLowestHPMonster();

        AbstractDungeon.actionManager.addToBottom(new DamageAction(target ,new DamageInfo(target, this.damage, this.damageTypeForTurn)));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(target ,new DamageInfo(target, this.damage, this.damageTypeForTurn)));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(target ,new DamageInfo(target, this.damage, this.damageTypeForTurn)));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(target ,new DamageInfo(target, this.damage, this.damageTypeForTurn)));

		if (upgraded)
		{
			AbstractDungeon.actionManager.addToBottom(new DamageAction(target ,new DamageInfo(target, this.damage, this.damageTypeForTurn)));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(target ,new DamageInfo(target, this.damage, this.damageTypeForTurn)));
		}

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new WeakPower(target, WEAK_AMOUNT, false), WEAK_AMOUNT));
	}

	public AbstractMonster getLowestHPMonster() {
		if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
			return null;
		}
		int lowestHp = 999;
		AbstractMonster tmp = null;
		for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
			if (!m.halfDead && !m.isDying && !m.isEscaping && m.currentHealth < lowestHp) {
				tmp = m;
				lowestHp = m.currentHealth;
			}
		}
		return tmp;
	}



	public AbstractCard makeCopy() {
		return new DinnerTime();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_ATTACK_AMOUNT);
		}
	}
	
}
