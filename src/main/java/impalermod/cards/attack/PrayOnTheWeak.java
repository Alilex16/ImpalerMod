package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BleedingPower;

public class PrayOnTheWeak extends AbstractImpalerCard {
	public static final String ID = "PrayOnTheWeak";
	public static final	String NAME = "Pray On The Weak";
	public static final	String IMG = "cards/attack/PrayOnTheWeak.png";
	public static final	String DESCRIPTION = "Deal !D! damage, if target is bleeding.";
	public static final	String[] DESCRIPTION_EXTENDED = new String[] {"They need ... To #r@Bleed@ ..."};

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;

	private static final int COST = 0;
	private static final int POWER = 7;
	private static final int UPGRADE_POWER = 3;

	private AbstractMonster targetMonster;

	public PrayOnTheWeak() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) { // if not bleeding, exhaust card??
		this.targetMonster = m;
		//for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
			if (!targetMonster.isDeadOrEscaped() && (targetMonster.hasPower(BleedingPower.POWER_ID)))
			{
				AbstractDungeon.actionManager.addToBottom(new DamageAction(targetMonster, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL,true));
			}
			else
			{
				AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTION_EXTENDED[0], true));
			}
	}

	public AbstractCard makeCopy() {
		return new PrayOnTheWeak();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
		}
	}
	
}
