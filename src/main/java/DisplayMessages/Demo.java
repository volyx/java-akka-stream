// Copyright (c) Microsoft. All rights reserved.

package DisplayMessages;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletionStage;

import static java.lang.System.out;

/**
 * Retrieve messages from IoT hub and display the data in the console
 */
public class Demo extends ReactiveStreamingApp {

    static ObjectMapper jsonParser = new ObjectMapper();

    public static void main(String args[]) {

        // Source retrieving messages from one IoT hub partition (e.g. partition 2)
        //Source<IoTMessage, NotUsed> messages = new IoTHubPartition(2).source();

        // Source retrieving from all IoT hub partitions for the past 24 hours
        Source<Integer, NotUsed> messages = Source.range(0, 100);

        messages
                .filter(m -> m % 2 == 0)
                .to(console())
                .run(streamMaterializer);
    }

    public static Sink<Integer, CompletionStage<Done>> console() {
        return Sink.foreach(m -> {
            out.print(m + ", ");
        });
    }
}
