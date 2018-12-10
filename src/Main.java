public class Main {

    public static void main(String[] args) {
        MongoTest mongoTest = new MongoTest();
        // 插入
//        mongoTest.insertTest(500000,false);

        // 删除
//        mongoTest.removeTest();

        // 查找
        mongoTest.searchTest(500);

        mongoTest.finish();
    }
}
