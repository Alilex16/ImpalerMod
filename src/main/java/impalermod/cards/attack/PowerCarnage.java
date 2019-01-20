package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

import java.util.Iterator;

public class PowerCarnage extends AbstractImpalerCard {
	public static final String ID = "PowerCarnage";
	public static final	String NAME = "Power Carnage ";
	public static final	String IMG = "cards/attack/PowerCarnage.png";
	public static final	String DESCRIPTION = "Ethereal. NL Lose !M! HP. NL Costs 2 less HP for every Power card played this combat. NL Deal !D! damage.";
    public static final	String DESCRIPTION_MAX = "Ethereal. NL Deal !D! damage.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int POWER = 20;
	private static final int UPGRADE_POWER = 8;
	private static int SELF_DMG = 12;
	private static final int UPGRADE_SELF_DMG = -2;


	public PowerCarnage() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.CheckBleedingEnergy();
		this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = SELF_DMG;
		this.isEthereal = true;
		if (CardCrawlGame.dungeon != null) {
			this.configureCostsOnNewCard();
		}
	}

    private void CheckBleedingEnergy()
    {
        if (BleedingEnergy.bleedingEnergyEquipped)
        {
            SELF_DMG = 24;
            this.initializeDescription();
        }
    }

	private void configureCostsOnNewCard() {
		Iterator var1 = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();

		while(var1.hasNext()) {
			AbstractCard c = (AbstractCard)var1.next();
			if (c.type == CardType.POWER) {
				this.upgradeMagicNumber(UPGRADE_SELF_DMG);
			}

			if (this.magicNumber <= 0)
			{
                this.rawDescription = DESCRIPTION_MAX;
                this.initializeDescription();
            }
		}
	}

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.POWER) {
            this.upgradeMagicNumber(UPGRADE_SELF_DMG);
        }

        if (this.magicNumber <= 0)
        {
            this.rawDescription = DESCRIPTION_MAX;
            this.initializeDescription();
        }

    }

	public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.magicNumber > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
        }
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
	}

	public AbstractCard makeCopy() {
		return new PowerCarnage();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
            this.initializeDescription();
		}
	}
	
}
