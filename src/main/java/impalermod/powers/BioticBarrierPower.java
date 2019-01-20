package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BioticBarrierPower extends AbstractImpalerPower {
    private static final Logger logger = LogManager.getLogger(BioticBarrierPower.class.getName());
    public static String POWER_ID = "BioticBarrierPower";
    public static String NAME = "Biotic Barrier";
    public static final String[] DESCRIPTIONS = new String[]{"Whenever you are attacked this turn, apply #b" , " #yBleeding to the attacker."};
    public static final String IMG = "powers/BioticBarrierSmall.png";

    public BioticBarrierPower(AbstractCreature owner, int bleedAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = bleedAmount;
        this.updateDescription();
    }


    public void stackPower(int stackAmount) {
        if (this.amount == -1) {
            logger.info(this.name + " does not stack");
        } else {
            this.fontScale = 8.0F;
            this.amount += stackAmount;
            this.updateDescription();
        }
    }


    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            this.flash();

            if (AbstractDungeon.player.hasRelic("LizardSkull"))
            {
                AbstractDungeon.player.getRelic("LizardSkull").flash();
                ++this.amount;
            }

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner, AbstractDungeon.player, new BleedingPower(info.owner, this.amount, true), this.amount, true, AbstractGameAction.AttackEffect.FIRE));
        }
        return damageAmount;
    }


    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "BioticBarrierPower"));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
