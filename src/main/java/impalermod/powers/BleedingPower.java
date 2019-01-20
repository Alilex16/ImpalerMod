package impalermod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import impalermod.ImpalerMod;
import impalermod.relics.Haemophilia;

public class BleedingPower extends AbstractPower implements HealthBarRenderPower {
    public static final String POWER_ID = "Bleeding";
    public static final String NAME = "Bleeding";
    public static final String[] DESCRIPTIONS = new String[]{ "Lose #b!D! HP at the end of the turn."};
    public static final String IMG = "powers/BleedingSmall.png";
    private boolean justApplied = false;

    public BleedingPower(AbstractCreature owner, int amount, boolean isSourcePlayer) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount; // amount of stacks
        updateDescription();
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        if (AbstractDungeon.actionManager.turnHasEnded && isSourcePlayer) {
            this.justApplied = true;
        }
        this.priority = 99;
    }

    //@Override// ???
    //public float modifyBlock(float blockAmount) {
    //    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
    //    return blockAmount * 3 / 4;
    //}

    //@Override // ???
    //public void onGainedBlock(float blockAmount){
    //    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
    //    owner.currentBlock = owner.currentBlock * 3/4;
    //}

    @Override
    public void atStartOfTurn() {
        //if (!isPlayer) {
            if (this.justApplied) {
                this.justApplied = false;
            } else {
                if (this.amount == 0) {
                    if (AbstractDungeon.player.hasRelic(Haemophilia.ID)) {
                        AbstractDungeon.player.getRelic(Haemophilia.ID).flash();
                    }
                    else
                    {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Bleeding"));
                    }
                } else {
                    int bonus = AbstractDungeon.player.getPower(PainfulWoundsPower.POWER_ID) == null?0:AbstractDungeon.player.getPower(PainfulWoundsPower.POWER_ID).amount;
                    int damage = 5 + bonus;
                    //if (AbstractDungeon.player.hasRelic("Crumpled Paper")) {
                    //    damage *= 2;
                    //if(AbstractDungeon.player.hasPower("PainfulWounds")){
                    //    damage += 5;
                    //}

//                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -damage), -damage));
//                    if (owner != null && !owner.hasPower("Artifact")) {
//                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new GainStrengthPower(owner, damage), damage));
//                    }
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner,
                            new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.HP_LOSS))); // **
                     //new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));

                    //AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.5F)); // **


                    if (AbstractDungeon.player.hasRelic(Haemophilia.ID)) {
                        AbstractDungeon.player.getRelic(Haemophilia.ID).flash();
                    }
                    else
                    {
                        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Bleeding", 1));
                    }

                    this.owner.damageFlash = true; // ??
                    this.owner.damageFlashFrames = 4; // ??
                }

            }
        //}
    }
    //public void applyPowers()

    private int bleedAmountVisual;

    @Override // this will not get updated if target is Bleeding and playing PainfulWounds afterwards.
    public void updateDescription() {
        int bonus = AbstractDungeon.player.getPower(PainfulWoundsPower.POWER_ID) == null?0:AbstractDungeon.player.getPower(PainfulWoundsPower.POWER_ID).amount;
        int damage = 5 + bonus;
        //if(AbstractDungeon.player.hasPower("PainfulWounds")){
        //    damage += 5;
        //}

        bleedAmountVisual = damage;

        if (this.amount == 1) {
            this.description = "Lose #b" + damage + " #yHP at the end of the turn.";
        } else {
            this.description = "Lose #b" + damage + " #yHP at the end of each the turn.";
        }
    }

    @Override
    public int getHealthBarAmount() {
        return bleedAmountVisual;
    } // this.amount

    @Override
    public Color getColor() {
        return Color.RED;
    }
}