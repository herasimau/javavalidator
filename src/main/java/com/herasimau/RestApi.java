package com.herasimau;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.springframework.web.bind.annotation.*;
import javax.tools.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


/**
 * Created by herasimau on 04/03/17.
 */
@RestController
public class RestApi {

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate (@RequestParam String clazz) throws Exception {
       // String source = "package level1.task1; public class Solution {public Solution() { System.out.println("world");}";

        String projectLocation = "/home/herasimau/Documents/javarush/src/main/java";
        String testLocation = "/home/herasimau/Documents/javarush/target/test-classes";

        File tests = new File(testLocation);
        File root = new File(projectLocation);

        String directoryPath = clazz.split(" ")[1].replace(";","").replace(".","/")+"/";
        String fileName = "Solution.java";


        File sourceFile = new File(root, directoryPath+fileName);
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), clazz.getBytes(StandardCharsets.UTF_8));

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());


        URLClassLoader testClassLoader = URLClassLoader.newInstance(new URL[] { tests.toURI().toURL() });
        Class<?> testSolution = Class.forName("com.herasimau.TestSolution", true, testClassLoader);
        Result result = JUnitCore.runClasses(testSolution);

        return result.wasSuccessful();

    }
}
