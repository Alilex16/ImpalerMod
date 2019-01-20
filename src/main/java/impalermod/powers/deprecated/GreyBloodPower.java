package impalermod.powers.deprecated;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import impalermod.ImpalerMod;
import impalermod.powers.AbstractImpalerPower;

public class GreyBloodPower extends AbstractImpalerPower {
    public static final String POWER_ID = "GreyBloodPower";
    public static final String NAME = "Grey Blood";
    public static final String[] DESCRIPTIONS = new String[]{ "Instead of #yHeal, gain #yBlock. Instead of #yBlock, gain #yHeal.", "Heal and Block has been reversed"};
    public static final String IMG = "powers/skill/placeholderskill.png";
    //private int bonus;

    public GreyBloodPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        //this.bonus = bonus;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        this.isTurnBased = true;
        this.priority = 12;
    }

    @Override
    public int onHeal(int healAmount)
    {
        if (healAmount > 0.0F )
        {
            //healAmount += bonus;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, healAmount));

            healAmount = 0;
        }
        else
        {
            return 0;
        }

        return healAmount;
    }

    @Override
    public float modifyBlock(float blockAmount)
    {
        if (blockAmount > 0.0F )
        {
            //blockAmount += bonus;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, (int)blockAmount));

            blockAmount = 0;
        }
        else
        {
            return 0;
        }

        return blockAmount;
    }


    public void atEndOfRound() {
        if (this.amount == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "GreyBloodPower"));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "GreyBloodPower", 1));
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}