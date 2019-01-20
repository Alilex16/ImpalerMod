package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.PowerOfBloodPower;
import impalermod.relics.BleedingEnergy;

public class PowerOfBlood extends AbstractImpalerCard {
    public static final String ID = "PowerOfBlood";
    public static final String NAME = "Power Of Blood";
    public static final	String IMG = "cards/power/PowerOfBlood.png";
    public static final	String DESCRIPTION = "Ethereal. NL Lose 6 HP. NL If you only played Attacks this turn, gain !M! Strength at the end of your turn.";
    public static final	String DESCRIPTION_BLEEDING_ENERGY = "Ethereal. NL Lose 12 HP. NL If you only played Attacks this turn, gain !M! Strength at the end of your turn.";

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 1;

    private static int SELF_DMG = 6;
    private static final int POWER = 2;
    private static final int UPGRADE_POWER = 1;


    public PowerOfBlood() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseMagicNumber = this.magicNumber = POWER;
        this.isEthereal = true;
        this.CheckBleedingEnergy();
    }

    private void CheckBleedingEnergy()
    {
        if (BleedingEnergy.bleedingEnergyEquipped)
        {
            SELF_DMG = 12;
            this.rawDescription = DESCRIPTION_BLEEDING_ENERGY;
            this.initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.5F));
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, SELF_DMG));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PowerOfBloodPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new PowerOfBlood();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_POWER);
            this.initializeDescription();
        }
    }
}
