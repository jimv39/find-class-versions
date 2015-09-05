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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Unit tests for our main class.
 * @author Jim Voris
 */
public class FindClassVersionsTest {
    
    public FindClassVersionsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class FindClassVersions.
     */
    @org.junit.Test
    public void testMain() {
        System.out.println("main");
        String[] args = new String[2];
        String cwd = System.getProperty("user.home");
        args[0] = cwd + "/dev";
        args[1] = "49";
        FindClassVersions.main(args);
    }
    
}
