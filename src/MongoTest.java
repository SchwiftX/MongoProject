import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MongoTest {

    Mongo mongo;
    DB school;
    DBCollection student;

    protected MongoTest(){
        try {
            mongo = new Mongo("localhost",10086);
            school = mongo.getDB("school");
            student = school.getCollection("student");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void insertTest(int num, boolean defaultID){
        long beginTime = 0, time = 0;
        List<Long> speedList = new ArrayList<>();
        DBObject obj;
        for(int i = 0; i <= num; i++){
            if(i == 0) {
                time = System.currentTimeMillis();
                beginTime = time;
                continue;
            }else if(i % 100000 == 0) {
                long curTime = System.currentTimeMillis();
                speedList.add((long)(100000.0 / ((curTime - time) / 1000.0)));
                time = curTime;
            }
            obj = new BasicDBObject();
            if(!defaultID)
                obj.put("_id","specifiedID_" + i);//obj.put("_id", new ObjectId("5c0da873f41dc50e1d56d67d"));
            obj.put("name", "Amy" + i);
            obj.put("age",18 + i % 10);
            obj.put("score",50 + i % 50);
            student.insert(obj);
        }
        System.out.println("Inserted " + num + " documents in " + (time - beginTime) / 1000.0 + " s. Speed every 100,000 insertions:" + speedList);
    }

    public void removeTest(){
        long startTime = System.currentTimeMillis();
        DBObject obj = new BasicDBObject();
        int removeCount = student.remove(obj).getN();
        long endTime = System.currentTimeMillis();
        System.out.println("Removed " + removeCount + " documents in " + (endTime - startTime) / 1000.0 + " s");
    }

    public void searchTest(int num){
        long startTime = System.currentTimeMillis();
        Random r = new Random();
        int fails = 0;
        for(int i = 0; i < num; i++){
            DBObject res = student.findOne(new BasicDBObject("name", "Amy" + r.nextInt((int) student.count())));
            if(res == null)
                fails++;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Found " + (num - fails) + " documents, fails " + fails + " documents in " + (endTime - startTime) / 1000.0 + " s");
    }

    public void finish(){
        mongo.close();
    }
}
