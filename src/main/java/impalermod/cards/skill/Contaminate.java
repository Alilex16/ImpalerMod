package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import impalermod.cards.AbstractImpalerCard;

import java.util.Iterator;

public class Contaminate extends AbstractImpalerCard {
	public static final String ID = "Contaminate";
	public static final	String NAME = "Contaminate";
	public static final	String IMG = "cards/skill/Contaminate.png";
	public static final	String DESCRIPTION = "Apply Weak and Vulnerable equal to the amount of Bleeding. NL Exhaust."; // ( !M! )
	public static final	String DESCRIPTION_UPGRADED = "Apply Weak and Vulnerable equal to the amount of Bleeding.";
    public static final	String[] DESCRIPTION_EXTENDED = new String[] {"No #r@Blood@ to ~Contaminate~ ..."};

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 1;

	public Contaminate() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = 0;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int bleedingCount = GetPowerCount(m, "Bleeding");
		this.magicNumber = bleedingCount;

        if (bleedingCount > 0)
		{
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new WeakPower(m, this.magicNumber, true), this.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new VulnerablePower(m, this.magicNumber, true), this.magicNumber));
		}
		else
        {
            AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTION_EXTENDED[0], true));
        }
	}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
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



	private int GetPowerCount(AbstractCreature c, String powerId) {
		AbstractPower power =  c.getPower(powerId);
		return power != null ? power.amount : 0;
	}

	public AbstractCard makeCopy() {
		return new Contaminate();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.exhaust = false;
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
		}
	}
	
}
