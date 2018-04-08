package org.flexdata.csv;

public interface Resource extends Iterable<ResourceLine> {
    static Resource fromClasspath(String fileInClasspath) {
        return CSVClasspathResource.of(fileInClasspath);
    }
}
