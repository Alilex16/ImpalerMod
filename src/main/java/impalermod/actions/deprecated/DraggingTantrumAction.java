package impalermod.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class DraggingTantrumAction extends AbstractGameAction {
    public int[] multiDamage;
    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageType damageTypeForTurn;
    private int selfDamage;
    private int energyOnUse = -1;

    public DraggingTantrumAction(AbstractPlayer p, AbstractMonster m, boolean upgraded, int[] multiDamage, DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse, int selfDamage) {
        this.multiDamage = multiDamage;
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
        this.selfDamage = selfDamage;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.p, this.p, this.selfDamage));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new DrawCardNextTurnPower(p, 1), 1));
            //++effect;
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                if (i == 0) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
                }

                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.p, new CleaveEffect(), 0.0F));
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.p, this.p, this.selfDamage));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.p, this.multiDamage, this.damageType, AttackEffect.NONE, true));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new DrawCardNextTurnPower(p, 1), 1));

            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
