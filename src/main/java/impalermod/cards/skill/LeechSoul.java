package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.LeechSoulAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.cards.attack.LeechLife;
import impalermod.cards.curse.VertcruxCurse;

import java.util.Random;

public class LeechSoul extends AbstractImpalerCard {
	public static final String ID = "LeechSoul";
	public static final	String NAME = "Leech Soul";
	public static final	String IMG = "cards/skill/LeechSoul.png";
	public static final	String DESCRIPTION = "Ethereal. NL Choose a Leech Card to add to your hand. NL Shuffle a Vertcrux Curse in your draw pile.";
	public static final	String DESCRIPTION_UPGRADED = "Ethereal. NL Choose an Upgraded Leech Card to add to your hand. NL Shuffle a Vertcrux Curse in your draw pile.";
	public AbstractCard previewTooltip;

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0; // -2

	public LeechSoul() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		//this.exhaust = true;
        this.isEthereal = true;

        Random rand = new Random();
        int number = rand.nextInt(7) + 1 ;

        if (number == 1)
        {
            previewTooltip = new LeechLife();
        }
        if (number == 2)
        {
            previewTooltip = new LeechSpeed();
        }
        if (number == 3)
        {
            previewTooltip = new LeechDexterity();
        }
        if (number == 4)
        {
            previewTooltip = new LeechStrength();
        }
		if (number == 5)
		{
			previewTooltip = new LeechEnergy();
		}
		if (number == 6)
		{
			previewTooltip = new LeechPotency();
		}
		if (number == 7)
		{
			previewTooltip = new VertcruxCurse();
		}

        this.cardPreviewTooltip = previewTooltip;
	}

	public void use(AbstractPlayer p, AbstractMonster m)
	{
		AbstractDungeon.actionManager.addToBottom(new LeechSoulAction(this.upgraded));

		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VertcruxCurse(), 1, true, false));
	}
	
	public AbstractCard makeCopy() {
		return new LeechSoul();
	}

	public void upgrade() {
		if (!upgraded) {
			upgradeName();
            this.rawDescription = DESCRIPTION_UPGRADED;
            cardPreviewTooltip.upgrade();
            this.initializeDescription();
		}
	}
}
