package impalermod.cards.attack;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BleedingPower;

public class ChargingRush extends AbstractImpalerCard implements PostDrawSubscriber, PostBattleSubscriber,
        PostDungeonInitializeSubscriber {
	public static final String ID = "ChargingRush";
	public static final	String NAME = "Charging Rush";
	public static final	String IMG = "cards/attack/ChargingRush.png";
	public static final	String DESCRIPTION = "Deal !D! damage to a random enemy and apply !M! Bleeding. NL When drawn, Bleeding applied increases by 1. NL Exhaust.";
	// Every time this card is drawn, apply 1 additional Bleeding. // When drawn, increases Bleeding amount by 1 // Every time this card is drawn, Bleeding applied increases by 1 // When drawn, Bleeding applied +1.
	public static final	String DESCRIPTION_UPGRADED = "Deal !D! damage to a random enemy and apply !M! Bleeding. NL When drawn, Bleeding applied increases by 1.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int POWER = 3;
	private static final int UPGRADE_POWER = 3;
	private static final int BLEEDING_AMOUNT = 0;
	private static final int BLEEDING_AMOUNT_BONUS = 1;

	private AbstractMonster target;


	public ChargingRush() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseDamage = POWER;
		this.baseMagicNumber = BLEEDING_AMOUNT;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m)
	{
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(true);
        this.target = randomMonster;

		if (AbstractDungeon.player.hasRelic("LizardSkull"))
		{
			AbstractDungeon.player.getRelic("LizardSkull").flash();
			++this.magicNumber;
		}

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, p, new BleedingPower(randomMonster, this.magicNumber, true), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(this.target, new DamageInfo(p, this.damage), 1));
	}

    @Override
    public void receivePostBattle(AbstractRoom arg0) {
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostDungeonInitialize() {
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostDraw(AbstractCard c)    {    }

	@Override
	public void triggerWhenDrawn()
	{
		this.flash();
		this.baseMagicNumber += BLEEDING_AMOUNT_BONUS;
		this.initializeDescription();
	}

	@Override // testing
	public void applyPowers(){
        this.magicNumber = this.baseMagicNumber;
		super.applyPowers();
        this.setDescription(true);
	}

    private void setDescription(boolean addExtended) {
        this.rawDescription = DESCRIPTION;

        if (upgraded)
        {
        	this.rawDescription = DESCRIPTION_UPGRADED;
        }

        this.initializeDescription();
    }


	public AbstractCard makeCopy() {
		return new ChargingRush();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.exhaust = false;
            this.initializeDescription();
		}
	}
	
}
