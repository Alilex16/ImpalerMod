package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BleedingPower;

public class VitalGash extends AbstractImpalerCard {
	public static final String ID = "VitalGash";
	public static final	String NAME = "Vital Gash";
	public static final	String IMG = "cards/attack/VitalGash.png";
	public static final	String DESCRIPTION = "Deal !D! damage. NL You gain !M! Bleeding.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 0;

	private static final int POWER = 8;
	private static final int UPGRADE_POWER = 5;
	private static final int BLEED_AMT = 1;


	public VitalGash() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = BLEED_AMT;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BleedingPower(p, magicNumber, false), 1, true, AbstractGameAction.AttackEffect.NONE));
	}

	public AbstractCard makeCopy() {
		return new VitalGash();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
		}
	}
	
}
