package io.github.bluereflega.barrier.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BarrierItem extends Item {
	public BarrierItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);

		world.playSound(null, user.getX(), user.getY(), user.getZ(),
			SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.PLAYERS, 1.0F, 1.0F);

		user.sendMessage(Text.literal("Barrier used!"), true);

		// 1.5-second cooldown
		user.getItemCooldownManager().set(this, 30);

		return TypedActionResult.success(itemStack, world.isClient());
	}
}
