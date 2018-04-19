package org.superbiz.engine;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.superbiz.tf.annotation.EngineImplementation;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class EngineFinder {
    private static final Map<String, Class<?>> MAPPING;

    static {
        try {
            ClassPath classPath = ClassPath.from(EngineFinder.class.getClassLoader());
            ImmutableSet<ClassPath.ClassInfo> topLevelClasses = classPath.getTopLevelClasses(Engine.class.getPackage().getName());
            MAPPING = topLevelClasses.stream()
                    .filter(clazz -> clazz.load().getAnnotation(EngineImplementation.class) != null)
                    .map(clazz -> EngineMapping.of(clazz.load().getAnnotation(EngineImplementation.class).value(), clazz.load()))
                    .collect(Collectors.toMap(EngineMapping::getName, EngineMapping::getClazz));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Engine load(String engineName) {
        Class<?> engineClass = MAPPING.get(engineName);
        if (engineClass == null) {
            throw new IllegalArgumentException(String.format("Engine %s is not on your classpath.", engineName));
        } else {
            try {
                Engine engine = (Engine) engineClass.newInstance();
                return engine;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException(String.format("Instantiation of engine %s failed.", engineName));
            }
        }
    }

    private static class EngineMapping {

        private final String name;
        private final Class<?> clazz;

        public EngineMapping(String name, Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }

        public static EngineMapping of(String name, Class<?> clazz) {
            return new EngineMapping(name, clazz);
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("EngineMapping{");
            sb.append("name='").append(name).append('\'');
            sb.append(", clazz=").append(clazz);
            sb.append('}');
            return sb.toString();
        }

        public Class<?> getClazz() {
            return clazz;
        }
    }
}
