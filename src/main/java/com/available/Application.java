package com.available;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.MongoException; 
import com.mongodb.MongoWriteConcernException; 
import com.mongodb.MongoWriteException; 
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoCommandException;
import java.util.Random;
import org.bson.Document;

import java.util.Arrays;

public class Application {
    /**
     * @param args takes an optional single argument for the connection string
     */
    public static void main(final String[] args) {
        String mongoURI = System.getenv("MONGODB_URI");
        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURI));
        MongoDatabase database = mongoClient.getDatabase("java");
        MongoCollection<Document> collection = database.getCollection("available");

        Random r = new Random();
        int low = 0;
        int high = 100;
        int random = r.nextInt(high);

        while(true) {
            Document doc = new Document("number", random);
            try {
                collection.insertOne(doc);
                System.out.println("At " + System.currentTimeMillis() + 
                              " inserted: " + doc.getObjectId("_id"));
            } 
            // if the write failed due some other failure specific to the insert command
            catch (MongoWriteException wex) {
                System.out.println(wex);
            }
            // if the write failed due being unable to fulfil the write concern
            catch (MongoWriteConcernException wcex) {
                System.out.println(wcex);
            }
            // If the write failed due to command failed with error
            catch (MongoCommandException cex) {
                System.out.println(cex);
            }
            // if the write failed due some other failure
            catch (MongoException ex) {
                System.out.println(ex);
            }
            
            random = r.nextInt(high);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                throw new RuntimeException("Sleeping interrupted", ex);
            }
        }

        //mongoClient.close();
    }
}
