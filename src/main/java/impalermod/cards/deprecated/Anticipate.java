package impalermod.cards.deprecated;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.actions.deprecated.AnticipateAction;
import impalermod.cards.AbstractImpalerCard;

public class Anticipate extends AbstractImpalerCard {
	public static final String ID = "Anticipate";
	public static final	String NAME = "Anticipate";
	public static final	String IMG = "cards/attack/placeholderattack.png";
	public static final	String DESCRIPTION = "Shuffle a card into your draw pile, depending on enemy intent.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 0;

	private static final int POWER = 1;

	public Anticipate() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = POWER;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new AnticipateAction(this.magicNumber, m));
	}

	public AbstractCard makeCopy() {
		return new Anticipate();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
		}
	}
	
}
