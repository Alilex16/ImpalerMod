package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.powers.HastePower;

public class FrenzyAction extends AbstractGameAction {
    private boolean upgraded;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageType damageTypeForTurn;
    public int damage;
    public int times = -1;

    public FrenzyAction(AbstractPlayer p, AbstractMonster m, boolean upgraded, DamageType damageTypeForTurn, int damage, int times) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.times = times;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
    }

    public void update() {
        int effect = times;
        if (this.times != -1) {
            effect = this.times;
        }


        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.upgraded)
        {
            //++effect;
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {

                if (this.upgraded)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new HastePower(p, 1), 1));
                }

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new HastePower(p, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AttackEffect.NONE, true));
            }
        }

        this.isDone = true;
    }
}
