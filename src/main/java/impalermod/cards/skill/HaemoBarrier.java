package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BleedingPower;

public class HaemoBarrier extends AbstractImpalerCard {
	public static final String ID = "HaemoBarrier";
	public static final	String NAME = "Haemo Barrier";
	public static final	String IMG = "cards/skill/HaemoBarrier.png";
	public static final	String DESCRIPTION = "Apply 1 Bleeding, gain !M! Block for each Bleeding on that target.";
    public static final	String DESCRIPTION_EXTENDED = "Apply 1 Bleeding, gain !M! Block for each Bleeding on that target. NL [Gain !B! Block].";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 1;

    private static final int POWER = 2;
    private static final int UPGRADE_BONUS = 1;
    private static final int BLOCK = POWER;
    private static final int UPGRADE_BLOCK = UPGRADE_BONUS;
	private static int bleedingAmount = 1;

	public HaemoBarrier() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseBlock = BLOCK;
        this.baseMagicNumber = POWER;
        this.magicNumber = this.baseMagicNumber;
	}

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

	public void use(AbstractPlayer p, AbstractMonster m) {

		if (AbstractDungeon.player.hasRelic("LizardSkull"))
		{
			AbstractDungeon.player.getRelic("LizardSkull").flash();
			++bleedingAmount;
		}

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new BleedingPower(m, bleedingAmount, true), bleedingAmount));

        int bleedingCount = GetPowerCount(m, "Bleeding");

        this.block = 0;

        if (!m.hasPower(ArtifactPower.POWER_ID))
        {
            this.block += this.magicNumber * (bleedingCount + 1); // +1 = the Bleeding added by this skill, which doesn't get counted
        }

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
	}

	public AbstractCard makeCopy() {
		return new HaemoBarrier();
	}

	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_BLOCK);
			this.upgradeMagicNumber(UPGRADE_BONUS);
		}
	}
}
