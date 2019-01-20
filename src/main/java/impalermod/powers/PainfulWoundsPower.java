package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.ImpalerMod;

public class PainfulWoundsPower extends AbstractImpalerPower {
    public static final String POWER_ID = "PainfulWoundsPower";
    public static final String NAME = "Painful Wounds";
    public static final String[] DESCRIPTIONS = new String[]{"All #yBleeding damage is increased by #b","."};
    public static final String IMG = "powers/PainfulWoundsSmall.png";

    public PainfulWoundsPower(AbstractCreature owner, int bleedAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.amount = bleedAmt;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped() && monster.hasPower(BleedingPower.POWER_ID)) {
                //AbstractDungeon.actionManager.addToTop(new DamageAction(this.owner,
                //        new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.HP_LOSS),
                //        AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

}

