package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.LivingOffScrapsAction;
import impalermod.actions.LivingOffScrapsFollowUpAction;
import impalermod.cards.AbstractImpalerCard;

public class LivingOffScraps extends AbstractImpalerCard {
	public static final String ID = "LivingOffScraps";
	public static final	String NAME = "Living Off Scraps";
	public static final	String IMG = "cards/skill/LivingOffScraps.png";
	public static final	String DESCRIPTION = "Draw !M! cards. Discard all non-Bloody cards drawn this way. NL Gain 3 HP for each card kept.";
    public static final	String DESCRIPTION_UPGRADED = "Draw !M! cards. Discard all non-Bloody cards drawn this way. NL Gain 5 HP for each card kept.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 1;

	private int BASE_HEAL;
	private static final int HEAL_AMOUNT = 3;
	private static final int UPGRADE_HEAL_AMOUNT = 2;
	private static final int DRAW_AMOUNT = 4;
	private static final int UPGRADE_DRAW_AMOUNT = 2;

	public LivingOffScraps() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.BASE_HEAL = HEAL_AMOUNT;
		this.baseMagicNumber = DRAW_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		//this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m)
	{
        AbstractDungeon.actionManager.addToBottom(new LivingOffScrapsAction(p, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
		AbstractDungeon.actionManager.addToBottom(new LivingOffScrapsFollowUpAction(p, this.BASE_HEAL));
	}

	public AbstractCard makeCopy() {
		return new LivingOffScraps();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_DRAW_AMOUNT);
			this.BASE_HEAL = HEAL_AMOUNT + UPGRADE_HEAL_AMOUNT;
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
	
}
