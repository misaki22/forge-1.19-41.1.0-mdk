package net.misaki.tutorialmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EightBallItem extends Item {
    public EightBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        if ((!level.isClientSide()) && hand == InteractionHand.MAIN_HAND){
            outputRandomNumber(player);
            player.getCooldowns().addCooldown(this, 20);
        }

        return super.use(level, player, hand);
    }

    //Tooltip for the item when hovered and when the SHIFT key is down
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList,
                                TooltipFlag tooltipFlag) {

        if (Screen.hasShiftDown()){
            componentList.add(Component.literal("Right click to get a random number!").withStyle(ChatFormatting.AQUA));
        }else{
            componentList.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(itemStack, level, componentList, tooltipFlag);
    }

    private void outputRandomNumber(Player player) {
        //Server: Main hand & Offhand
        //Client: Main hand & Offhand

        player.sendSystemMessage(Component.literal("Your Number is " + getRandomNumber()));
    }

    private int getRandomNumber() {
        return RandomSource.createNewThreadLocalInstance().nextInt(10);
    }
}
