package impalermod.cards.attack;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import impalermod.cards.AbstractImpalerCard;

public class Bite_Impaler extends AbstractImpalerCard {
	public static final String ID = "Bite_Impaler";
	public static final	String NAME = "Bite";
	public static final	String IMG = "cards/attack/Bite.png";
	public static final	String DESCRIPTION = "Deal !D! damage. NL Heal !M! HP.";

	private static final CardRarity RARITY = CardRarity.BASIC;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 0;
	private static final int COST = 1;

	private static final int POWER = 5;
	private static final int HEAL_AMT = 1;
	private static final int UPGRADE_POWER = 2;
	private static final int UPGRADE_HEAL = 1;

	public Bite_Impaler() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseMagicNumber = HEAL_AMT;

		basemod.helpers.CardTags.addTags(this, BaseModCardTags.BASIC_STRIKE);

		if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower("HeavyJawPower")) {
            this.baseDamage = POWER + 2 * AbstractDungeon.player.getPower("HeavyJawPower").amount;
            this.magicNumber = this.baseMagicNumber + AbstractDungeon.player.getPower("HeavyJawPower").amount;
		} else {
            this.baseDamage = POWER;
            this.magicNumber = this.baseMagicNumber;
		}

	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.1F)); // original 0.3F
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Bite_Impaler();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_HEAL);
		}
	}
	
}
