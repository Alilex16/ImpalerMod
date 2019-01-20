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

public class Bloodrage extends AbstractImpalerCard {
	public static final String ID = "Impale";
	public static final	String NAME = "Impale";
	public static final	String IMG = "cards/attack/placeholderattack.png";
	public static final	String DESCRIPTION = "Deal !D! damage. Costs less [R] for each time you healed HP this turn.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 5;

	private static final int POWER = 16;
	private static final int UPGRADE_POWER = 6;


	public Bloodrage() {
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
			if ((c.rawDescription.contains("Heal") || c.rawDescription.contains("heal")) && (c.rawDescription.contains("HP"))) {
				if (this.cost > 0)
				{
					this.updateCost(-1);
				}
			}
		}
	}

	public void triggerOnCardPlayed(AbstractCard c) {
		if ((c.rawDescription.contains("Heal") || c.rawDescription.contains("heal")) && (c.rawDescription.contains("HP"))) {
			if (this.cost > 0)
			{
				this.updateCost(-1);
			}
		}
	}


	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
	}


	public AbstractCard makeCopy() {
		return new Bloodrage();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
		}
	}
	
}
