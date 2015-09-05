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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Report the java version of the class files within any jar files that exist beneath a given root directory.
 *
 * @author Jim Voris
 */
public class FindClassVersions {

    /**
     * Application entry point.
     *
     * @param args command line arguments. arg&#091;0&#093; is the base directory for the scan (required). arg&#091;1&#093; (optional)
     * defines the minimum java class file version to include in the report. <br>For example, Java6 has as class file version number of 50;
     * Java7 has a class file version of 51; Java8 has a class file version of 52. If arg&#091;1&#093; is not supplied, report the versions
     * of <i>all</i> jar files.
     */
    public static void main(String[] args) {
        // Verify that the given directory actually exists.
        if (args.length > 0) {
            String directoryName = args[0];
            System.out.println("Directory: [" + directoryName + "]");
            File rootDirectory = new File(directoryName);
            if (rootDirectory.exists() && rootDirectory.isDirectory()) {
                try {
                    Integer targetVersion = Integer.MIN_VALUE;
                    if (args.length > 1) {
                        try {
                            targetVersion = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Bad version string. Reporting all versions.");
                        }
                    }
                    reportOnDirectoryTree(rootDirectory, targetVersion);
                } catch (IOException ex) {
                    Logger.getLogger(FindClassVersions.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println(args[0] + " does not exist, or is not a directory.");
            }
        } else {
            System.out.println("The directory name should be your 1st command line argument. Follow with the minimum java class file version number [optional].");
        }
    }

    private static void reportOnDirectoryTree(File rootDirectory, Integer targetVersion) throws IOException {
        Set<String> jarVersionSet = new TreeSet<>();
        Path startingPath = rootDirectory.toPath();
        FileVisitor visitor = new FileVisitor(jarVersionSet, targetVersion);
        Files.walkFileTree(startingPath, visitor);
        jarVersionSet.stream().forEach((jarEntry) -> {
            System.out.println(jarEntry);
        });
    }

}
