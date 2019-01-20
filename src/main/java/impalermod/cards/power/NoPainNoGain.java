package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.NoPainNoGainPower;

public class NoPainNoGain extends AbstractImpalerCard {
    public static final String ID = "NoPainNoGain";
    public static final String NAME = "No Pain No Gain";
    public static final	String IMG = "cards/power/NoPainNoGain.png";
    public static final	String DESCRIPTION = "Whenever you apply Bleeding, gain !M! HP.";
    public static final	String DESCRIPTION_UPGRADED = "Innate. NL Whenever you apply Bleeding, gain !M! HP.";

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 1;

    private static final int POWER = 2;
    //private static final int UPGRADE_POWER = 0;

    public NoPainNoGain() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NoPainNoGainPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new NoPainNoGain();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            //this.upgradeMagicNumber(UPGRADE_POWER);
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
        }
    }
}
