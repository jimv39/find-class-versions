/*
 * Copyright 2015 JimVoris.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jimv39.findclassversions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Peal open the jars that we find, and examine the .class files within.
 *
 * @author Jim Voris
 */
class FileVisitor extends SimpleFileVisitor<Path> {
    private final Set<String> jarVersionSet;
    private final Integer targetVersion;
    
    FileVisitor(Set<String> fileVersionSet, Integer targetVersion) {
        this.jarVersionSet = fileVersionSet;
        this.targetVersion = targetVersion;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(attrs);
        if (file.toFile().getName().endsWith(".jar")) {
            reportJavaVersion(file);
        }
        return FileVisitResult.CONTINUE;
    }

    private void reportJavaVersion(Path file) throws IOException {
        try (ZipFile jarFile = new ZipFile(file.toFile())) {
            for (Enumeration<? extends ZipEntry> list = jarFile.entries(); list.hasMoreElements();) {
                ZipEntry entry = list.nextElement();
                if (entry.getName().endsWith(".class")) {
                    InputStream is = jarFile.getInputStream(entry);
                    byte[] entryHeader = new byte[10];
                    is.read(entryHeader);
                    String formattedMinorVersion = String.format("%1d%1d", entryHeader[4], entryHeader[5]);
                    String formattedMajorVersion = String.format("%1d%1d", entryHeader[6], entryHeader[7]);
                    int minorVersion = Integer.parseInt(formattedMinorVersion);
                    int majorVersion = Integer.parseInt(formattedMajorVersion);
                    String setEntry = file.toFile().getCanonicalPath() + ":" + majorVersion + "." + minorVersion;
                    if (majorVersion > this.targetVersion) {
                        this.jarVersionSet.add(setEntry);
                    }
                }
            }
        } catch (ZipException e) {
            System.out.println(file.toFile().getAbsolutePath() + ":" + e.getLocalizedMessage());
        }
    }
}
