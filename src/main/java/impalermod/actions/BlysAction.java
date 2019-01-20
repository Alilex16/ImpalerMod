package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import impalermod.powers.BleedingPower;
import impalermod.powers.HastePower;

public class BlysAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int numTimes;
    private AbstractCreature owner;
    private int bleedingAmount = 1;

    public BlysAction(AbstractCreature owner, int numTimes) {
        this.actionType = ActionType.DEBUFF;
        this.attackEffect = AttackEffect.NONE;
        this.duration = 0.01F;
        this.owner = owner;
        this.numTimes = numTimes;
    }

    public void update() {
        int effect = numTimes;
        if (this.numTimes != -1) {
            effect = this.numTimes;
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {

                AbstractMonster randomMonster = AbstractDungeon.getRandomMonster();


                if (AbstractDungeon.player.hasRelic("LizardSkull"))
                {
                    AbstractDungeon.player.getRelic("LizardSkull").flash();
                    ++this.bleedingAmount;
                }

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(randomMonster, owner, new BleedingPower(randomMonster, this.bleedingAmount, true), this.bleedingAmount, true, attackEffect));
            }
        }

        this.isDone = true;
    }
}
