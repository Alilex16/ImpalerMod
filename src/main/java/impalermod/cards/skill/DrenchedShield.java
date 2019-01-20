package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BloodshieldPower;

public class DrenchedShield extends AbstractImpalerCard {
	public static final String ID = "DrenchedShield";
	public static final	String NAME = "Drenched Shield";
	public static final	String IMG = "cards/skill/DrenchedShield.png";
	public static final	String DESCRIPTION = "Gain Block equal to missing HP. NL Currently gain !B! Block. NL [Activates Bloodshield]";
	public static final	String DESCRIPTION_UPGRADED = "Gain Block equal to missing HP + 3. NL Currently gain !B! Block. NL [Activates Bloodshield]";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static final int POWER = 0;
	private static final int UPGRADE_POWER = 3;

	public DrenchedShield() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseBlock = POWER;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		int calculate = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;

		if (upgraded)
		{
			calculate += UPGRADE_POWER;
		}

        //this.block += calculate;
		this.baseBlock += calculate;

		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        BloodshieldPower.bloodShieldAmount += this.block;
        BloodshieldPower.isBloodshielded = true;
	}

    @Override
    public void applyPowers() {
        int calculate = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;

        if (upgraded)
        {
            calculate += UPGRADE_POWER;
        }

        this.baseBlock = calculate;

        super.applyPowers();
        this.setDescription(true);
    }

    private void setDescription(boolean addExtended) {
        this.rawDescription = DESCRIPTION;
        if (upgraded) {
            this.rawDescription = DESCRIPTION_UPGRADED;
        }
        this.initializeDescription();
    }


	public AbstractCard makeCopy() {
		return new DrenchedShield();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_POWER);
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
	
}
