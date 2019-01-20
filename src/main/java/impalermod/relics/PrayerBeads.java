package impalermod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import impalermod.powers.SpiritPower;

public class PrayerBeads extends AbstractImpalerRelic {
    public static final String ID = "PrayerBeads";
    private static final RelicTier TIER = RelicTier.RARE;
    private static final String IMG = "relics/PrayerBeads.png";
    private static final LandingSound SOUND = LandingSound.HEAVY;

    public PrayerBeads() {
        super(ID, IMG, TIER, SOUND);
        this.counter = 0;
    }

    public void atBattleStart() {
        if (this.counter != 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SpiritPower(AbstractDungeon.player, this.counter), this.counter));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, PrayerBeads.this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1];
    }


    public AbstractRelic makeCopy() {
        return new PrayerBeads();
    }
}
