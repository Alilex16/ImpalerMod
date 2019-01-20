package impalermod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import impalermod.ImpalerMod;
import impalermod.cards.curse.UndeadCurse;

public class BloodshieldPower extends AbstractImpalerPower implements PostDrawSubscriber, PostBattleSubscriber,
        PostDungeonInitializeSubscriber {
    public static String POWER_ID = "BloodshieldPower";
    public static String NAME = "Bloodshield";
    public static final String DESCRIPTION = "You can gain Bloodshield, which is gained by excess Healing.";
    public static final String[] DESCRIPTIONS = new String[]{"You can gain #yBloodshield, which is gained by excess #yHealing."};
    public static final String IMG = "powers/BloodShieldSmall.png";
    public static boolean isBloodshielded;
    public static int bloodShieldAmount;

    public BloodshieldPower(AbstractCreature owner) { //, int amount) { // , int amount
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        //this.amount = amount;
        this.isTurnBased = false;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.priority = 2;
        this.updateDescription();
    }


    /*
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        //super.onApplyPower(power, target, source);
        //if (power.type == AbstractPower.PowerType.BUFF && !power.ID.equals("Shackled") && source != owner && target == owner) {

            //if (AbstractDungeon.player.hasRelic("Condenser"))
            //{
                //AbstractDungeon.player.getRelic("Condenser").flash();
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new SpiritPower(owner, 1), 1));
            //}
        //}

        if (power.type == AbstractPower.PowerType.DEBUFF && power.ID.equals("Bleeding") && source == owner && target != owner && !target.hasPower("Artifact"))
        {
            if (AbstractDungeon.player.hasRelic("LizardSkull"))
            {
                AbstractDungeon.player.getRelic("LizardSkull").flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new BleedingPower(target, 1, true), 1));
            }
        }
    }
    */


    @Override
    public void onInitialApplication() {
        BaseMod.subscribe(this);
    }

    @Override
    public void receivePostBattle(AbstractRoom arg0) {
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostDungeonInitialize() {
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostDraw(AbstractCard c) {    }

    @Override
    public int onHeal(final int healAmount) {
        int currentHealth = AbstractDungeon.player.currentHealth;
        int maxHealth = AbstractDungeon.player.maxHealth;
        int healthDifference = maxHealth - currentHealth;
        int restHealing = (healAmount - healthDifference) * 2; // * 2 = to gain 2 block per HP gain while full HP
        int blockGained;

        if (UndeadCurse.undeadCursed)
        {
            return 0;
        }

        if (healAmount >= healthDifference)
        {
            if (AbstractDungeon.player.hasRelic("CrimsonAmulet"))
            {
                AbstractDungeon.player.getRelic("CrimsonAmulet").flash();
                blockGained = restHealing * 3 / 2;
                //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, blockGained));
                //bloodShieldAmount += blockGained;
            }
            else
            {
                blockGained = restHealing;
                //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, blockGained));
                //bloodShieldAmount += blockGained;
            }

            if (blockGained > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, blockGained));
                bloodShieldAmount += blockGained;
                isBloodshielded = true;
                AbstractDungeon.player.getRelic("Bloodshield").flash();
            }

            return healthDifference;
        }
        else
        {
            return healAmount;
        }
    }


    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        if(isBloodshielded && !AbstractDungeon.player.hasRelic("EverlastingFluid"))
        {
            bloodShieldAmount = 0;
            isBloodshielded = false;
        }
    }


    /*
    //@Override
    public void onBlock()
    {


        // decrease bloodShieldAmount by damage blocked.
    }

        public void onBlockBroken(AbstractCreature m) {}

    */



    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}
