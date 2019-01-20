package impalermod.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class RecycleBin extends AbstractImpalerRelic {
    public static final String ID = "RecycleBin";
    private static final RelicTier TIER = RelicTier.COMMON;
    private static final String IMG = "relics/RecycleBin.png";
    private static final LandingSound SOUND = LandingSound.FLAT;

    public RecycleBin() {
        super(ID, IMG, TIER, SOUND);
    }


    public void onPlayerEndTurn()
    {
        int energyLeft = EnergyPanel.totalCount;

        if (energyLeft > 0)
        {
            AbstractDungeon.player.getRelic("RecycleBin").flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, RecycleBin.this));

            //AbstractDungeon.actionManager.addToBottom(new RecycleBinAction(AbstractDungeon.player, energyLeft));
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, energyLeft));
            AbstractDungeon.player.energy.use(energyLeft);
        }
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }


    public AbstractRelic makeCopy() {
        return new RecycleBin();
    }
}
