package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.PleasureForPainPower;
import impalermod.relics.BleedingEnergy;

public class PleasureForPain extends AbstractImpalerCard {
    public static final String ID = "PleasureForPain";
    public static final	String NAME = "Pleasure For Pain";
    public static final	String IMG = "cards/power/PleasureForPain.png";
    public static final	String DESCRIPTION = "Lose !M! HP. NL Every time you Heal, deal either 1 damage to all enemies or 3 damage to a random enemy.";

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 1;
    private static final int UPGRADE_COSTS = 0;

    private static int SELF_DMG = 2;
    private static final int POWER_ALL = 1;
    private static final int POWER_RANDOM = 3;


    public PleasureForPain() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.CheckBleedingEnergy();
        this.baseMagicNumber = this.magicNumber = SELF_DMG;
    }

    private void CheckBleedingEnergy()
    {
        if (BleedingEnergy.bleedingEnergyEquipped)
        {
            SELF_DMG = 4;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PleasureForPainPower(p, POWER_ALL, POWER_RANDOM), 1));
    }

    public AbstractCard makeCopy() {
        return new PleasureForPain();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COSTS);
        }
    }
}
