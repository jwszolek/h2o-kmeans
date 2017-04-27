package com.acxiom;

import org.apache.hadoop.fs.Path;
import water.Futures;
import water.H2O;
import water.Key;
import water.fvec.*;
import hex.kmeans.KMeans;
import hex.kmeans.KMeansModel;
import hex.kmeans.KMeansModel.KMeansParameters;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import java.io.IOException;

/**
 * Created by jwszol on 24/04/17.
 */
public class H2OKMeans {

    private static  FileSystem hdfs = H2ODriver.HDFS_FILESYSTEM;

    public static void initialize(){

        // Setup cloud name
        String[] args = new String[] {"-name", "h2o_kmeans_test"};
        // Build a cloud of 1
        H2O.main(args);
        H2O.waitForCloudSize(1, 10*1000 /* ms */);
    }
    public static void runKMeans(String hdfsPath) throws IOException {

//        Configuration conf = new Configuration();
//        hdfs = FileSystem.get(conf);
//        Path fileLocation = new Path(hdfsPath);
//        ContentSummary cSummary = hdfs.getContentSummary(fileLocation);
//        long length = cSummary.getLength();

//        Key hdfsKey = HDFSFileVec.make(hdfsPath,length);

        //Frame frame = water.parser.ParseDataset.parse(Key.make(),hdfsKey);
        Frame frame = new Frame();
        frame.add("tesing",dvec());


        KMeansParameters params = new KMeansParameters();
        params._train = frame._key;
        params._k = 5;
        params._max_iterations = 1000;
        KMeans job = new KMeans(params);
        KMeansModel kmm = job.trainModel().get();
        Frame output = kmm.score(frame);
        Vec[] vecs = output.vecs();
        System.out.print("vecs:");
        for ( Vec v : vecs ) {
            System.out.println(v +","+ v.length());
            for ( int i = 0; i < v.length(); i++ ) {
                System.out.println(i +":"+ v.at(i));
            }
        }
        System.out.println();
    }

    public static Vec dvec(double...rows) {
        Key<Vec> k = Vec.VectorGroup.VG_LEN1.addVec();
        Futures fs = new Futures();
        AppendableVec avec = new AppendableVec(k, Vec.T_NUM);
        NewChunk chunk = new NewChunk(avec, 0);
        for (double r : rows)
            chunk.addNum(r);
        chunk.close(0, fs);
        Vec vec = avec.layout_and_close(fs);
        fs.blockForPending();
        return vec;
    }

}
