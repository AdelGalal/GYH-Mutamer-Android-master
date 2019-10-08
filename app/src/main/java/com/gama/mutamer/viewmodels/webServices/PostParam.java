package com.gama.mutamer.viewModels.webServices;

/**
 * Created by mustafa on 8/8/17.
 * Release the GEEK
 */

public class PostParam {
    private String Name, Value;

    public PostParam() {

    }

    public PostParam(String name, String value) {
        setName(name);
        setValue(value);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
