package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.SpiritPower;

public class InnerPower extends AbstractImpalerCard {
    public static final String ID = "InnerPower";
    public static final	String NAME = "Inner Power";
    public static final	String IMG = "cards/power/InnerPower.png";
    public static final	String DESCRIPTION = "Gain !M! Spirit.";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 1;
    private static final int COST = 2;

    private static final int POWER = 1;
    private static final int UPGRADED_POWER = 1;

    public InnerPower() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.magicNumber = this.baseMagicNumber = POWER;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new InflameEffect(p), 1.0F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpiritPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new InnerPower();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_POWER);
            this.initializeDescription();
        }
    }
}