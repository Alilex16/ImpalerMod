package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.TacticalStimulationPower;

public class TacticalStimulation extends AbstractImpalerCard {
    public static final String ID = "TacticalStimulation";
    public static final	String NAME = "Tactical Stimulation";
    public static final	String IMG = "cards/power/TacticalStimulation.png";
    public static final String DESCRIPTION = "Whenever a card is Exhausted, heal !M! HP.";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 1;
    private static final int UPGRADE_COSTS = 0;

    private static final int POWER = 1;

    public TacticalStimulation() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = POWER;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TacticalStimulationPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new TacticalStimulation();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COSTS);
            //this.initializeDescription();
        }
    }
}
