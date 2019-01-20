package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.SafetyDrawPower;

public class SafetyDraw extends AbstractImpalerCard {
    public static final String ID = "SafetyDraw";
    public static final String NAME = "Safety Draw";
    public static final	String IMG = "cards/power/SafetyDraw.png";
    public static final	String DESCRIPTION = "If not Bloodshielded, draw !M! extra card next turn.";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int POWER = 1;

    public SafetyDraw() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SafetyDrawPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new SafetyDraw();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
            this.initializeDescription();
        }
    }
}
