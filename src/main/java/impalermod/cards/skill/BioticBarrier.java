package impalermod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.BioticBarrierPower;
import impalermod.powers.BloodshieldPower;

public class BioticBarrier extends AbstractImpalerCard {
    public static final String ID = "BioticBarrier";
    public static final	String NAME = "Biotic Barrier";
    public static final	String IMG = "cards/skill/placeholderskill.png";
    public static final	String DESCRIPTION = "Gain !B! Block. NL Whenever you are attacked this turn, apply !M! Bleeding to the attacker. NL [Activates Bloodshield]";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int POOL = 1;
    private static final int COST = 2;

    private static final int BLOCK = 12;
    private static final int UPGRADE_BLOCK = 4;
    private static final int APPLY_BLEEDING = 1;
    private static final int UPGRADE_APPLY_BLEEDING = 1;

    public BioticBarrier() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = APPLY_BLEEDING;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        BloodshieldPower.bloodShieldAmount += this.block;
        BloodshieldPower.isBloodshielded = true;

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BioticBarrierPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new BioticBarrier();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
            this.upgradeMagicNumber(UPGRADE_APPLY_BLEEDING);
        }
    }
}
