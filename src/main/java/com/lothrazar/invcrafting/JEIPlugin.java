/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
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
 ******************************************************************************/
package com.lothrazar.invcrafting;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

//  private ResourceLocation id = new ResourceLocation(ModInvCrafting.MODID, "player");
 
  @Override
  public void register(IModRegistry registry) {
     
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerPlayerCrafting.class, VanillaRecipeCategoryUid.CRAFTING,
            1, // @param recipeSlotStart    the first slot for recipe inputs // skip over the 1 output and the 5 armor slots
            9, // @param recipeSlotCount    the number of slots for recipe inputs //3x3
            15, //@param inventorySlotStart the first slot of the available inventory (usually player inventory) =9+6
            36);//@param inventorySlotCount the number of slots of the available inventory //top right including hotbar =4*9
  }

//  @Override
//  public ResourceLocation getPluginUid() {
//    return this.id;
//  }
}
