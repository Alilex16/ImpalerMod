package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BleedingPower;

public class Transfusion extends AbstractImpalerCard {
	public static final String ID = "Transfusion";
	public static final	String NAME = "Transfusion";
	public static final	String IMG = "cards/attack/Transfusion.png";
	public static final	String DESCRIPTION = "Deal !D! damage. NL Apply !M! Bleeding and gain !M! Regeneration.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int POWER = 1;
	private static final int UPGRADE_POWER = 1;
	private static final int TRANSFUSION_AMOUNT = 1;
	private static final int UPGRADE_TRANSFUSION_AMOUNT = 1;

	public Transfusion() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = TRANSFUSION_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, this.magicNumber), this.magicNumber));

		if (AbstractDungeon.player.hasRelic("LizardSkull"))
		{
			AbstractDungeon.player.getRelic("LizardSkull").flash();
			++this.magicNumber;
		}

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new BleedingPower(m, this.magicNumber, true), magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Transfusion();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_TRANSFUSION_AMOUNT);
		}
	}
	
}
