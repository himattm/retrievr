package ninja.retrievr.retrievr;

/**
 * Created by mattmckenna on 4/11/15.
 */
public class RetrievrItem {

    private String name;
//  TODO  private boolean isLost = false;

    public RetrievrItem () {
        this.name  = "Unavailable";
    }

    public RetrievrItem (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
