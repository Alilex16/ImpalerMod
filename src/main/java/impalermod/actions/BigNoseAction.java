package impalermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import impalermod.relics.BigNose;

public class BigNoseAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractRelic relic;
    public int damage;
    public int times = -1;

    public BigNoseAction(AbstractPlayer p, AbstractRelic relic) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.relic = relic;
    }

    public void update() {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            int currentHealth = monster.currentHealth;
            int maxHealth = monster.maxHealth;
            boolean isHalfDead = false;

            if (currentHealth <= (maxHealth / 2))
            {
                isHalfDead = true;
            }

            if (!monster.isDeadOrEscaped() && isHalfDead && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) { // monster.isBloodied

                //AbstractDungeon.player.getRelic("BigNose").flash();
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(monster, this.relic)); // addToBottom?
                //this.relic.beginPulse();
                this.relic.beginLongPulse(); // ??

                //AbstractDungeon.player.getRelic("BigNose").beginPulse();

                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, POWER), POWER));
                AbstractDungeon.player.addPower(new StrengthPower(p, 1));

                isHalfDead = false;
            }
        }

        this.isDone = true;
    }
}