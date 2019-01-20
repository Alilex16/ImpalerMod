package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BloodshieldPower;

public class Bloodsplosion extends AbstractImpalerCard {
	public static final String ID = "Bloodsplosion";
	public static final	String NAME = "Bloodsplosion";
	public static final	String IMG = "cards/attack/Bloodsplosion.png";
	public static final	String DESCRIPTION = "Deal damage equal to Bloodshield. NL Currently deals !D! damage. NL Remove all Bloodshield.";
	public static final	String DESCRIPTION_UPGRADED = "Deal damage equal to amount of Bloodshield. NL Currently deals !D! damage.";

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static final int POWER = 0;

	public Bloodsplosion() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
	}


	@Override
	public void applyPowers() {
		if (AbstractDungeon.player != null && BloodshieldPower.bloodShieldAmount > 0) {
			this.baseDamage = POWER + BloodshieldPower.bloodShieldAmount;
		} else {
			this.baseDamage = POWER;
		}
		super.applyPowers();
		this.setDescription(true);
	}

	private void setDescription(boolean addExtended) {
		this.rawDescription = DESCRIPTION;
		if (upgraded) {
			this.rawDescription = DESCRIPTION_UPGRADED;
		}
		this.initializeDescription();
	}


	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AbstractDungeon.player != null && BloodshieldPower.bloodShieldAmount > 0) {
			this.baseDamage = POWER + BloodshieldPower.bloodShieldAmount;
		} else {
			this.baseDamage = POWER;
		}

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.POISON));

		if (!upgraded)
        {
            int bloodShield = BloodshieldPower.bloodShieldAmount;
            p.loseBlock(bloodShield);
            BloodshieldPower.bloodShieldAmount = 0;
            BloodshieldPower.isBloodshielded = false;
        }
	}

	public AbstractCard makeCopy() {
		return new Bloodsplosion();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
	
}
