package impalermod.cards.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;

import java.util.Iterator;

public class BloodrageOld extends AbstractImpalerCard {
	public static final String ID = "BloodrageOld";
	public static final	String NAME = "BloodrageOld";
	public static final	String IMG = "cards/attack/placeholderattack.png";
	public static final	String DESCRIPTION = "NL Costs 1 less [R] for each time you gained HP this turn. NL Deal !D! damage.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 5;

	private static final int POWER = 16;
	private static final int UPGRADE_POWER = 6;


	public BloodrageOld() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		if (CardCrawlGame.dungeon != null) {
			this.configureCostsOnNewCard();
		}
	}

	private void configureCostsOnNewCard() {
		Iterator var1 = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();

		while(var1.hasNext()) {
			AbstractCard c = (AbstractCard)var1.next();
			if (c.heal >= 1) {
				this.updateCost(-1);
			}
		}
	}

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.heal >= 1) {
			this.updateCost(-1);
        }
    }

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
	}

	public AbstractCard makeCopy() {
		return new BloodrageOld();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			if (this.cost < 5) {
				this.upgradeBaseCost(this.cost - 1);
				if (this.cost < 0) {
					this.cost = 0;
				}
			} else {
				this.upgradeBaseCost(4);
			}
			this.upgradeDamage(UPGRADE_POWER);;
		}
	}
	
}
