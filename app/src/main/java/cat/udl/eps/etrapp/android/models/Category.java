package cat.udl.eps.etrapp.android.models;

/**
 * Created by joe on 8/1/18.
 */

public class Category {

    private long id;
    private String name;
    private long parent_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nomes) {
        this.name = nomes;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

}
