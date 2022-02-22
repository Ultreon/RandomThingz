package com.ultreon.randomthingz.plugin;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.util.Reflection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.util.*;
import java.util.stream.Collectors;

import static net.minecraftforge.fml.Logging.LOADING;

@Beta
public class RTPluginLoader {
    private static final Logger LOGGER = LogManager.getLogger("RandomThingz:Plugins-Loader");
    private static final Type AUTO_SUBSCRIBER = Type.getType(QFMPlugin.class);
    private static final Type MOD_TYPE = Type.getType(Mod.class);

    public RTPluginLoader() {
        for (ModFileScanData scanData : ModList.get().getAllScanData()) {
            if (scanData == null) return;
            List<ModFileScanData.AnnotationData> ebsTargets = scanData.getAnnotations().stream().
                    filter(annotationData -> AUTO_SUBSCRIBER.equals(annotationData.annotationType())).toList();
            Map<String, String> modids = scanData.getAnnotations().stream().
                    filter(annotationData -> MOD_TYPE.equals(annotationData.annotationType())).
                    collect(Collectors.toMap(a -> a.clazz().getClassName(), a -> (String) a.annotationData().get("value")));

            for (Map.Entry<String, String> modIdMap : modids.entrySet()) {
                LOGGER.debug(LOADING, "Attempting to register RandomThingz Plugin for Mod: {}", modIdMap.getValue());

                Class<?> modClass = (Class<?>) Reflection.getField(ModList.get().getModContainerById(modIdMap.getValue()).orElseThrow(), "modClass");
                ClassLoader loader = modClass.getClassLoader();
                ebsTargets.forEach(ad -> {
                    @SuppressWarnings("unchecked") final List<ModAnnotation.EnumHolder> sidesValue = (List<ModAnnotation.EnumHolder>) ad.annotationData().
                            getOrDefault("value", Arrays.asList(new ModAnnotation.EnumHolder(null, "CLIENT"), new ModAnnotation.EnumHolder(null, "DEDICATED_SERVER")));
                    final EnumSet<Dist> sides = sidesValue.stream().map(eh -> Dist.valueOf(eh.getValue())).
                            collect(Collectors.toCollection(() -> EnumSet.noneOf(Dist.class)));
                    final String modId = (String) ad.annotationData().getOrDefault("modid", modids.getOrDefault(ad.clazz().getClassName(), modIdMap.getValue()));
//                    final ModAnnotation.EnumHolder busTargetHolder = (ModAnnotation.EnumHolder)ad.getAnnotationData().getOrDefault("bus", new ModAnnotation.EnumHolder(null, "FORGE"));
//                    final Mod.EventBusSubscriber.Bus busTarget = Mod.EventBusSubscriber.Bus.valueOf(busTargetHolder.getValue());
                    if (Objects.equals(modIdMap.getValue(), modId) && sides.contains(FMLEnvironment.dist)) {
                        try {
                            LOGGER.debug("Registering RandomThingz Plugin for Class name: {}", ad.clazz().getClassName());
                            QFMPluginManager.get().registerPlugin(Class.forName(ad.clazz().getClassName(), true, loader));
                        } catch (ClassNotFoundException e) {
                            LOGGER.fatal(LOADING, "Failed to read RandomThingz Plugin class {} for @RandomThingzPlugin annotation", ad.clazz(), e);
                            throw new RuntimeException(e);
                        }
                    }/* else if (!Objects.equals(modIdMap.getValue(), modId)) {
                        LOGGER.warn("Random Thingz Plugin has invalid modid: " + modId + ", expected: " + modIdMap);
                    }*/
                });
            }
        }
    }
}
