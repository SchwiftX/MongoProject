import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;

public class MongoDemo {
    Mongo mongo;
    DB school;
    DBCollection student;

    protected MongoDemo(){
        try {
            mongo = new Mongo("localhost",10086);
            school = mongo.getDB("school");
            student = school.getCollection("student");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void iterate(){
        DBCursor cursor = student.find();
//        DBObject sort = new BasicDBObject("_id", -1);
//        cursor.sort(sort);
        int i = 0;
        while (cursor.hasNext()){
            DBObject obj = cursor.next();
            System.out.println(obj);
            if((++i) >= 200)
                break;
        }
    }

    public void remove(){
        DBObject obj = new BasicDBObject("_id", new ObjectId("5c06a71b152abf1a9194f43b"));
        WriteResult removeRes = student.remove(obj);
        System.out.println("removes:"+removeRes.getN());
    }

    public void insert(){
        DBObject obj = new BasicDBObject();
        obj.put("name", "angelaDD");
        obj.put("age",30);
        student.insert(obj);
    }

    public void update(){
        DBObject obj = new BasicDBObject();
        obj.put("age",30);
        DBObject target = new BasicDBObject();
        target.put("size","C");
        DBObject upsertValue = new BasicDBObject("$set", target);
        WriteResult res = student.updateMulti(obj, upsertValue);
        System.out.println("updates:"+res.getN());
    }

    public void removeKey(){
        DBObject obj = new BasicDBObject();
        obj.put("age",30);
        DBObject target = new BasicDBObject();
        target.put("personal website",1);
        DBObject upsertValue = new BasicDBObject("$unset", target);
        WriteResult res = student.updateMulti(obj, upsertValue);
        System.out.println("removeKeys:"+res.getN());
    }
    public void finish(){
        mongo.close();
    }

}
