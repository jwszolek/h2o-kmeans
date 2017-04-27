package com.jwszol;

import org.apache.hadoop.fs.FileSystem;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


/**
 * Created by jwszol on 24/04/17.
 */
public class H2ODriver {
    public static FileSystem HDFS_FILESYSTEM = null;

    @Option(name="-f", aliases = { "--filepath" }, usage="HDFS filepath")
    private String filePath;

    public static void main(String[] args) throws Exception
    {
        new H2ODriver().doMain(args);
    }


    private void doMain(String[] args) throws Exception{
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);

        H2OKMeans.initialize();
        H2OKMeans.runKMeans(filePath);
    }
}
