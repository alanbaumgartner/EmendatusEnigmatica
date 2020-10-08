/*
 * MIT License
 *
 * Copyright (c) 2020 Ridanisaurus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ridanisaurus.emendatusenigmatica.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class OreUranium extends OreBlock {
    public OreUranium() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(3.0f, 3.0f)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random r) {
        double d0 = 0.5625D;
        if (r.nextFloat() < 0.5f) {
            float red = MathHelper.nextFloat(r, 0.05f, 0.2f);
            float green = MathHelper.nextFloat(r, 0.5f, 0.8f);
            float blue = MathHelper.nextFloat(r, 0.1f, 0.3f);
            float alpha = MathHelper.nextFloat(r, 0.5f, 0.9f);

            for (Direction direction : Direction.values()) {
                BlockPos blockpos = pos.offset(direction);
                if (r.nextFloat() < 0.5f && !world.getBlockState(blockpos).isOpaqueCube(world, blockpos)) {
                    Direction.Axis direction$axis = direction.getAxis();
                    double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getXOffset() : (double) r.nextFloat();
                    double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getYOffset() : (double) r.nextFloat();
                    double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getZOffset() : (double) r.nextFloat();
                    world.addParticle(new RedstoneParticleData(red, green, blue, alpha), (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
