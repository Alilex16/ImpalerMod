package impalermod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import impalermod.ImpalerMod;

public class HastePower extends AbstractPower {
    public static final String POWER_ID = "Haste";
    public static final String NAME = "Haste";
    private String[] DESCRIPTIONS;
    public static final String IMG = "powers/HasteSmall.png";

    public HastePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount; // amount of stacks
        this.DESCRIPTIONS = new String[] {
                "For each card played this turn, deal 10% more damage.",
                " NL Damage is increased by #b", "%.",
                " NL Attacks currently deal #b", "% more damage."
        };
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.updateDescription();
        //this.loadRegion("time");
    }

    public void atEndOfRound() {
            this.amount = 0;
            this.updateDescription();
        }

        public void updateDescription() {
            this.description = DESCRIPTIONS[0];
            if (this.amount != 0) {
                //this.description = this.description + DESCRIPTIONS[1];
                //this.description = this.description + DESCRIPTIONS[1] + (10 * this.amount) + DESCRIPTIONS[2];
                this.description = this.description + DESCRIPTIONS[3] + (10 * this.amount) + DESCRIPTIONS[4];
            }
        }

        public void onAfterUseCard(AbstractCard card, UseCardAction action) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new impalermod.powers.HastePower(this.owner, 1), 1));
        }

        public float atDamageGive(float damage, DamageInfo.DamageType type) {
            return type == DamageInfo.DamageType.NORMAL ? damage * (1.0F + (float)this.amount * 0.1F) : damage;
        }

    }