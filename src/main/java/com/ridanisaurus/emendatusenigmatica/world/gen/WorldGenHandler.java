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

package com.ridanisaurus.emendatusenigmatica.world.gen;

import com.ridanisaurus.emendatusenigmatica.config.WorldGenConfig;
import com.ridanisaurus.emendatusenigmatica.config.WorldGenConfig.OreConfigs.BakedOreProps;
import com.ridanisaurus.emendatusenigmatica.registries.BlockHandler;
import com.ridanisaurus.emendatusenigmatica.util.Ores;
import com.ridanisaurus.emendatusenigmatica.util.Strata;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import static com.ridanisaurus.emendatusenigmatica.EmendatusEnigmatica.LOGGER;

public class WorldGenHandler {

    public static void addEEOres(BiomeGenerationSettingsBuilder builder) {
        for (Strata stratum : Strata.values()) {
            if (WorldGenConfig.COMMON.STRATA.get(stratum) && stratum.block.get() != null) {
                for (Ores ore : Ores.values()) {
                    BakedOreProps p = WorldGenConfig.COMMON.ORES.get(ore);
                    if (p.ACTIVE) {
                        LOGGER.debug("Adding ore {} with stratum {} to the world!", ore, stratum);
                        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, getOreFeature(
                                p.COUNT_PER_CHUNK,
                                p.VEIN_SIZE,
                                p.MIN_Y,
                                p.MAX_Y, getFilter(stratum), getOreBlock(stratum, ore)));
                    }
                }
            }
        }
    }

    private static RuleTest getFilter(Strata stratum) {
        return new BlockMatchRuleTest(stratum.block.get());
    }

    private static BlockState getOreBlock(Strata stratum, Ores ore) {
        return BlockHandler.oreBlockTable.get().get(stratum, ore).get().getDefaultState();
    }

    private static ConfiguredFeature<?, ?> getOreFeature(int count, int size, int minY, int maxY, RuleTest filler, BlockState state) {
        Feature<OreFeatureConfig> oreFeature = Feature.ORE;
        return oreFeature.withConfiguration(new OreFeatureConfig(filler, state, size))
                .withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(minY, maxY))) // min and max y using vanilla depth averages
                .func_242728_a() // square vein
                .func_242731_b(count) // max count per chunk
                ;
    }
}