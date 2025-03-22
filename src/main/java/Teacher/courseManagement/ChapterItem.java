package Teacher.courseManagement;

public class ChapterItem {
    private int chapId;
    private String chapterName;
    private String desc;
    private String imrUrl;

    public ChapterItem(int chapId, String name, String desc){
        this.setChapId(chapId);
        this.setChapterName(name);
        this.setDesc(desc);
    }

    public int getChapId(){
        return this.chapId;
    }

    public void setChapId(int chapId){
        this.chapId = chapId;
    }

    public void setImrUrl(String url){
        this.imrUrl = url;
    }

    public String getImrUrl(){
        return this.imrUrl;
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
