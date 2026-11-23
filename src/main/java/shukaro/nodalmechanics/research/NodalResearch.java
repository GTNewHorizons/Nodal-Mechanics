package shukaro.nodalmechanics.research;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import shukaro.nodalmechanics.items.NodalItems;
import shukaro.nodalmechanics.recipe.NodalRecipes;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class NodalResearch {

    public static ResearchPage matrixRecipePage;

    public static void initResearch() {
        ResearchItem researchNodeCatalyzation = new ResearchItem(
                "NODECATALYZATION",
                "BASICS",
                new AspectList().add(Aspect.AURA, 20).add(Aspect.VOID, 15).add(Aspect.GREED, 12).add(Aspect.ELDRITCH, 9)
                        .add(Aspect.MIND, 6).add(Aspect.MAGIC, 3),
                -5,
                4,
                3,
                new ItemStack(NodalItems.itemMatrix)).setPages(
                        new ResearchPage(StatCollector.translateToLocal("nodalmechanics.nodecatalyzation.research")),
                        matrixRecipePage,
                        new ResearchPage(NodalRecipes.variedAttuneRecipe),
                        new ResearchPage(NodalRecipes.sameAttuneRecipe),
                        new ResearchPage(NodalRecipes.variedNodeRecipe),
                        new ResearchPage(NodalRecipes.sameNodeRecipe)).setParents("NODEJAR", "NODETAPPER2")
                        .setParentsHidden("JARLABEL").setSpecial();
        researchNodeCatalyzation.registerResearchItem();
        ThaumcraftApi.addWarpToResearch("NODECATALYZATION", 8);
        ThaumcraftApi.addWarpToItem(new ItemStack(NodalItems.itemMatrix), 8);
    }
}
