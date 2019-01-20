package impalermod.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RecycleBinAction extends AbstractGameAction {
    private AbstractPlayer p;
    public int damage;
    public int times = -1;

    public RecycleBinAction(AbstractPlayer p, int times) {
        this.p = p;
        this.times = times;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        int effect = times;
        if (this.times != -1) {
            effect = this.times;
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {

                AbstractDungeon.player.getRelic("RecycleBin").flash();
                AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 1));

                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new HastePower(p, 1), 1));
            }
        }

        this.isDone = true;
    }
}
