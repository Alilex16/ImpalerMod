package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.RedemptionPower;

public class Redemption extends AbstractImpalerCard {
    public static final String ID = "Redemption";
    public static final String NAME = "Redemption";
    public static final	String IMG = "cards/power/Redemption.png";
    public static final	String DESCRIPTION = "Every turn, the HP costs of !M! Bloody card is ignored.";
    public static final	String DESCRIPTION_UPGRADED = "Every turn, the HP costs of !M! Bloody cards are ignored.";
    //public static final	String DESCRIPTION_MULTI = "Every turn, the HP costs of !M! Bloody cards are ignored.";

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    private static final int POWER = 1;
    private static final int UPGRADE_POWER = 1;

    public Redemption() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RedemptionPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Redemption();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_POWER);
            //this.upgradeBaseCost(UPGRADED_COST);
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
        }
    }
}