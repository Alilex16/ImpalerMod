package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.PumpItUpPower;

public class PumpItUp extends AbstractImpalerCard {
    public static final String ID = "PumpItUp";
    public static final	String NAME = "Pump It Up";
    public static final	String IMG = "cards/power/PumpItUp.png";
    public static final	String DESCRIPTION = "While your HP is at or below 25%, your Healing is increased by 50%.";

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 2;
    private static final int UPGRADE_COSTS = 1;

    private static final int POWER = 1;


    public PumpItUp() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PumpItUpPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new PumpItUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COSTS);
        }
    }


}
