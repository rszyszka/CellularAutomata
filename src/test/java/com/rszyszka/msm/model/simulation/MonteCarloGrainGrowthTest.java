package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Space;
import com.rszyszka.msm.model.generator.nucleons.NucleonsGenerator;
import junit.framework.TestCase;

public class MonteCarloGrainGrowthTest extends TestCase {


    public void test() {
        Space space = new Space(400, 400);
        NucleonsGenerator.fillSpaceWithNumberOfUniqueIds(3, space);
        MonteCarloGrainGrowth grainGrowth = new MonteCarloGrainGrowth(space, 20, 0.5);
        grainGrowth.simulateGrainGrowth();
    }


}