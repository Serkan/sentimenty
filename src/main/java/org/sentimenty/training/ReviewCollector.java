package org.sentimenty.training;

import edu.stanford.nlp.util.Pair;

/**
 * Created by serkan on 01.05.2015.
 */
public class ReviewCollector {

    //        idList.add("com.sgiggle.production");
//        idList.add("com.twitter.android");
//        idList.add("com.foursquare.robin");
//        idList.add("com.askfm");
//        idList.add("com.tumblr");
//        idList.add("com.waplog.social");
//        idList.add("com.pinterest");
//        idList.add("com.linkedin.android");
//        idList.add("com.google.android.apps.plus");
//        idList.add("com.connected2.ozzy.c2m");

    public void startCollecting(Pair<String, String> app) {
        ReviewCollectorTask collector = new ReviewCollectorTask(app.first());
        collector.run();
    }


}
