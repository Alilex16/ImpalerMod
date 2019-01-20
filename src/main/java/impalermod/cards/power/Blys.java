package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BlysPower;

public class Blys extends AbstractImpalerCard {
    public static final String ID = "Blys";
    public static final String NAME = "Blys";
    public static final	String IMG = "cards/power/Blys.png";
    public static final	String DESCRIPTION = "At the start of your turn, apply 1 Bleeding to !M! random enemy.";
    public static final	String DESCRIPTION_UPGRADED = "At the start of your turn, apply 1 Bleeding to !M! random enemies.";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 1;

    private static final int POWER = 1;
    private static final int UPGRADE_POWER = 1;

    public Blys() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BlysPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Blys();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_POWER);
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
        }
    }
}
