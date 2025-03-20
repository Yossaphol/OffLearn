package Teacher.courseManagement;

public class ChapterItem {
    private String chapterName;
    private String desc;

    public ChapterItem(String name, String desc){
        this.setChapterName(name);
        this.setDesc(desc);
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
}
