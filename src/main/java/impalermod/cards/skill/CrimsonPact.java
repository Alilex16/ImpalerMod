package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.HPGainFromDeckToHandAction;
import impalermod.cards.AbstractImpalerCard;

import java.util.Iterator;

public class CrimsonPact extends AbstractImpalerCard {
	public static final String ID = "CrimsonPact";
	public static final	String NAME = "Crimson Pact";
	public static final	String IMG = "cards/skill/CrimsonPact.png";
	public static final	String DESCRIPTION = "Discard a card, then search your draw pile for a Healing card and place it in your hand. NL Exhaust.";
	public static final	String DESCRIPTION_UPGRADED = "Discard a card, then search your draw pile for a Healing card and place it in your hand.";
	public static final	String[] DESCRIPTION_EXTENDED = new String[] {"I can't make a ~pact~ right now."};

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0;


	public CrimsonPact() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.exhaust = true;
	}


	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, false));
		AbstractDungeon.actionManager.addToBottom(new HPGainFromDeckToHandAction(1));
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
				//if (c.type == CardType.SKILL)
				if (((c.rawDescription.contains("regeneration") || c.rawDescription.contains("Regeneration")) || (c.rawDescription.contains("Heal") || c.rawDescription.contains("heal")) && (c.rawDescription.contains("HP"))) && (!c.cardID.contains("BForBlood") && !c.cardID.contains("PleasureForPain")))
				//if (c.rawDescription.contains("Exhaust") || c.rawDescription.contains("exhaust"))
				{
					hasSkill = true; // what now happens, when condition is met, choose ANY card, instead of just the ones with the specific words
				}
			}

			if (!hasSkill) {
				this.cantUseMessage = DESCRIPTION_EXTENDED[0];
				canUse = false;
			}

			return canUse;
		}
	}

    public AbstractCard makeCopy() {
		return new CrimsonPact();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.exhaust = false;
            this.initializeDescription();
		}
	}
	
}
