package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.DoubleBleedingAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class Haemorrhage extends AbstractImpalerCard {
	public static final String ID = "Haemorrhage";
	public static final	String NAME = "Haemorrhage ";
	public static final	String IMG = "cards/attack/placeholderattack.png";
	public static final	String DESCRIPTION = "Lose !M! HP. NL Deal !D! damage. NL Doubles amount of Bleeding.";

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static final int POWER = 5;
	private static final int UPGRADE_POWER = 3;
	private static int SELF_DMG = 8;
	private static final int UPGRADE_SELF_DMG = -5;


	public Haemorrhage() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.CheckBleedingEnergy();
		this.magicNumber = this.baseMagicNumber = SELF_DMG;
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 16;

			if (upgraded)
			{
				SELF_DMG = 6;
			}
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		AbstractDungeon.actionManager.addToBottom(new DoubleBleedingAction(m, p));
	}

	public AbstractCard makeCopy() {
		return new Haemorrhage();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			if (BleedingEnergy.bleedingEnergyEquipped)
			{
				this.upgradeMagicNumber(UPGRADE_SELF_DMG);
			}
			this.upgradeMagicNumber(UPGRADE_SELF_DMG);
            this.initializeDescription();
		}
	}
	
}
