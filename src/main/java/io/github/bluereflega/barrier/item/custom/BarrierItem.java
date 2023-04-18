package io.github.bluereflega.barrier.item.custom;

import io.github.bluereflega.barrier.util.math.SphericalCoordinateHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
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
		addBarrierParticles(world, user);

		user.sendMessage(Text.literal("Barrier used!"), true);

		// 1.5-second cooldown
		user.getItemCooldownManager().set(this, 30);

		return TypedActionResult.success(itemStack, world.isClient());
	}

	private void addBarrierParticles(World world, PlayerEntity player) {
		double playerX = player.getX();
		double playerY = player.getY() + (player.getHeight() / 2);
		double playerZ = player.getZ();

		double max_zenith = 180.0;
		double max_azimuth = 360.0;

		double radius = 1.1;
		int zenith_lines = 10;
		int azimuth_lines = zenith_lines * 2;

		for (int zenith_line = 0; zenith_line <= zenith_lines; zenith_line++) {
			for (int azimuth_line = 0; azimuth_line <= azimuth_lines; azimuth_line++) {
				double zenith = max_zenith * ((double) (zenith_line) / zenith_lines);
				double azimuth = max_azimuth * ((double) (azimuth_line) / azimuth_lines);

				world.addParticle(
					// TODO: use dust particle instead of end rod particle
					ParticleTypes.END_ROD,
					playerX + SphericalCoordinateHelper.toCartX(radius, zenith, azimuth),
					playerY + SphericalCoordinateHelper.toCartY(radius, zenith),
					playerZ + SphericalCoordinateHelper.toCartZ(radius, zenith, azimuth),
					0, 0, 0
				);
			}
		}
	}
}
