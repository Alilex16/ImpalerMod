package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import impalermod.cards.AbstractImpalerCard;

import java.util.Random;

public class Confess extends AbstractImpalerCard {
	public static final String ID = "Confess";
	public static final	String NAME = "Confess";
	public static final	String IMG = "cards/skill/Confess.png";
	public static final	String DESCRIPTION = "Unplayable. NL When this card is discarded from your hand, gain !B! Block. NL Block is increased by 2 for each Spirit.";
	public static final	String DESCRIPTION_UPGRADED = "Unplayable. NL When this card is discarded from your hand, gain !B! Block. NL Block is increased by 3 for each Spirit.";
	public static final	String[] DESCRIPTION_EXTENDED = new String[] {"I'm not ~confessing~ to anything ...", "It wasn't ~me~ ...", "I didn't do ~anything~ wrong ..."};

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 1;
	private static final int COST = -2;

	private static final int BLOCK_AMOUNT = 5;
	private static final int UPGRADE_BLOCK_AMOUNT = 3;
	private static final int SPIRITUAL_BLOCK = 2;
	private static final int UPGRADED_SPIRITUAL_BLOCK = 3;


	public Confess() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseBlock = BLOCK_AMOUNT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        Random rand = new Random();
        int number = rand.nextInt(6);

        if (number >= 3)
        {
            number = 0;
        }

        this.cantUseMessage = DESCRIPTION_EXTENDED[number];
		return false;
	}

	public void triggerOnManualDiscard()
	{
		int spiritCount = GetPowerCount(AbstractDungeon.player, "Spirit");

		if (spiritCount >= 1)
		{
			if (upgraded)
			{
				this.block += (spiritCount * 3);
			}
			else
			{
				this.block += (spiritCount * 2);
			}

		}

		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
	}

	private int GetPowerCount(AbstractCreature c, String powerId) {
		AbstractPower power =  c.getPower(powerId);
		return power != null ? power.amount : 0;
	}

	@Override
	public void applyPowers() {
		int spiritCount = GetPowerCount(AbstractDungeon.player, "Spirit");
		//this.damage = (calculate / 2) * this.baseMagicNumber;

		if (upgraded)
		{
			this.block += spiritCount * UPGRADED_SPIRITUAL_BLOCK;
		}
		else
		{
			this.block += spiritCount * SPIRITUAL_BLOCK;
		}


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
		return new Confess();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_BLOCK_AMOUNT);
			this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
		}
	}
	
}
