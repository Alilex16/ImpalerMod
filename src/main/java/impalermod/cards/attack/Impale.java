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

public class Impale extends AbstractImpalerCard {
	public static final String ID = "Impale";
	public static final	String NAME = "Impale";
	public static final	String IMG = "cards/attack/placeholderattack.png";
	public static final	String DESCRIPTION = "Deal !D! damage. NL Apply !M! Bleeding.";

	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static final int POWER = 11;
	private static final int UPGRADE_POWER = 4;
	private static final int BLEEDING_AMOUNT = 2;
    private static final int UPGRADE_BLEED_AMT = 1;


	public Impale() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = BLEEDING_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

        if (AbstractDungeon.player.hasRelic("LizardSkull"))
        {
            AbstractDungeon.player.getRelic("LizardSkull").flash();
            ++this.magicNumber;
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new BleedingPower(m, this.magicNumber, true), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
	}

	public AbstractCard makeCopy() {
		return new Impale();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_BLEED_AMT);
		}
	}
	
}
