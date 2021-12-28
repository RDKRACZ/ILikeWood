package yamahari.ilikewood.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yamahari.ilikewood.ILikeWood;
import yamahari.ilikewood.entity.WoodenItemFrameEntity;
import yamahari.ilikewood.registry.objecttype.WoodenEntityType;
import yamahari.ilikewood.registry.objecttype.WoodenItemType;
import yamahari.ilikewood.registry.woodtype.IWoodType;
import yamahari.ilikewood.util.Util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class ILikeWoodEntityTypeRegistry
    extends AbstractILikeWoodObjectRegistry<EntityType<?>, WoodenEntityType> {

    public ILikeWoodEntityTypeRegistry() {
        super(ForgeRegistries.ENTITIES);
    }

    private void registerItemFrameEntityTypes() {
        final Map<IWoodType, RegistryObject<EntityType<?>>> entityTypes = new HashMap<>();
        ILikeWood.WOOD_TYPE_REGISTRY
            .getWoodTypes()
            .filter(woodType -> woodType.getItemTypes().contains(WoodenItemType.ITEM_FRAME))
            .forEach(woodType -> entityTypes.put(woodType, registerItemFrameEntityType(woodType)));
        this.registryObjects.put(WoodenEntityType.ITEM_FRAME, Collections.unmodifiableMap(entityTypes));
    }

    private RegistryObject<EntityType<?>> registerItemFrameEntityType(final IWoodType woodType) {
        final String name = Util.toRegistryName(woodType.getName(), WoodenItemType.ITEM_FRAME.getName());
        return this.registry.register(name,
            () -> EntityType.Builder.<WoodenItemFrameEntity>of((entityType, world) -> new WoodenItemFrameEntity(woodType,
                entityType,
                world), MobCategory.MISC)
                .sized(0.5F, 0.5F)
                .clientTrackingRange(10)
                .updateInterval(Integer.MAX_VALUE)
                .build(name));
    }

    @Override
    protected void register() {
        registerItemFrameEntityTypes();
    }

    @Override
    public Stream<RegistryObject<EntityType<?>>> getRegistryObjects(final WoodenEntityType objectType) {
        return ILikeWood.WOOD_TYPE_REGISTRY
            .getWoodTypes()
            .filter(woodType -> woodType.getItemTypes().contains(WoodenItemType.ITEM_FRAME))
            .map(woodType -> this.getRegistryObject(woodType, objectType));
    }
}
