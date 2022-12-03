package pl.put.poznan.json_tools.logic;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class JSONTools{

    private final String[] transforms;

    public JSONTools(String[] transforms){
        this.transforms = transforms;
    }

    public String transform(String text){
        // of course, normally it would do something based on the transforms
        return text.toUpperCase();
    }
}
