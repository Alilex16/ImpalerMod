package impalermod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import impalermod.ImpalerMod;

import java.util.Random;

public class PleasureForPainPower extends AbstractImpalerPower implements PostDrawSubscriber, PostBattleSubscriber,
        PostDungeonInitializeSubscriber {
    public static String POWER_ID = "PleasureForPainPower";
    public static String NAME = "Pleasure For Pain";
    public static final String DESCRIPTION = "Every time you Heal, deal either !D! damage to all enemies or !D! damage to a random enemy.";
    public static final String[] DESCRIPTIONS = new String[]{ "Every time you Heal, deal either #b", " damage to all enemies or #b", " damage to a random enemy."};
    public static final String IMG = "powers/Placeholder.png";
    private int amountAll;
    private int amountRandom;

    public PleasureForPainPower(AbstractCreature owner, int amountAll, int amountRandom) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amountAll = amountAll;
        this.amountRandom = amountRandom;
        this.isTurnBased = true;
        this.img = new Texture(ImpalerMod.getResourcePath(IMG));
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        BaseMod.subscribe(this);
    }

    @Override
    public void receivePostBattle(AbstractRoom arg0) {
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostDungeonInitialize() {
        BaseMod.unsubscribeLater(this);
    }

    @Override
    public void receivePostDraw(AbstractCard c) {    }


    @Override
    public int onHeal(final int healAmount)
    {
        actionAfterHeal();
        return healAmount;
    }

    private void actionAfterHeal()
    {
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(true);

        Random rand = new Random();
        int number = rand.nextInt(2) + 1 ;

        if (number == 1)
        {
            //amount = 3;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(randomMonster, new DamageInfo(this.owner, amountRandom, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
            //AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(this.owner, new DamageInfo(this.source, this.amount), 1));
        }

        if (number == 2)
        {
            //amount = 1;
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new CleaveEffect(), 0.25F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(amountAll, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, true));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amountAll + DESCRIPTIONS[1] + amountRandom + DESCRIPTIONS[2];
    }
}