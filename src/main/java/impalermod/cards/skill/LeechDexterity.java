package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import impalermod.cards.AbstractImpalerCard;

public class LeechDexterity extends AbstractImpalerCard {
	public static final String ID = "LeechDexterity";
	public static final	String NAME = "Leech Dexterity";
	public static final	String IMG = "cards/skill/LeechDexterity.png";
	public static final	String DESCRIPTION = "Apply !M! Vulnerable and gain !M! Dexterity. NL Add 1 Dazed to your discard pile. NL Exhaust.";

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int BUFF_AMOUNT = 2;
	private static final int UPGRADE_BUFF_AMOUNT = 1;

	public LeechDexterity() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = BUFF_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
		this.cardPreviewTooltip = new Dazed();
	}

	public void use(AbstractPlayer p, AbstractMonster m)
	{
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));

		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, false));
	}

	public AbstractCard makeCopy() {
		return new LeechDexterity();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_BUFF_AMOUNT);
			this.initializeDescription();
		}
	}

}
