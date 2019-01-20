package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import impalermod.actions.HPLoseFromDeckToHandAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.relics.BleedingEnergy;

import java.util.Iterator;

public class PainForPleasure extends AbstractImpalerCard {
	public static final String ID = "PainForPleasure";
	public static final	String NAME = "Pain For Pleasure";
	public static final	String IMG = "cards/attack/PainForPleasure.png";
	public static final	String DESCRIPTION = "Lose !M! HP. NL Deal !D! damage. NL Choose a Bloody card from your draw pile and put it in your hand.";
	public static final	String DESCRIPTION_UPGRADED = "Deal !D! damage. NL Choose a Bloody card from your draw pile and put it in your hand.";
	public static final	String[] DESCRIPTION_EXTENDED = new String[] {"No #r@Bloody@ cards..."};

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static int SELF_DMG = 5;
	private static final int UPGRADED_SELF_DMG = -5;
	private static final int POWER = 5;
	private static final int UPGRADE_POWER = 4;
	private int amountBloodyCards;


	public PainForPleasure() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.CheckBleedingEnergy();
		this.baseMagicNumber = SELF_DMG;
		this.magicNumber = this.baseMagicNumber;
	}

	private void CheckBleedingEnergy()
	{
		if (BleedingEnergy.bleedingEnergyEquipped)
		{
			if (!upgraded)
			{
				SELF_DMG = 10;
			}
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
	    if (!upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
        }

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

		if (amountBloodyCards >= 1)
		{
            AbstractDungeon.actionManager.addToBottom(new HPLoseFromDeckToHandAction(1));
		}
		else
        {
            AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTION_EXTENDED[0], true));
        }

		amountBloodyCards = 0;
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		} else {
			boolean hasSkill = false;
			Iterator var5 = p.drawPile.group.iterator();

			while(var5.hasNext()) {
				AbstractCard c = (AbstractCard)var5.next();
				if ((c.rawDescription.contains("Lose") || c.rawDescription.contains("lose")) && (c.rawDescription.contains("HP")))
				{
                    amountBloodyCards += 1;
					hasSkill = true;
				}
			}

			if (!hasSkill) {
				canUse = true;
			}

			return canUse;
		}
	}


	public AbstractCard makeCopy() {
		return new PainForPleasure();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADED_SELF_DMG);
			if (BleedingEnergy.bleedingEnergyEquipped)
			{
				this.upgradeMagicNumber(UPGRADED_SELF_DMG);
			}
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
	
}
