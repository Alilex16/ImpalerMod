package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

import java.util.Iterator;

public class Exsanguinate extends AbstractImpalerCard {
	public static final String ID = "Exsanguinate";
	public static final	String NAME = "Exsanguinate";
	public static final	String IMG = "cards/attack/Exsanguinate.png";
	public static final	String DESCRIPTION = "Lose 6 HP. NL Deal !D! damage for each stack of Bleeding. NL Target loses all Bleeding. NL Exhaust.";
	public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 12 HP. NL Deal !D! damage for each stack of Bleeding. NL Target loses all Bleeding. NL Exhaust.";
	public static final	String[] DESCRIPTION_EXTENDED = new String[] {"They need ... To #r@Bleed@ ..."};

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static int SELF_DMG = 6;
	private static final int POWER = 6;
	private static final int UPGRADE_POWER = 2;


	public Exsanguinate() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = POWER;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
		this.damageType = DamageInfo.DamageType.THORNS;
		this.CheckBleedingEnergy();
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			SELF_DMG = 12;
			this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
			this.initializeDescription();
		}
	}

	private int GetPowerCount(AbstractCreature c, String powerId) {
		AbstractPower power =  c.getPower(powerId);
		return power != null ? power.amount : 0;
	}


    @Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int bleedingCount = GetPowerCount(m, "Bleeding");

		this.damage = this.damage * bleedingCount;

		if (bleedingCount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageType), AbstractGameAction.AttackEffect.NONE));
        }
        else
        {
            AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTION_EXTENDED[0], true));
        }

		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Bleeding"));

	}

    @Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		} else {
			boolean isBleeding = false;
			Iterator var5 = AbstractDungeon.getMonsters().monsters.iterator();

			while(var5.hasNext()) {
				AbstractMonster c = (AbstractMonster)var5.next();
				if (!c.isDead && !c.escaped && c.hasPower("Bleeding"))
				{
					isBleeding = true;
				}
			}

			if (!isBleeding) {

                canUse = false;
			}

			return canUse;
		}
	}


    @Override
    public void applyPowers() {

        int bleedingCount = 0;

        if (AbstractDungeon.getCurrRoom().monsters.hoveredMonster != null)
        {
            AbstractMonster hoveredMonster = AbstractDungeon.getCurrRoom().monsters.hoveredMonster;
            bleedingCount = GetPowerCount(hoveredMonster, "Bleeding");
        }

        if (bleedingCount > 0)
        {
            this.damage = this.damage * bleedingCount;
        }

        super.applyPowers();
        this.setDescription(true);
    }

    private void setDescription(boolean addExtended) {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }




    public AbstractCard makeCopy() {
		return new Exsanguinate();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_POWER);
		}
	}
	
}
