package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;

public class Revange extends AbstractImpalerCard {
	public static final String ID = "Revange";
	public static final	String NAME = "Revange";
	public static final	String IMG = "cards/attack/Revange.png";
	public static final	String DESCRIPTION = "Deal damage equal to missing HP.";
	public static final	String DESCRIPTION_EXTENDED = "Discard a card. NL Deal damage equal to missing HP. NL Currently deals !D! damage.";
	//public static final String[] DESCRIPTION_EXTENDED = new String[] { " (!D!)." };

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;

	private static final int COST = 3;
	private static final int UPGRADED_COST = 2;

	private static final int POWER = 1;
	//private static final int UPGRADE_POWER = 4;
	private static final int MISSING_HP = 1;


	public Revange() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = MISSING_HP;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m)
	{
		int calculate = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
		//this.damage = (calculate / 2) * this.baseMagicNumber;
		this.baseMagicNumber = calculate;
		this.baseDamage = this.baseMagicNumber;
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, false));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

	}

	@Override
	public void applyPowers() {
		int calculate = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
		//this.damage = (calculate / 2) * this.baseMagicNumber;
		this.baseMagicNumber = calculate;
		this.baseDamage = this.baseMagicNumber;
		super.applyPowers();
		this.setDescription(true);
	}

	private void setDescription(boolean addExtended) {
		this.rawDescription = DESCRIPTION;
		if (addExtended) {
			this.rawDescription = DESCRIPTION_EXTENDED;
		}
		this.initializeDescription();
	}


	public AbstractCard makeCopy() {
		return new Revange();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADED_COST);
			//this.upgradeDamage(UPGRADE_POWER);
		}
	}
	
}
