package impalermod.actions.deprecated;

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

public class UndeadCurseAction extends AbstractGameAction {
    private AbstractPlayer p;
    private DamageType damageTypeForTurn;
    public int damage;

    public UndeadCurseAction(AbstractPlayer p, DamageType damageTypeForTurn, int damage) {
        this.p = p;
        this.damage = damage;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
    }


    public void update() {
        AbstractDungeon.actionManager.addToTop(new DamageAction(p, new DamageInfo(p, this.damage, this.damageTypeForTurn), AttackEffect.NONE, true));

        this.isDone = true;
    }
}
