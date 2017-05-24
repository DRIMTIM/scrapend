package org.scrapend.service.impl;

import org.scrapend.service.ScrapService;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by pelupotter on 22/05/17.
 */

@Service
public class ScrapServiceImpl implements ScrapService {

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    @Override
    public String spiderTestMethod() {

        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

        DB db = mongoClient.getDB("test");

        Jongo jongo = new Jongo(db);
        MongoCollection friends = jongo.getCollection("friends");

        String domainName = "google.com";
        String command = "ping -c 3 " + domainName;

        String output = this.executeCommand(command);

        System.out.println(output);

        return "SpiderMan!";
    }
}
