package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import impalermod.cards.AbstractImpalerCard;

public class SpiritualSmash extends AbstractImpalerCard {
	public static final String ID = "SpiritualSmash";
	public static final	String NAME = "Spiritual Smash";
	public static final	String IMG = "cards/attack/SpiritualSmash.png";
	public static final	String DESCRIPTION = "Deal !D! damage. NL Each Spirit increases the damage by !M!.";

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 1;

	private static final int POWER = 7;
    private static final int UPGRADED_POWER = 3;

    private static final int POWER_PER_SPIRIT = 2;
    private static final int UPGRADE_POWER_PER_SPIRIT = 1;


	public SpiritualSmash() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseDamage = POWER;
        this.baseMagicNumber = POWER_PER_SPIRIT;
        this.magicNumber = this.baseMagicNumber;
	}


	public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower("Spirit"))
        {
            this.baseDamage = POWER + this.magicNumber * AbstractDungeon.player.getPower("Spirit").amount;
        }
        else
        {
            this.baseDamage = POWER;
        }

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
	}


    @Override
    public void applyPowers() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower("Spirit"))
        {
            this.baseDamage = POWER + this.magicNumber * AbstractDungeon.player.getPower("Spirit").amount;
        }
        else
        {
            this.baseDamage = POWER;
        }

        super.applyPowers();
        this.setDescription(true);
    }

    private void setDescription(boolean addExtended) {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

	public AbstractCard makeCopy() {
		return new SpiritualSmash();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADED_POWER);
            this.upgradeMagicNumber(UPGRADE_POWER_PER_SPIRIT);
		}
	}
	
}
