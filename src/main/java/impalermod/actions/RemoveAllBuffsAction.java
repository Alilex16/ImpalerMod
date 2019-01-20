package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import java.util.Iterator;

public class RemoveAllBuffsAction extends AbstractGameAction {
    private boolean buffsOnly;
    private AbstractCreature c;

    public RemoveAllBuffsAction(AbstractCreature c, boolean buffsOnly) {
        this.buffsOnly = buffsOnly;
        this.c = c;
        this.duration = 0.5F;
    }

    public void update() {
        Iterator var1 = this.c.powers.iterator();

        while(true) {
            AbstractPower p;
            do {
                if (!var1.hasNext()) {
                    this.isDone = true;
                    return;
                }

                p = (AbstractPower)var1.next();
            } while(p.type != PowerType.BUFF && this.buffsOnly);

            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.c, this.c, p.ID));
        }
    }
}