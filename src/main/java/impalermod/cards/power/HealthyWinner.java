package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.HealthyWinnerPower;
import impalermod.relics.BleedingEnergy;

public class HealthyWinner extends AbstractImpalerCard {
    public static final String ID = "HealthyWinner";
    public static final String NAME = "Healthy Winner";
    public static final	String IMG = "cards/power/HealthyWinner.png";
    public static final	String DESCRIPTION = "Lose 14 HP. NL At the end of combat, gain !M! Max HP.";
    public static final	String DESCRIPTION_BLEEDING_ENERGY = "Lose 28 HP. NL At the end of combat, gain !M! Max HP.";

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 2;

    private static int SELF_DMG = 14;
    private static final int POWER = 2;
    private static final int UPGRADE_POWER = 1;


    public HealthyWinner() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
        this.CheckBleedingEnergy();
    }

    private void CheckBleedingEnergy()
    {
        if (BleedingEnergy.bleedingEnergyEquipped)
        {
            SELF_DMG = 28;
            this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
            this.initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HealthyWinnerPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new HealthyWinner();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_POWER);
            this.initializeDescription();
        }
    }
}
