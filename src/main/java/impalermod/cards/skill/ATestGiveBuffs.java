package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.HastePower;
import impalermod.powers.SpiritPower;

public class ATestGiveBuffs extends AbstractImpalerCard {
	public static final String ID = "ATestGiveBuffs";
	public static final	String NAME = "Give them Buffs";
	public static final	String IMG = "cards/skill/placeholderskill.png";
	public static final	String DESCRIPTION = "Apply many Buffs to the target.";

	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 0;
	private static final int COST = 0;

	private static final int BUFF_AMOUNT = 2;
	private static final int UPGRADE_BUFF_AMOUNT = 1;

	public ATestGiveBuffs() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = BUFF_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SpiritPower(m, this.magicNumber), this.magicNumber, true));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new HastePower(m, this.magicNumber), this.magicNumber, true));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, this.magicNumber), this.magicNumber, true));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DexterityPower(m, this.magicNumber), this.magicNumber, true));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new RegenPower(m, this.magicNumber), this.magicNumber, true));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new FocusPower(m, this.magicNumber), this.magicNumber, true));

		//AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.makeStatEquivalentCopy(), 1));
        //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new ATestGiveBuffs(), 1));
	}

	public AbstractCard makeCopy() {
		return new ATestGiveBuffs();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_BUFF_AMOUNT);
		}
	}
	
}
