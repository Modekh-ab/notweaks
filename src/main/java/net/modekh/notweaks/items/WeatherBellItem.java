package net.modekh.notweaks.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.modekh.notweaks.items.base.ItemBase;
import net.modekh.notweaks.utils.WeatherCondition;

public class WeatherBellItem extends ItemBase {
    public WeatherBellItem() {
        super(new Properties().rarity(Rarity.RARE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        MinecraftServer server = level.getServer();
        WeatherCondition currentCondition = null;
        WeatherCondition condition = null;

        if (server != null) {
            ServerLevel serverLevel = server.getLevel(level.dimension());

            if (serverLevel != null) {
                boolean rain = serverLevel.isRaining();
                boolean thunder = serverLevel.isThundering();

                if (rain) {
                    currentCondition = thunder ? WeatherCondition.THUNDER : WeatherCondition.RAIN;
                } else {
                    currentCondition = thunder ? WeatherCondition.THUNDER : WeatherCondition.CLEAR;
                }

                condition = currentCondition.next();

                serverLevel.setWeatherParameters(
                        0, condition.getRaining() ? 12000 : 0,
                        condition.getRaining(), condition.getThundering()
                );
            }

            player.displayClientMessage(Component.translatable("message.notweaks.weather_bell", condition.getMessage()), true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.NOTE_BLOCK_BELL.get(), SoundSource.MASTER, 1.0F, 1.0F);
        }

        return InteractionResultHolder.success(stack);
    }
}
