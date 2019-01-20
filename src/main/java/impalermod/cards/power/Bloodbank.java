package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import impalermod.cards.AbstractImpalerCard;
import impalermod.cards.skill.BloodPack;
import impalermod.powers.BloodbankPower;

public class Bloodbank extends AbstractImpalerCard {
    public static final String ID = "Bloodbank";
    public static final String NAME = "Bloodbank";
    public static final	String IMG = "cards/power/placeholderpower.png";
    public static final	String DESCRIPTION = "Gain !M! Draw-Reduction for 3 turns. NL Whenever you draw a Status card, replace it with a Blood Pack.";
    public static final	String DESCRIPTION_UPGRADED = "Gain !M! Draw-Reduction for 3 turns. NL Whenever you draw a Status card, replace it with an Upgraded Blood Pack.";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 1;

    private static final int POWER = 1;


    public Bloodbank() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
        cardPreviewTooltip = new BloodPack();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawReductionPower(p, 3), 3));

        this.initializeDescription(); // useless?? possible Revange ApplyPower can help

        if (upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BloodbankPower(p, this.magicNumber, true), this.magicNumber));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BloodbankPower(p, this.magicNumber, false), this.magicNumber));
        }
    }

    public AbstractCard makeCopy() {
        return new Bloodbank();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(-1);
            this.rawDescription = DESCRIPTION_UPGRADED;
            //this.initializeDescription();
            cardPreviewTooltip.upgrade();
            this.initializeDescription();
        }
    }
}
