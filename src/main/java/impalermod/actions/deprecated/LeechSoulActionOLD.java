package impalermod.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.attack.LeechLife;
import impalermod.cards.skill.LeechDexterity;
import impalermod.cards.skill.LeechSpeed;
import impalermod.powers.HastePower;

public class LeechSoulActionOLD extends AbstractGameAction {
    private boolean upgraded;
    public int damage;
    public int times;
    public AbstractCard cardPreviewTooltip;
    private int tooltipTimer = 0;

    public LeechSoulActionOLD(boolean upgraded, AbstractCard cardPreviewTooltip, int times) {
        this.times = times;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.cardPreviewTooltip = cardPreviewTooltip;
    }

    public void update() {
        int effect = times;
        if (this.times != -1) {
            effect = this.times;
        }

        if (this.upgraded)
        {
            //++effect;
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {

                //if (this.upgraded)

                if (tooltipTimer == 0)
                {
                    cardPreviewTooltip = new LeechSpeed();
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
                    tooltipTimer = 1;
                }
                else if (tooltipTimer == 1)
                {
                    cardPreviewTooltip = new LeechLife();
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
                    tooltipTimer = 2;
                }
                else if (tooltipTimer == 2)
                {
                    cardPreviewTooltip = new LeechDexterity();
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
                    tooltipTimer = 0;
                }
            }
        }

        this.isDone = true;
    }
}