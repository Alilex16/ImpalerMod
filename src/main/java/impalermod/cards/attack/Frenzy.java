package impalermod.cards.attack;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import impalermod.actions.FrenzyAction;
import impalermod.cards.AbstractImpalerCard;

import java.util.Iterator;

public class Frenzy extends AbstractImpalerCard {
	public static final String ID = "Frenzy";
	public static final	String NAME = "Frenzy";
	public static final	String IMG = "cards/attack/placeholderattack.png";
	public static final	String DESCRIPTION = "Gain !M! Haste and deal 2 damage for each Bleeding on the enemy. NL Unaffected by Weak. NL Exhaust.";
	public static final	String DESCRIPTION_UPGRADED = "Gain !M! Haste and deal 2 damage for each Bleeding on the enemy. NL Unaffected by Weak.";
    public static final	String[] DESCRIPTION_EXTENDED = new String[] {"~WHAT~ ~?!~ Not ... #r@Bleeding@ ... ~?!~"};

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int POWER = 2;
	private static final int UPGRADE_POWER = 0;
	private static final int HASTE_AMOUNT = 1;
    private static final int UPGRADED_HASTE_AMOUNT = 1;


	public Frenzy() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = HASTE_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
	}

	private int GetPowerCount(AbstractCreature c, String powerId) {
		AbstractPower power =  c.getPower(powerId);
		return power != null ? power.amount : 0;
	}


	public void use(AbstractPlayer p, AbstractMonster m) {
		int bleedingCount = GetPowerCount(m, "Bleeding");

		if (p.hasPower(WeakPower.POWER_ID))
		{
			if (p.hasPower(StrengthPower.POWER_ID))
			{
				int strengthStacks = p.getPower(StrengthPower.POWER_ID).amount;
				this.damage = 1 + strengthStacks;
			}
			else
			{
				this.damage = 1;
			}
		}

		if (bleedingCount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new FrenzyAction(p, m, this.upgraded, this.damageTypeForTurn, this.damage, bleedingCount));
        }
        else
        {
            AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTION_EXTENDED[0], true));
        }
	}


	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		//int bleedingCount = GetPowerCount(m, "Bleeding");

		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		} else {
			boolean isBleeding = false;
			//Iterator var5 = p.drawPile.group.iterator();
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

	public AbstractCard makeCopy() {
		return new Frenzy();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADED_HASTE_AMOUNT);
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.exhaust = false;
			this.initializeDescription();
		}
	}
	
}
