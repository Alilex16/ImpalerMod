package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.HeavyJawPower;

public class HeavyJaw extends AbstractImpalerCard {
    public static final String ID = "HeavyJaw";
    public static final String NAME = "Heavy Jaw";
    public static final	String IMG = "cards/power/placeholderpower.png";
    public static final	String DESCRIPTION = "Bite cards damage is increased by !M! and Heal by 1.";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    private static final int POWER = 2;
    private static final int HEAL = 1;


    public HeavyJaw() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = POWER;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HeavyJawPower(p, this.magicNumber, HEAL), this.magicNumber)); // , HEAL
    }

    public AbstractCard makeCopy() {
        return new HeavyJaw();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            this.initializeDescription();
        }
    }
}
