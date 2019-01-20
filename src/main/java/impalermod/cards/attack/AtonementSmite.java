package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.ExhaustCurseBonusAction;
import impalermod.cards.AbstractImpalerCard;

public class AtonementSmite extends AbstractImpalerCard {
	public static final String ID = "AtonementSmite";
	public static final	String NAME = "Atonement Smite";
	public static final	String IMG = "cards/attack/AtonementSmite.png";
	public static final	String DESCRIPTION = "Deal !D! damage. NL Exhaust a card. NL If Exhausted card is a Curse, gain !M! Spirit.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int POWER = 9;
	private static final int UPGRADE_POWER = 4;
	private static final int SPIRIT_GAIN = 1;
	private static final int UPGRADE_SPIRIT_GAIN = 1;


	public AtonementSmite() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = SPIRIT_GAIN;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
		AbstractDungeon.actionManager.addToBottom(new ExhaustCurseBonusAction(p, p, 1, false, this.magicNumber, false)); // amount = cards to exhaust
	}

	public AbstractCard makeCopy() {
		return new AtonementSmite();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_SPIRIT_GAIN);
			this.initializeDescription();
		}
	}
}