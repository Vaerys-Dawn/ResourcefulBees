package com.resourcefulbees.resourcefulbees.client.render.entity;

import com.resourcefulbees.resourcefulbees.ResourcefulBees;
import com.resourcefulbees.resourcefulbees.api.beedata.CustomBeeData;
import com.resourcefulbees.resourcefulbees.client.render.entity.layers.*;
import com.resourcefulbees.resourcefulbees.client.render.entity.models.CustomBeeModel;
import com.resourcefulbees.resourcefulbees.entity.passive.CustomBeeEntity;
import com.resourcefulbees.resourcefulbees.lib.BaseModelTypes;
import com.resourcefulbees.resourcefulbees.lib.BeeConstants;
import com.resourcefulbees.resourcefulbees.lib.ModelTypes;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;


@OnlyIn(Dist.CLIENT)
public class CustomBeeRenderer extends MobRenderer<CustomBeeEntity, CustomBeeModel<CustomBeeEntity>> {

    private final ResourceLocation angryTexture;
    private final ResourceLocation baseTexture;

    public CustomBeeRenderer(BaseModelTypes modelType, EntityRendererManager manager, CustomBeeData beeData) {
        super(manager, new CustomBeeModel<>(modelType), 0.4F);

        angryTexture = new ResourceLocation(ResourcefulBees.MOD_ID, BeeConstants.ENTITY_TEXTURES_DIR + beeData.getBaseLayerTexture() + "_angry.png");
        baseTexture = new ResourceLocation(ResourcefulBees.MOD_ID, BeeConstants.ENTITY_TEXTURES_DIR + beeData.getBaseLayerTexture() + ".png");

        if (beeData.getColorData().isBeeColored()) {
            addLayer(new PrimaryColorLayer(this, beeData.getColorData()));
            addLayer(new SecondaryColorLayer(this, beeData.getColorData()));
        }

        if (beeData.getColorData().isGlowing() || beeData.getColorData().isEnchanted()) {
            addLayer(new EmissiveBeeLayer(this, beeData.getColorData()));
        }

        if (beeData.getColorData().getModelType().equals(ModelTypes.GELATINOUS)) {
            addLayer(new GelLayer<>(this));
        } else if (beeData.getColorData().getModelType().equals(ModelTypes.ORE)) {
            addLayer(new CrystalsLayer(this));
        }
    }

    @Nonnull
    public ResourceLocation getEntityTexture(CustomBeeEntity entity) {
        return entity.hasAngerTime() ? angryTexture : baseTexture;
    }
}
