package Teacher.courseManagement;

import Teacher.quiz.QuizItem;

public class ChapterItem {
    private int chapId;
    private String chapterName;
    private String desc;
    private String imgUrl;
    private String material;
    private QuizItem quizItem;

    public ChapterItem(int chapId, String name, String desc, String imgUrl, String material){
        this.setChapId(chapId);
        this.setChapterName(name);
        this.setDesc(desc);
        this.setImgUrl(imgUrl);
        this.setMaterial(material);
    }

    public int getChapId(){
        return this.chapId;
    }

    public void setChapId(int chapId){
        this.chapId = chapId;
    }

    public void setImgUrl(String url){
        this.imgUrl = url;
    }

    public String getImgUrl(){
        return this.imgUrl;
    }

    public void setChapterName(String name){
        this.chapterName = name;
    }

    public String getChapterName(){
        return this.chapterName;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return  this.desc;
    }

    @Override
    public String toString(){
       return this.getChapterName() + "\n" + this.getDesc();
    }

    public QuizItem getQuizItem() {
        return quizItem;
    }

    public void setQuizItem(QuizItem quizItem) {
        this.quizItem = quizItem;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
