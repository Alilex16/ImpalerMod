package impalermod.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RiteOfPassageAction extends AbstractGameAction {
    private int amount;

    public RiteOfPassageAction(int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.player.heal(AbstractDungeon.player.exhaustPile.size() / this.amount);
        }

        this.tickDuration();
    }
}
