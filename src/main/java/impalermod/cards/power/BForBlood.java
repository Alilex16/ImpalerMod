package impalermod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BForBloodPower;
import impalermod.powers.BloodshieldPower;

public class BForBlood extends AbstractImpalerCard {
    public static final String ID = "BForBlood";
    public static final String NAME = "B For Blood";
    public static final	String IMG = "cards/power/BForBlood.png";
    public static final	String DESCRIPTION = "Lose !M! HP. NL Attacks Heal for 50% of [unblocked] damage done. NL [Activates Bloodshield]";
    public static final	String DESCRIPTION_UPGRADED = "Lose !M! HP. NL Attacks Heal for 50% of [unblocked] damage done. NL Gain Block equal to Floor Level ( !B! ). NL [Activates Bloodshield]";
    private static final String[] DESCRIPTION_EXTENDED = new String[] {"You will pay for this ..."};

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int POOL = 0;
    private static final int COST = 3;

    private int POWER = AbstractDungeon.floorNum;
    private int keep1HP;


    public BForBlood() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseBlock = POWER;
        this.baseMagicNumber = keep1HP;
        this.magicNumber = this.baseMagicNumber;
    }


    @Override
    public void applyPowers() {
        int playerCurrentHP = AbstractDungeon.player.currentHealth;
        keep1HP = playerCurrentHP - 1;

        this.baseBlock = POWER;
        this.baseMagicNumber = keep1HP;
        this.magicNumber = this.baseMagicNumber;

        super.applyPowers();
        this.setDescription(true);
    }

    private void setDescription(boolean addExtended) {
        this.rawDescription = DESCRIPTION;
        if (upgraded) {
            this.rawDescription = DESCRIPTION_UPGRADED;
        }
        this.initializeDescription();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX, p.dialogY, 3.0F, DESCRIPTION_EXTENDED[0], true));

        int playerCurrentHP = AbstractDungeon.player.currentHealth;
        keep1HP = playerCurrentHP - 1;

        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, keep1HP));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BForBloodPower(0), 0));
        BloodshieldPower.isBloodshielded = true;

        if (upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.baseBlock));
            BloodshieldPower.bloodShieldAmount += this.baseBlock;
        }
    }

    public AbstractCard makeCopy() {
        return new BForBlood();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = DESCRIPTION_UPGRADED;
            this.initializeDescription();
        }
    }
}
