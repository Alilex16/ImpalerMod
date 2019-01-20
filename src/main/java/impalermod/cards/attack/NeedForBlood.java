package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BleedingPower;

public class NeedForBlood extends AbstractImpalerCard {
	public static final String ID = "NeedForBlood";
	public static final	String NAME = "Need For Blood";
	public static final	String IMG = "cards/attack/NeedForBlood.png";
	public static final	String DESCRIPTION = "Deal !D! damage. NL If target has Block, damage is doubled. NL If dealing [unblocked] damage, apply !M! Bleeding.";

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 3;

	private static final int POWER = 18;
	private static final int UPGRADE_POWER = 7;
	private static final int BLEEDING_AMOUNT = 3;
    private static final int UPGRADE_BLEEDING_AMOUNT = 1;

	public NeedForBlood() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = BLEEDING_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void applyPowers() {

		super.applyPowers();
		this.setDescription(true);
	}

	private void setDescription(boolean addExtended) {
		this.rawDescription = DESCRIPTION;
		this.initializeDescription();
	}


	public void use(AbstractPlayer p, AbstractMonster m) {
		if (m.currentBlock > 0)
		{
			this.damage = POWER * 2;
		}

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));

		if (m.currentBlock < this.damage)
		{
			if ((!m.hasPower("Buffer")) || !m.hasPower("IntangiblePlayer"))
			{

				if (AbstractDungeon.player.hasRelic("LizardSkull"))
				{
					AbstractDungeon.player.getRelic("LizardSkull").flash();
					++this.magicNumber;
				}

				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new BleedingPower(m, this.magicNumber, true), this.magicNumber));
			}
		}
	}

	public AbstractCard makeCopy() {
		return new NeedForBlood();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_BLEEDING_AMOUNT);
			this.initializeDescription();
		}
	}
	
}
