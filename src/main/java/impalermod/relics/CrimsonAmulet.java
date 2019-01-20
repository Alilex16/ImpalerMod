package impalermod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CrimsonAmulet extends AbstractImpalerRelic {
    public static final String ID = "CrimsonAmulet";
    private static final RelicTier TIER = RelicTier.SPECIAL;
    private static final String IMG = "relics/CrimsonAmulet.png";
    private static final LandingSound SOUND = LandingSound.FLAT;

    public CrimsonAmulet() {
        super(ID, IMG, TIER, SOUND);
        updateDescription();
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, CrimsonAmulet.this));
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0]);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CrimsonAmulet();
    }
}
