package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BraveSoulPower;

public class BraveSoul extends AbstractImpalerCard {
    public static final String ID = "BraveSoul";
    public static final	String NAME = "Brave Soul";
    public static final	String IMG = "cards/power/BraveSoul.png";
    public static final	String DESCRIPTION = "Innate. NL At the start of your turn, become Vulnerable and draw !M! card."; // gain 1 = become
    public static final	String DESCRIPTION_UPGRADED = "Innate. NL At the start of your turn, become Vulnerable and draw !M! cards.";

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 0;

    private static final int POWER = 1;


    public BraveSoul() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BraveSoulPower(p, magicNumber), magicNumber));
    }

    public AbstractCard makeCopy() {
        return new BraveSoul();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.isInnate = true;
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
        }
    }


}
