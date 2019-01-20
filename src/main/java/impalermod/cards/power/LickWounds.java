package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.LickWoundsPower;

public class LickWounds extends AbstractImpalerCard {
    public static final String ID = "LickWounds";
    public static final String NAME = "Lick Wounds";
    public static final	String IMG = "cards/power/placeholderpower.png";
    public static final	String DESCRIPTION = "At the end of your turn, Heal !M! HP for every Bleeding enemy.";
    public static final	String DESCRIPTION_UPGRADED = "Innate. NL At the end of your turn, Heal !M! HP for every Bleeding enemy.";

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 2;

    private static final int POWER = 3;

    public LickWounds() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LickWoundsPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new LickWounds();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
        }
    }
}
