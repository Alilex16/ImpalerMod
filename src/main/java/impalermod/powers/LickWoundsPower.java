package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.ImpalerMod;

public class LickWoundsPower extends AbstractImpalerPower {
    public static String POWER_ID = "LickWoundsPower";
    public static String NAME = "Lick Wounds";
    public static final String DESCRIPTION = "At the end of your turn, gain 3 HP for every Bleeding enemy.";
    public static final String[] DESCRIPTIONS = new String[]{ "At the end of your turn, gain #b" , " HP for every #yBleeding enemy."};
    public static final String IMG = "powers/LickWoundsSmall.png";

    public LickWoundsPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = false;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped() && monster.hasPower(BleedingPower.POWER_ID)) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}