package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import impalermod.cards.AbstractImpalerCard;

import java.util.Iterator;

public class CrimsonDash extends AbstractImpalerCard {
	public static final String ID = "CrimsonDash";
	public static final	String NAME = "Crimson Dash";
	public static final	String IMG = "cards/attack/CrimsonDash.png";
	public static final	String DESCRIPTION = "Gain !B! Block. NL Deal !D! damage. NL If you have 2 or more Bloody cards in your hand, gain !M! Dexterity.";
	public static final	String[] DESCRIPTION_EXTENDED = new String[] {"Not ~fast~ enough... Need more... #r@Bloody@ cards..."};

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;

	private static final int POOL = 1;
	private static final int COST = 2;

	private static final int POWER = 6;
	private static final int UPGRADE_POWER = 2;
	private static final int DEXTERITY_GAIN = 2;
	private static final int UPGRADE_DEXTERITY_GAIN = 1;
	//private static final int UPGRADED_COST = 1;

	private int amountBloodyCards = 0;


	public CrimsonDash() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
		this.baseBlock = POWER;
		this.baseDamage = POWER;
		this.baseMagicNumber = DEXTERITY_GAIN;
		this.magicNumber = this.baseMagicNumber;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

        Iterator var5 = p.hand.group.iterator();
        //amountBloodyCards = 0;
        while(var5.hasNext()) {

            AbstractCard c = (AbstractCard)var5.next();
            if ((c.rawDescription.contains("Lose") || c.rawDescription.contains("lose")) && (c.rawDescription.contains("HP")) && c.type != CardType.CURSE)
            {
                amountBloodyCards += 1;
            }
        }

		if (amountBloodyCards >= 2)
		{
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		}
		else
		{
			AbstractDungeon.effectList.add(new SpeechBubble(p.dialogX, p.dialogY, 2.5F, DESCRIPTION_EXTENDED[0], true));
		}
        amountBloodyCards = 0;
	}


	public AbstractCard makeCopy() {
		return new CrimsonDash();
	}

	public void upgrade() {
		if (!upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_POWER);
			this.upgradeDamage(UPGRADE_POWER);
			this.upgradeMagicNumber(UPGRADE_DEXTERITY_GAIN);
			//this.upgradeBaseCost(UPGRADED_COST);
			this.initializeDescription();
		}
	}
}