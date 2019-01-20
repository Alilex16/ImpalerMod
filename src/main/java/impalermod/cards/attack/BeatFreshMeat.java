package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

public class BeatFreshMeat extends AbstractImpalerCard {
	public static final String ID = "BeatFreshMeat";
	public static final	String NAME = "Beat Fresh Meat";
	public static final	String IMG = "cards/attack/BeatFreshMeat.png";
	public static final	String DESCRIPTION = "Lose 3 HP. NL Deal !D! damage. NL If the target has full HP, deal damage 2 times. NL Exhaust.";
    public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 6 HP. NL Deal !D! damage. NL If the target has full HP, deal damage 2 times. NL Exhaust.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static int SELF_DMG = 3;
	private static final int POWER = 10;
	private static final int UPGRADE_POWER = 3;
    private static final int ATTACK_AMOUNT = 2;
    private static final int UPGRADE_ATTACK_AMOUNT = 0;


	public BeatFreshMeat() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = ATTACK_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
        this.CheckBleedingEnergy();
	}

	private void CheckBleedingEnergy()
    {
        if (BleedingEnergy.bleedingEnergyEquipped)
        {
            SELF_DMG = 6;
            this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
            this.initializeDescription();
        }
    }

	public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));

		if (m.currentHealth == m.maxHealth)
		{
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }
	}

	/*
	private void setDescription(boolean useless) {
		this.rawDescription = DESCRIPTION;
		if (AbstractDungeon.player.hasRelic(BleedingEnergy.ID))
		{
			//this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
		}
		this.initializeDescription();
	}
	*/


	public AbstractCard makeCopy() {
		return new BeatFreshMeat();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
            this.initializeDescription();
		}
	}
	
}
