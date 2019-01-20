package impalermod.cards.attack;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import impalermod.actions.VitalityStrikeAction;
import impalermod.cards.AbstractImpalerCard;

public class VitalityStrike extends AbstractImpalerCard {
    public static final String ID = "VitalityStrike";
    public static final	String NAME = "Vitality Strike";
    public static final	String IMG = "cards/attack/VitalityStrike.png";
    public static final	String DESCRIPTION = "Deal !D! damage X times. NL Gain !M! Regeneration X times.";
    public static final	String DESCRIPTION_UPGRADED = "Deal !D! damage X+1 times. NL Gain !M! Regeneration X+1 times.";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int POOL = 0;
    private static final int COST = -1;

    private static final int POWER = 4;
    private static final int UPGRADE_POWER = 1;
    private static final int REGENERATION_AMOUNT = 1;

    public VitalityStrike() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseDamage = POWER;
        this.baseMagicNumber = this.magicNumber = REGENERATION_AMOUNT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        AbstractDungeon.actionManager.addToBottom(new VitalityStrikeAction(p, m, this.upgraded, this.damage, this.damageTypeForTurn ,this.freeToPlayOnce, this.energyOnUse));
    }

    public AbstractCard makeCopy() {
        return new VitalityStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_POWER);
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
        }

    }

}
