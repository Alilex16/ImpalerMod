package impalermod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import impalermod.powers.BleedingPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheBloodborne extends AbstractImpalerRelic {
    private static final Logger logger = LogManager.getLogger(TheBloodborne.class.getName());
    public static final String ID = "TheBloodborne";
    private static final RelicTier TIER = RelicTier.RARE;
    private static final String IMG = "relics/TheBloodborne.png";
    private static final LandingSound SOUND = LandingSound.CLINK;

    public TheBloodborne() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onMonsterDeath(AbstractMonster m) {
        if (m.hasPower("Bleeding")) {
            int amount = m.getPower("Bleeding").amount;
            AbstractMonster target = AbstractDungeon.getRandomMonster(m);
            if (target != null) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, TheBloodborne.this));

                if (AbstractDungeon.player.hasRelic("LizardSkull"))
                {
                    AbstractDungeon.player.getRelic("LizardSkull").flash();
                    ++amount;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new BleedingPower(target, amount, true), amount));
            } else {
                logger.info("no target for the bloodborne");
            }
        }
    }

    public AbstractRelic makeCopy() {
        return new TheBloodborne();
    }
}
