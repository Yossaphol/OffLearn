package Student.roadmap;

public class RoadmapCategory {
    private String catID;
    private String catName;

    public RoadmapCategory(String catID, String catName) {
        this.catID = catID;
        this.catName = catName;
    }

    public String getCatID() {
        return this.catID;
    }

    public String getCatName() {
        return this.catName;
    }
}
