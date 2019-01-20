package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.RampingPullAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class RampingPull extends AbstractImpalerCard {
	public static final String ID = "RampingPull";
	public static final	String NAME = "Ramping Pull";
	public static final	String IMG = "cards/attack/RampingPull.png";
	public static final	String DESCRIPTION = "Lose 1 HP. NL Deal !D! damage. NL Draw 1 card, if the card is an Attack, this card deals !M! additional damage this combat.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 2 HP. NL Deal !D! damage. NL Draw 1 card, if the card is an Attack, this card deals !M! additional damage this combat.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 0;
	private static final int COST = 0;

	private static int SELF_DMG = 1;
	private static final int POWER = 3;
	private static final int UPGRADE_POWER = 2;
	private static final int POWER_GROWTH = 1;
	private static final int UPGRADE_POWER_GROWTH = 1;


	public RampingPull() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = POWER_GROWTH;
		this.magicNumber = this.baseMagicNumber;
		this.CheckBleedingEnergy();
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 2;
			this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
			this.initializeDescription();
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m)
	{
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
		AbstractDungeon.actionManager.addToBottom(new RampingPullAction(this, this.magicNumber)); // this.magicNumber instead of POWER_GROWTH ??
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
	}

	public AbstractCard makeCopy() {
		return new RampingPull();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_POWER_GROWTH);
		}
	}
	
}
