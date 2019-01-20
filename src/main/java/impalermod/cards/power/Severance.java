package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.SeverancePower;
import impalermod.relics.BleedingEnergy;

public class Severance extends AbstractImpalerCard {
    public static final String ID = "Severance";
    public static final	String NAME = "Severance";
    public static final	String IMG = "cards/power/Severance.png";
    public static final String DESCRIPTION = "Lose 8 HP. NL Whenever you play a Bloody card, gain [R].";
    public static final	String DESCRIPTION_UPGRADED = "Whenever you play a Bloody card, gain [R].";
    public static final String DESCRIPTION_BLEEDING_ENERGY = "Lose 16 HP. NL Whenever you play a Bloody card, gain [R].";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 2;

    private static final int POWER = 4;
    private static final int UPGRADE_POWER = 2;

    private static int SELF_DMG = 8;
    private static final int UPGRADE_SELF_DMG = -8;


    public Severance() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.CheckBleedingEnergy();
        //this.baseBlock = POWER;
        this.baseMagicNumber = SELF_DMG;
        this.magicNumber = this.baseMagicNumber;
    }

    private void CheckBleedingEnergy()
    {
        if (BleedingEnergy.bleedingEnergyEquipped)
        {
            if (!upgraded)
            {
                SELF_DMG = 16;
                this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
                this.initializeDescription();
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded){
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SeverancePower(p, 1), 1));
    }

    public AbstractCard makeCopy() {
        return new Severance();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(UPGRADE_POWER);
            this.upgradeMagicNumber(UPGRADE_SELF_DMG);
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
        }
    }
}
