package impalermod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import impalermod.cards.AbstractImpalerCard;
import impalermod.powers.EnervatedPower;

public class TheThirst extends AbstractImpalerCard {
    public static final String ID = "TheThirst";
    public static final	String NAME = "The Thirst";
    public static final	String IMG = "cards/attack/TheThirst.png";
    public static final	String DESCRIPTION = "Deal !D! damage to all enemies. NL Heal !M! HP for each enemy hit. NL Become Enervated.";

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int POOL = 1;

    private static final int COST = 1;
    private static final int POWER = 13;
    private static final int HEAL_AMT = 1;
    private static final int UPGRADE_POWER = 3;
    private static final int UPGRADE_HEAL = 1;

    public TheThirst() {
        super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,RARITY,TARGET,POOL);
        this.baseDamage = POWER;
        this.baseMagicNumber = this.magicNumber = HEAL_AMT;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()){
                AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber)); // or just calculate magicNumber/enemy and add this line lower?
            }
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnervatedPower(p, 1), 1));
    }

    public AbstractCard makeCopy() {
        return new TheThirst();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_POWER);
            this.upgradeMagicNumber(UPGRADE_HEAL);
        }

    }

}
