package impalermod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import impalermod.powers.SpiritPower;

public class Sending extends AbstractImpalerRelic {
    public static final String ID = "Sending";
    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final String IMG = "relics/Sending.png";
    private static final LandingSound SOUND = LandingSound.HEAVY;

    public Sending() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth <= 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !m.hasPower("Minion") && !m.halfDead && m.isDying) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SpiritPower(AbstractDungeon.player, 1), 1));
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new Sending();
    }
}