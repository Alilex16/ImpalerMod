package impalermod.cards.skill;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.PreservationPower;

public class Preservation extends AbstractImpalerCard {
	public static final String ID = "Preservation";
	public static final	String NAME = "Preservation";
	public static final	String IMG = "cards/skill/Preservation.png";
	public static final	String DESCRIPTION = "Whenever you Heal this turn, gain !M! Block.";

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;

	private static final int POOL = 0;
	private static final int COST = 0;

	private static final int POWER = 3;
	private static final int UPGRADE_POWER = 2;

	public Preservation() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		//this.baseBlock = POWER;
		this.magicNumber = this.baseMagicNumber = this.baseBlock = POWER;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new SFXAction("RAGE"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.ORANGE, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.0F));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PreservationPower(p, this.magicNumber), this.magicNumber)); // (p, this.block), this.block)
	}

	public AbstractCard makeCopy() {
		return new Preservation();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_POWER);
		}
	}
	
}
